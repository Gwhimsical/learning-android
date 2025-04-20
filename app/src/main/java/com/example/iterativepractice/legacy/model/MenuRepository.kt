package com.example.iterativepractice.legacy.model

import kotlinx.coroutines.flow.MutableStateFlow

class MenuRepository (private val _menuItemsFlow: MutableStateFlow<List<MenuItem>>) {

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
