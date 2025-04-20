package com.example.iterativepractice.domain.usecases.cart

import com.example.iterativepractice.domain.repository.ShopRepository
import javax.inject.Inject

class GetCartItemCountUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    suspend operator fun invoke(productId: Int): Int {
        return repository.getCartItemCount(productId)
    }
}