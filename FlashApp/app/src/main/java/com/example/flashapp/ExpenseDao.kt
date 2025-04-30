package com.example.flashapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expense: ExpenseEntity)

    @Query("SELECT * FROM expense ORDER BY id DESC")
    fun getAllExpenses(): LiveData<List<ExpenseEntity>>

    @Delete
    fun delete(expense: ExpenseEntity)
}
