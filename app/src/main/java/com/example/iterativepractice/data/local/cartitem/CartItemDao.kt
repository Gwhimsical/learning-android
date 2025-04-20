package com.example.iterativepractice.data.local.cartitem

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.iterativepractice.data.local.pojo.CartItemWithProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {
    @Query("SELECT * FROM CartItem")
    fun getAll(): Flow<List<CartItem>>

    @Query("SELECT * FROM CartItem WHERE productId = :productId")
    suspend fun getByProductId(productId: Int): CartItem?

    @Query("SELECT SUM(p.price * ci.quantity) FROM CartItem ci JOIN Product p ON ci.productId = p.id")
    fun getCartTotal(): Flow<Double?>

    @Query("SELECT SUM(quantity) FROM CartItem")
    suspend fun getCartQuantity(): Int?

    @Query("SELECT COUNT(*) FROM CartItem WHERE productId = :productId")
    suspend fun getCartItemCount(productId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItem)

    @Update
    suspend fun update(cartItem: CartItem)

    @Delete
    suspend fun delete(cartItem: CartItem)

    @Query("DELETE FROM CartItem")
    suspend fun clearCart()

    @Query("UPDATE CartItem SET quantity = :newQuantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: Int, newQuantity: Int)

    @Transaction
    @Query("SELECT * FROM CartItem")
    fun getCartItemsWithProducts(): Flow<List<CartItemWithProduct>>

    @Transaction
    @Query("SELECT * FROM CartItem WHERE productId = :productId")
    suspend fun getCartItemWithProduct(productId: Int): CartItemWithProduct?

    @Transaction
    @Query("SELECT * FROM CartItem")
    suspend fun getCartItemsWithProductsSync(): List<CartItemWithProduct>

    @Transaction
    suspend fun addToCart(addedItem: CartItem) {
        val existingCartItem = getByProductId(addedItem.productId)
        if (existingCartItem != null) {
            val newQuantity = existingCartItem.quantity + addedItem.quantity
            updateQuantity(addedItem.productId, newQuantity)
        } else {
            insert(addedItem)
        }
    }
}