package com.example.iterativepractice.legacy.model

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor() : ViewModel() {

    private val _menuItemsFlow = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItemsFlow: StateFlow<List<MenuItem>> = _menuItemsFlow

    fun deleteItem(menuItem: MenuItem) {
        val newList = _menuItemsFlow.value.toMutableList().apply {
            remove(menuItem)
        }
        _menuItemsFlow.value = newList
    }

    init {
        _menuItemsFlow.value = listOf(
                MenuItem(1, "Egg Sandwich", Category.BREAKFAST, 5.99),
                MenuItem(2, "Toast", Category.BREAKFAST, 4.99),
                //MenuItem(3, "French Toast", Category.BREAKFAST, 6.99),
                MenuItem(4, "Falafel", Category.LUNCH, 7.99),
                MenuItem(5, "Burger", Category.LUNCH, 8.99),
                MenuItem(6, "Pizza", Category.LUNCH, 9.99),
                MenuItem(7, "Pasta", Category.DINNER, 10.99),
                MenuItem(8, "Spaghetti", Category.DINNER, 11.99),
                //MenuItem(9, "Steak", Category.DINNER, 12.99),
                MenuItem(10, "Salad", Category.DINNER, 6.99)
        )
    }
}