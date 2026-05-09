package com.example.expensetracker.domain.repository

import com.example.expensetracker.data.local.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpense(): Flow<List<ExpenseEntity>>
    suspend fun addExpense(expense: ExpenseEntity)
    fun getMonthlyTotal(): Flow<Double?>
    suspend fun deleteExpense(expense: ExpenseEntity)
}
