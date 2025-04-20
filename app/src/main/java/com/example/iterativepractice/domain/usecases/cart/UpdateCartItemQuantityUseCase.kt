package com.example.iterativepractice.domain.usecases.cart

import com.example.iterativepractice.domain.repository.ShopRepository
import javax.inject.Inject

class UpdateCartItemQuantityUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    suspend operator fun invoke(productId: Int, quantity: Int) {
        repository.updateCartItemQuantity(productId, quantity)
    }
}