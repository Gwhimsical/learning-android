package com.example.iterativepractice.domain.usecases.product

import com.example.iterativepractice.data.local.product.Product
import com.example.iterativepractice.domain.repository.ShopRepository
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    suspend operator fun invoke(product: Product): Boolean {
        return repository.updateProduct(product)
    }

}