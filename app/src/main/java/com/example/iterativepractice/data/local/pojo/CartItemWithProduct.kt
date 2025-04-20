package com.example.iterativepractice.data.local.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.example.iterativepractice.data.local.cartitem.CartItem
import com.example.iterativepractice.data.local.product.Product


data class CartItemWithProduct(
    @Embedded val cartItem: CartItem,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val productInfo: Product
)