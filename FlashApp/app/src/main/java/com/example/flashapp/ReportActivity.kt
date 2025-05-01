package com.example.flashapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flashapp.databinding.ActivityReportBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ReportActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var binding: ActivityReportBinding
    private var startDate: String = ""
    private var endDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        setupDatePickers()

        // Generate report button
        binding.generateReportBtn.setOnClickListener { generateReport() }

        // Cancel button
        binding.cancelBtn.setOnClickListener { finish() }

        // Categories button (navigate to CategoryListActivity)
        binding.categoriesBtn.setOnClickListener {
            val intent = Intent(this, CategoryListActivity::class.java)
            startActivity(intent)
        }

        // Expenses button (navigate to AddExpenseActivity)
        binding.expensesBtn.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupDatePickers() {
        val calendar = Calendar.getInstance()

        binding.startDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                val selectedDate = String.format("%02d-%02d-%04d", d, m + 1, y)
                binding.startDate.setText(selectedDate)
                startDate = selectedDate
            }, year, month, day)
            datePicker.show()
        }

        binding.endDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                val selectedDate = String.format("%02d-%02d-%04d", d, m + 1, y)
                binding.endDate.setText(selectedDate)
                endDate = selectedDate
            }, year, month, day)
            datePicker.show()
        }
    }

    private fun generateReport() {
        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please select both start and end date", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            // Get total spent between selected dates
            val totalSpent = withContext(Dispatchers.IO) {
                db.expenseDao().getTotalSpentBetweenDates(startDate, endDate)
            }

            // Get category breakdown between selected dates
            val categoryBreakdown = withContext(Dispatchers.IO) {
                db.expenseDao().getCategoryBreakdownBetweenDates(startDate, endDate)
            }

            // Display total spent
            binding.totalSpentText.text = "Total Spent: R${totalSpent}"

            // Clear previous results
            binding.categoryListContainer.removeAllViews()

            // Display category breakdown
            categoryBreakdown.forEach { categorySpent ->
                val categoryText = "${categorySpent.category}: R${categorySpent.totalAmount}"
                val textView = TextView(this@ReportActivity).apply {
                    text = categoryText
                    textSize = 16f
                    setTextColor(resources.getColor(R.color.DarkViolet, null))
                    setPadding(0, 8, 0, 8)
                }
                binding.categoryListContainer.addView(textView)
            }
        }
    }
}
