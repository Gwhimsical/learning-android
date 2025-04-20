package com.example.iterativepractice.domain.usecases.cart

import com.example.iterativepractice.data.local.cartitem.CartItem
import com.example.iterativepractice.domain.repository.ShopRepository
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    suspend operator fun invoke(productId: Int) {
        val cartItem = repository.getCartItemByProductId(productId)
        if (cartItem != null) {
            repository.removeCartItem(cartItem)
        }
    }
}