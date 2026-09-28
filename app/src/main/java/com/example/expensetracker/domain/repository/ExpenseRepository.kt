package com.example.expensetracker.domain.repository

import com.example.expensetracker.data.local.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpense(): Flow<List<ExpenseEntity>>
    fun getExpensesByCategory(category: String): Flow<List<ExpenseEntity>>
    fun getExpensesByMonth(month: String, year: String): Flow<List<ExpenseEntity>>
    suspend fun getExpenseById(id: Int): ExpenseEntity?
    suspend fun addExpense(expense: ExpenseEntity)
    suspend fun updateExpense(expense: ExpenseEntity)
    fun getMonthlyTotal(month: String, year: String): Flow<Double?>
    suspend fun deleteExpense(expense: ExpenseEntity)
}
