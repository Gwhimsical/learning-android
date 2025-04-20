package com.example.iterativepractice.domain.usecases.cart

import com.example.iterativepractice.domain.repository.ShopRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val repository: ShopRepository
){
    suspend operator fun invoke(){
        repository.clearCart()
    }
}