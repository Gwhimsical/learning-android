package com.example.iterativepractice.presentation.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iterativepractice.data.local.product.Product
import com.example.iterativepractice.domain.usecases.cart.CartUseCases
import com.example.iterativepractice.domain.usecases.common.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Direct State Manipulation with Manual State Updates
 */
@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuUseCases: CartUseCases,
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    private val _selectedQuantities = MutableStateFlow<Map<Int, Int>>(emptyMap())

    private val _cartTotal = MutableStateFlow(0.0)
    val cartTotal: StateFlow<Double> = _cartTotal

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        loadProducts()
        viewModelScope.launch { observeCartTotal() }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                val productsList = getAllProductsUseCase()
                _products.value = productsList
            } catch (e: Exception) {
                Log.e("MenuViewModel", "Error loading products: ${e.message}", e)
            }
        }
    }

    /* Moved feature to cart, kinda redundant otherwise

    fun removeFromCart(productId: Int) {
        viewModelScope.launch (context = Dispatchers.IO) {
            menuUseCases.removeFromCart(productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch (context = Dispatchers.IO)  {
            menuUseCases.clearCart()
        }
    }
    */

    fun addToCart(productId: Int) {
        viewModelScope.launch {
            val currentMap = _selectedQuantities.value.toMutableMap()
            val qty = currentMap[productId]
            if (qty != null) {
                menuUseCases.addToCart(productId, qty)
            }
        }
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        val currentMap = _selectedQuantities.value.toMutableMap()
        currentMap[productId] = quantity
        _selectedQuantities.value = currentMap
    }

    private fun observeCartTotal() {
        viewModelScope.launch {
            menuUseCases.getCartTotal()
                .catch { e ->
                    Log.e("MenuViewModel", "Error getting cart total: ${e.message}", e)
                }
                .collect { total ->
                    _cartTotal.value = total ?: 0.0
                }
        }
    }
}