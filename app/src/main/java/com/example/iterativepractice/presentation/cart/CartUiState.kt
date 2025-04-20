package com.example.iterativepractice.presentation.cart

import com.example.iterativepractice.data.local.pojo.CartItemWithProduct

data class CartUiState(
    val items: List<CartItemWithProduct> = emptyList(),
    val total: Double = 0.0,
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false,
    val error: String? = null
)