package com.example.iterativepractice.domain.repository

import com.example.iterativepractice.data.local.cartitem.CartItem
import com.example.iterativepractice.data.local.pojo.CartItemWithProduct
import com.example.iterativepractice.data.local.product.Product
import kotlinx.coroutines.flow.Flow

interface ShopRepository {
    //val allProducts: Flow<List<Product>>
    //val allCartItems: Flow<List<CartItem>>

    // Simple CRUD API for products
    suspend fun getAllProducts(): List<Product>
    suspend fun addProduct(product: Product): Product
    suspend fun deleteProduct(product: Product): Boolean
    suspend fun updateProduct(product: Product): Boolean

    // Cart API
    suspend fun addToCart(cartItem: CartItem)
    //suspend fun updateCartItem(cartItem: CartItem)
    suspend fun updateCartItemQuantity(productId: Int, quantity: Int)
    suspend fun removeCartItem(cartItem: CartItem)
    suspend fun getCartTotal(): Flow<Double?>
    suspend fun getCartItemCount(productId: Int): Int // This isn't used
    suspend fun clearCart()
    suspend fun getCartItemsSync(): List<CartItemWithProduct>
    suspend fun getCartItemByProductId(productId: Int): CartItem?
    //suspend fun getProductById(productId: Int): Product?
    suspend fun getCartItemsWithProducts(): Flow<List<CartItemWithProduct>>
}
