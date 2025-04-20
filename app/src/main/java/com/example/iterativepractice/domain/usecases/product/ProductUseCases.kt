package com.example.iterativepractice.domain.usecases.product

import com.example.iterativepractice.domain.usecases.common.GetAllProductsUseCase

data class ProductUseCases(
    val addProduct: AddProductUseCase,
    val deleteProduct: DeleteProductUseCase,
    val updateProduct: UpdateProductUseCase,
    val getAllProducts: GetAllProductsUseCase
)