package com.example.expensetracker.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.ExpenseEntity
import com.example.expensetracker.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository
): ViewModel() {
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
