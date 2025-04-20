package com.example.iterativepractice.domain.usecases.product

import com.example.iterativepractice.core.result.ApiResult
import com.example.iterativepractice.data.local.product.Product
import com.example.iterativepractice.domain.repository.ShopRepository
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    suspend operator fun invoke(product: Product): ApiResult<Product> {
        return try {
            repository.addProduct(product)
            ApiResult.success(product)
        } catch (e: Exception) {
            ApiResult.error(e)
        }
    }
}