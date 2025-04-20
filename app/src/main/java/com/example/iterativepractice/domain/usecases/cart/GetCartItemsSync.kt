package com.example.iterativepractice.domain.usecases.cart

import com.example.iterativepractice.data.local.pojo.CartItemWithProduct
import com.example.iterativepractice.domain.repository.ShopRepository
import javax.inject.Inject

class GetCartItemsSyncUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    suspend operator fun invoke(): List<CartItemWithProduct> {
        return repository.getCartItemsSync()
    }
}