package com.example.iterativepractice.domain.usecases.cart

import com.example.iterativepractice.data.local.pojo.CartItemWithProduct
import javax.inject.Inject

class CalculateTotal @Inject constructor() {
    operator fun invoke(cartItems: List<CartItemWithProduct>): Double {
        return cartItems.sumOf { it.productInfo.price * it.cartItem.quantity }
    }

}