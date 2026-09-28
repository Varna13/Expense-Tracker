package com.example.expensetracker.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.ExpenseEntity
import com.example.expensetracker.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository
): ViewModel() {

    private val _expenseToEdit = MutableStateFlow<ExpenseEntity?>(null)
    val expenseToEdit = _expenseToEdit.asStateFlow()

    fun loadExpense(id: Int) {
        if (id == -1) {
            _expenseToEdit.value = null
            return
        }
        viewModelScope.launch {
            _expenseToEdit.value = repository.getExpenseById(id)
        }
    }

    fun addOrUpdateExpense(id: Int, title: String, amount: Double, category: String, date: Long) {
        viewModelScope.launch {
            val expense = ExpenseEntity(
                id = if (id == -1) 0 else id,
                title = title,
                amount = amount,
                category = category,
                date = date
            )
            if (id == -1) {
                repository.addExpense(expense)
            } else {
                repository.updateExpense(expense)
            }
        }
    }

    fun addExpenseWithDate(title: String, amount: Double, category: String, date: Long) {
        viewModelScope.launch {
            repository.addExpense(
                ExpenseEntity(
                    id = 0,
                    title = title,
                    amount = amount,
                    category = category,
                    date = date
                )
            )
        }
    }

    fun addExpense(title: String, amount: Double, category: String){
        viewModelScope.launch {
            repository.addExpense(
                ExpenseEntity(
                    id = 0,
                    title = title,
                    amount = amount,
                    category = category,
                    date = System.currentTimeMillis()
                )
            )
        }
    }
}
