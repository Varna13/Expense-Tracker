package com.example.expensetracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.ExpenseEntity
import com.example.expensetracker.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ExpenseRepository
): ViewModel(){

    val expenses = repository.getExpense().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val total = repository.getMonthlyTotal()
        .map { it ?: 0.0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    fun deleteExpense(expense: ExpenseEntity){
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

    fun addExpense(expense: ExpenseEntity){
        viewModelScope.launch {
            repository.addExpense(expense)
        }
    }
}
