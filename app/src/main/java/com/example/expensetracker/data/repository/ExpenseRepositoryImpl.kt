package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.ExpenseDao
import com.example.expensetracker.data.local.ExpenseEntity
import com.example.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepositoryImpl(
    private val dao: ExpenseDao
): ExpenseRepository {

    override fun getExpense(): Flow<List<ExpenseEntity>> {
        return dao.getAllExpenses()
    }

    override suspend fun addExpense(expense: ExpenseEntity) {
        dao.insertExpense(
            ExpenseEntity(
                id = expense.id,
                title = expense.title,
                amount = expense.amount,
                category = expense.category,
                date = expense.date
            )
        )
    }

    override fun getMonthlyTotal(): Flow<Double?> {
        return dao.getMonthlyTotal()
    }

    override suspend fun deleteExpense(expense: ExpenseEntity) {
        dao.deleteExpense(
            ExpenseEntity(
                id = expense.id,
                title = expense.title,
                amount = expense.amount,
                category = expense.category,
                date = expense.date
            )
        )
    }


}
