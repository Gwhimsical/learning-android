package com.example.iterativepractice.data.repository

import com.example.iterativepractice.data.local.cartitem.CartItem
import com.example.iterativepractice.data.local.cartitem.CartItemDao
import com.example.iterativepractice.data.local.pojo.CartItemWithProduct
import com.example.iterativepractice.data.local.product.Product
import com.example.iterativepractice.data.local.product.ProductDao
import com.example.iterativepractice.domain.repository.ShopRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ShopRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val productDao: ProductDao,
    private val cartItemDao: CartItemDao
) : ShopRepository {

    //override val allProducts: Flow<List<Product>> = productDao.getAll()
    //override val allCartItems: Flow<List<CartItem>> = cartItemDao.getAll()

    override suspend fun addProduct(product: Product): Product =
        withContext(dispatcher) {
            val id = productDao.insert(product).toInt()
            product.copy(id = id)
    }

    override suspend fun updateProduct(product: Product): Boolean =
        withContext(dispatcher) {
            val rowsAffected = productDao.update(product)
            rowsAffected > 0
    }

    override suspend fun deleteProduct(product: Product): Boolean =
        withContext(dispatcher) {
            val rowsAffected = productDao.delete(product)
            rowsAffected > 0
    }

    override suspend fun getAllProducts(): List<Product> =
        withContext(dispatcher) {
            productDao.getAll()
        }

    override suspend fun addToCart(cartItem: CartItem) =
        withContext(dispatcher) {
            cartItemDao.addToCart(cartItem)
        }

    /*override suspend fun updateCartItem(cartItem: CartItem) =
        withContext(dispatcher) {
            cartItemDao.update(cartItem)
        }
     */

    override suspend fun updateCartItemQuantity(productId: Int, quantity: Int) =
        withContext(dispatcher) {
            cartItemDao.updateQuantity(productId, quantity)
        }

    override suspend fun removeCartItem(cartItem: CartItem) =
        withContext(dispatcher) {
            cartItemDao.delete(cartItem)
        }

    override suspend fun clearCart() =
        withContext(dispatcher) {
            cartItemDao.clearCart()
        }

    override suspend fun getCartTotal(): Flow<Double?> {
        return withContext(dispatcher) {
            cartItemDao.getCartTotal()
        }
    }

    override suspend fun getCartItemCount(productId: Int) =
        withContext(dispatcher) {
            cartItemDao.getCartItemCount(productId)
        }

    override suspend fun getCartItemByProductId(productId: Int): CartItem? =
        withContext(dispatcher) {
            cartItemDao.getByProductId(productId)
        }

    /*override suspend fun getProductById(productId: Int): Product? =
        withContext(dispatcher) {
            productDao.getById(productId)
        }
*/
    override suspend fun getCartItemsWithProducts(): Flow<List<CartItemWithProduct>> =
        withContext(dispatcher) {
            cartItemDao.getCartItemsWithProducts()
        }

    override suspend fun getCartItemsSync(): List<CartItemWithProduct> =
        withContext(dispatcher) {
            cartItemDao.getCartItemsWithProductsSync()
        }
}