package com.example.iterativepractice.domain.usecases.common

import com.example.iterativepractice.data.local.product.Product
import com.example.iterativepractice.domain.repository.ShopRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    suspend operator fun invoke(): List<Product> {
        return repository.getAllProducts()
    }
}