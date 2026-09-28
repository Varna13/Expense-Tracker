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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ExpenseRepository
): ViewModel(){

    private val _selectedMonth = MutableStateFlow(Calendar.getInstance().get(Calendar.MONTH) + 1)
    val selectedMonth = _selectedMonth.asStateFlow()

    private val _selectedYear = MutableStateFlow(Calendar.getInstance().get(Calendar.YEAR))
    val selectedYear = _selectedYear.asStateFlow()

    // Combine month and year to trigger data updates
    private val selectedDate = combine(_selectedMonth, _selectedYear) { month, year ->
        val monthStr = if (month < 10) "0$month" else month.toString()
        Pair(monthStr, year.toString())
    }

    val expenses: StateFlow<List<ExpenseEntity>> = selectedDate.flatMapLatest { (month, year) ->
        repository.getExpensesByMonth(month, year).map { list ->
            list.groupBy { it.category }
                .map { (category, items) ->
                    val totalAmount = items.sumOf { it.amount }
                    val latestItem = items.maxByOrNull { it.date }
                    ExpenseEntity(
                        id = latestItem?.id ?: items.first().id,
                        title = if (items.size > 1) "${items.size} Transactions" else items.first().title,
                        amount = totalAmount,
                        category = category,
                        date = latestItem?.date ?: 0L
                    )
                }
                .sortedByDescending { it.date }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    // Calculate total directly from the filtered expenses list to ensure they are always in sync
    val total: StateFlow<Double> = expenses.map { list ->
        list.sumOf { it.amount }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0.0
    )

    fun changeMonth(month: Int) {
        _selectedMonth.value = month
    }

    fun changeYear(year: Int) {
        _selectedYear.value = year
    }

    fun deleteExpense(expense: ExpenseEntity){
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }
}
