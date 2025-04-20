package com.example.iterativepractice.presentation.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iterativepractice.data.local.pojo.CartItemWithProduct
import com.example.iterativepractice.domain.usecases.cart.CartUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Reactive Cart State Management Pattern with Optimistic Updates
 * With idempotent State Updates
 */
@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCases: CartUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState(isLoading = true))
    val  uiState: StateFlow<CartUiState> = _uiState

    /** Holds edits the user made the DB hasn't confirmed yet */
    private val pendingEdits = MutableStateFlow<Map<Int, Int>>(emptyMap())

    private var updateJob: Job? = null
    private val debounceTime = 100L

    init { observeCart() }

    fun updateQuantity(productId: Int, quantity: Int) {
        pendingEdits.update { it + (productId to quantity) }

        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            delay(debounceTime)
            try {
                cartUseCases.updateCartItemQuantity(productId, quantity)
            } catch (e: Exception) {
                Log.e("CartViewModel", "updateQuantity error: ${e.message}", e)
                _uiState.update { it.copy(error = "Failed to update quantity") }
            } finally {
                pendingEdits.update { it - productId }
            }
        }
    }

    fun removeFromCart(productId: Int) = viewModelScope.launch {
        pendingEdits.update { it - productId }
        try {
            cartUseCases.removeFromCart(productId)
        } catch (e: Exception) {
            Log.e("CartViewModel", "removeFromCart error: ${e.message}", e)
            _uiState.update { it.copy(error = "Failed to remove item") }
        }
    }

    fun clearCart() = viewModelScope.launch {
        pendingEdits.value = emptyMap()
        try {
            cartUseCases.clearCart()
        } catch (e: Exception) {
            Log.e("CartViewModel", "clearCart error: ${e.message}", e)
            _uiState.update { it.copy(error = "Failed to clear cart") }
        }
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartUseCases.getCartItems()
                .combine(pendingEdits) { dbItems, edits ->
                    applyPendingEdits(dbItems, edits)
                }
                .distinctUntilChanged(::listEqualsByIdAndQty)
                .catch { e ->
                    handleCartError(e)
                }
                .collect { mergedItems ->
                    updateCartState(mergedItems)
                }
        }
    }

    private fun applyPendingEdits(
        dbItems: List<CartItemWithProduct>,
        edits: Map<Int, Int>
    ): List<CartItemWithProduct> {
        if (edits.isEmpty()) return dbItems

        return dbItems.map { item ->
            val pendingQuantity = edits[item.cartItem.productId]
            if (pendingQuantity != null) {
                item.copy(cartItem = item.cartItem.copy(quantity = pendingQuantity))
            } else {
                item
            }
        }
    }

    private fun handleCartError(e: Throwable) {
        Log.e("CartViewModel", "observeCart error: ${e.message}", e)
        _uiState.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
    }

    private fun updateCartState(items: List<CartItemWithProduct>) {
        val newState = CartUiState(
            items = items,
            total = cartUseCases.calculateTotal(items),
            isLoading = false,
            error = null
        )

        if (!uiContentEquals(_uiState.value, newState)) {
            _uiState.value = newState
        }
    }

    private fun listEqualsByIdAndQty(
        old: List<CartItemWithProduct>,
        new: List<CartItemWithProduct>
    ): Boolean = old.size == new.size &&
            old.zip(new).all { (a, b) ->
                a.cartItem.productId == b.cartItem.productId &&
                        a.cartItem.quantity  == b.cartItem.quantity
            }

    private fun uiContentEquals(a: CartUiState, b: CartUiState): Boolean =
        a.total == b.total && listEqualsByIdAndQty(a.items, b.items)
}
