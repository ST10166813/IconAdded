package com.example.flashapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String?,
    val limit: Double?,
    val icon: Int, // You'll store the resource ID of the drawable
    val type: String // "Income" or "Expense"
)