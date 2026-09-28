package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.ExpenseDao
import com.example.expensetracker.data.local.ExpenseEntity
import com.example.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class ExpenseRepositoryImpl(
    private val dao: ExpenseDao
): ExpenseRepository {

    override fun getExpense(): Flow<List<ExpenseEntity>> {
        return dao.getAllExpenses()
    }

    override fun getExpensesByCategory(category: String): Flow<List<ExpenseEntity>> {
        return dao.getExpensesByCategory(category)
    }

    override fun getExpensesByMonth(month: String, year: String): Flow<List<ExpenseEntity>> {
        return dao.getExpensesByMonth(month, year)
    }

    override suspend fun getExpenseById(id: Int): ExpenseEntity? {
        return dao.getExpenseById(id)
    }

    override suspend fun addExpense(expense: ExpenseEntity) {
        dao.insertExpense(expense)
    }

    override suspend fun updateExpense(expense: ExpenseEntity) {
        dao.updateExpense(expense)
    }

    override fun getMonthlyTotal(month: String, year: String): Flow<Double?> {
        return dao.getMonthlyTotal(month, year)
    }

    override suspend fun deleteExpense(expense: ExpenseEntity) {
        dao.deleteExpense(expense)
    }
}
