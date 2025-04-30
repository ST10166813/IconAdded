package com.example.flashapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CategoryActivity : AppCompatActivity() {

    private lateinit var categoryNameEditText: EditText
    private lateinit var categoryDescriptionEditText: EditText
    private lateinit var categoryLimitEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var homeButton: ImageView
    private lateinit var expenseRadioButton: RadioButton
    private lateinit var incomeRadioButton: RadioButton
    private var selectedIcon: Int = R.drawable.bulb // Default icon

    private lateinit var categoryDao: CategoryDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        categoryDao = AppDatabase.getDatabase(application).categoryDao()

        categoryNameEditText = findViewById(R.id.cate_name)
        categoryDescriptionEditText = findViewById(R.id.cate_description)
        categoryLimitEditText = findViewById(R.id.cate_limit)
        saveButton = findViewById(R.id.cate_save_btn)
        cancelButton = findViewById(R.id.cate_cancel_btn)
        homeButton = findViewById(R.id.home_btn_cate)
        expenseRadioButton = findViewById(R.id.Expense_btn)
        incomeRadioButton = findViewById(R.id.Income_btn)

        setupIconSelection()
        setupButtons()
    }

    private fun setupIconSelection() {
        findViewById<ImageView>(R.id.imageView3).setOnClickListener { selectedIcon = R.drawable.bulb }
        findViewById<ImageView>(R.id.imageView5).setOnClickListener { selectedIcon = R.drawable.gasstation }
        findViewById<ImageView>(R.id.imageView6).setOnClickListener { selectedIcon = R.drawable.deliveryman }
        findViewById<ImageView>(R.id.imageView7).setOnClickListener { selectedIcon = R.drawable.plane }
        findViewById<ImageView>(R.id.imageView8).setOnClickListener { selectedIcon = R.drawable.grocery }
        // Add more icon click listeners as needed
    }

    private fun setupButtons() {
        saveButton.setOnClickListener {
            val name = categoryNameEditText.text.toString().trim()
            val description = categoryDescriptionEditText.text.toString().trim()
            val limitText = categoryLimitEditText.text.toString().trim()
            val limit = limitText.toDoubleOrNull()
            var type = ""

            if (expenseRadioButton.isChecked) {
                type = "Expense"
            } else if (incomeRadioButton.isChecked) {
                type = "Income"
            } else {
                Toast.makeText(this, "Please select category type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (name.isNotEmpty()) {
                if (limit == null) {
                    Toast.makeText(this, "Please enter a valid limit", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val newCategory = CategoryEntity(
                    name = name,
                    description = if (description.isNotEmpty()) description else null,
                    limit = limit,
                    icon = selectedIcon,
                    type = type
                )

                Thread {
                    categoryDao.insertSync(newCategory)
                    runOnUiThread {
                        clearInputFields()
                        Toast.makeText(this, "Category saved", Toast.LENGTH_SHORT).show()
                    }
                }.start()
            } else {
                Toast.makeText(this, "Category name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener {
            clearInputFields()
        }

        homeButton.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

    private fun clearInputFields() {
        categoryNameEditText.text.clear()
        categoryDescriptionEditText.text.clear()
        categoryLimitEditText.text.clear()
        expenseRadioButton.isChecked = false
        incomeRadioButton.isChecked = false
        selectedIcon = R.drawable.bulb // Reset default icon
    }
}
