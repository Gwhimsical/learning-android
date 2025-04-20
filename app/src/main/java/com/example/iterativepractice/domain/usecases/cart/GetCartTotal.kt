package com.example.iterativepractice.domain.usecases.cart

import com.example.iterativepractice.domain.repository.ShopRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartTotal @Inject constructor(
    private val repository: ShopRepository
) {
    suspend operator fun invoke(): Flow<Double?> {
        return repository.getCartTotal()
    }
}