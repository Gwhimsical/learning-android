package com.example.iterativepractice.presentation.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iterativepractice.data.local.product.Product
import com.example.iterativepractice.domain.usecases.common.GetAllProductsUseCase
import com.example.iterativepractice.domain.usecases.product.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 *  Optimistic Updates with Manual State Manipulation and Synchronization checks.
 *
 *  End-goal: Try different approaches for optimistic updates and test performance & responsiveness.
 */
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private var isOptimisticUpdateInProgress = false

    init {
        loadProducts()
    }

    fun addProduct(name: String, price: Double) {
        viewModelScope.launch {
            try {
                val newProduct = Product(name = name, price = price)

                isOptimisticUpdateInProgress = true
                val currentList = _products.value.toMutableList()
                currentList.add(newProduct)
                _products.value = currentList

                productUseCases.addProduct(newProduct)

                val updatedList = getAllProductsUseCase()
                if (_products.value != updatedList) {
                    _products.value = updatedList
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error adding product: ${e.message}", e)
                loadProducts()
            } finally {
                isOptimisticUpdateInProgress = false
            }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            try {
                isOptimisticUpdateInProgress = true
                val currentList = _products.value.toMutableList()
                val index = currentList.indexOfFirst { it.id == product.id }
                if (index != -1) {
                    currentList[index] = product
                    _products.value = currentList
                }

                val success = productUseCases.updateProduct(product)

                if (!success) {
                    loadProducts()
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error updating product: ${e.message}", e)
                loadProducts()
            } finally {
                isOptimisticUpdateInProgress = false
            }
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            try {
                isOptimisticUpdateInProgress = true
                val currentList = _products.value.toMutableList()
                currentList.removeIf { it.id == product.id }
                _products.value = currentList

                val success = productUseCases.deleteProduct(product)

                if (!success) {
                    loadProducts()
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error deleting product: ${e.message}", e)
                loadProducts()
            } finally {
                isOptimisticUpdateInProgress = false
            }
        }
    }

    private fun loadProducts() {
        // Skip loading if optimistic update is in progress
        if (isOptimisticUpdateInProgress) return

        viewModelScope.launch {
            try {
                val productsList = getAllProductsUseCase()
                _products.value = productsList
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error loading products: ${e.message}", e)
            }
        }
    }
}