package com.example.flashapp

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "expense")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val category: String,
    val description: String,
    val amount: Double,
    val type: String, // "Expense" or "Income"
    val imagePath: String?
)