package com.example.iterativepractice.domain.usecases.cart

import com.example.iterativepractice.data.local.pojo.CartItemWithProduct
import com.example.iterativepractice.domain.repository.ShopRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repository: ShopRepository
)
{
    suspend operator fun invoke(): Flow<List<CartItemWithProduct>> {
        return repository.getCartItemsWithProducts()
    }
}