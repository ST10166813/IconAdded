package com.example.flashapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface CategoryDao {

    // Synchronous method to get all categories
    @Query("SELECT * FROM categories")
    fun getAllCategoriesSync(): List<CategoryEntity>

    // Synchronous method to insert a new category
    @Insert
    fun insertSync(category: CategoryEntity)
}

