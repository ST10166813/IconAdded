package com.example.flashapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CategoryListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryDao: CategoryDao
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)

        recyclerView = findViewById(R.id.category_recycler_view)
        backButton = findViewById(R.id.back_button)

        recyclerView.layoutManager = LinearLayoutManager(this)
        categoryAdapter = CategoryAdapter(emptyList())
        recyclerView.adapter = categoryAdapter

        categoryDao = AppDatabase.getDatabase(application).categoryDao()

        loadCategories()

        backButton.setOnClickListener {
            finish() // Go back to the previous screen
        }
    }

    private fun loadCategories() {
        Thread {
            val categories = categoryDao.getAllCategoriesSync()
            runOnUiThread {
                categoryAdapter = CategoryAdapter(categories)
                recyclerView.adapter = categoryAdapter
            }
        }.start()
    }
}