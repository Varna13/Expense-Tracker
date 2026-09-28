package com.example.expensetracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.ExpenseEntity
import com.example.expensetracker.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _category = MutableStateFlow("")
    private val _month = MutableStateFlow("")
    private val _year = MutableStateFlow("")
    
    val expenses: StateFlow<List<ExpenseEntity>> = combine(_category, _month, _year) { cat, m, y ->
        Triple(cat, m, y)
    }.flatMapLatest { (category, month, year) ->
        if (month.isEmpty() || year.isEmpty()) {
            repository.getExpensesByCategory(category)
        } else {
            repository.getExpensesByMonth(month, year).map { list ->
                list.filter { it.category == category }
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun setData(category: String, month: Int, year: Int) {
        _category.value = category
        _month.value = if (month < 10) "0$month" else month.toString()
        _year.value = year.toString()
    }

    fun deleteExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }
}
