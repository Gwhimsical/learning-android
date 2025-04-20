package com.example.iterativepractice.di

import com.example.iterativepractice.domain.repository.ShopRepository
import com.example.iterativepractice.domain.usecases.cart.CartUseCases
import com.example.iterativepractice.domain.usecases.cart.GetCartItemCountUseCase
import com.example.iterativepractice.domain.usecases.product.AddProductUseCase
import com.example.iterativepractice.domain.usecases.product.DeleteProductUseCase
import com.example.iterativepractice.domain.usecases.common.GetAllProductsUseCase
import com.example.iterativepractice.domain.usecases.product.ProductUseCases
import com.example.iterativepractice.domain.usecases.product.UpdateProductUseCase
import com.example.iterativepractice.domain.usecases.cart.AddToCartUseCase
import com.example.iterativepractice.domain.usecases.cart.CalculateTotal
import com.example.iterativepractice.domain.usecases.cart.ClearCartUseCase
import com.example.iterativepractice.domain.usecases.cart.GetCartItemsSyncUseCase
import com.example.iterativepractice.domain.usecases.cart.GetCartItemsUseCase
import com.example.iterativepractice.domain.usecases.cart.GetCartTotal
import com.example.iterativepractice.domain.usecases.cart.RemoveFromCartUseCase
import com.example.iterativepractice.domain.usecases.cart.UpdateCartItemQuantityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideProductUseCases(repository: ShopRepository): ProductUseCases {
        return ProductUseCases(
            addProduct = AddProductUseCase(repository),
            deleteProduct = DeleteProductUseCase(repository),
            updateProduct = UpdateProductUseCase(repository),
            getAllProducts = GetAllProductsUseCase(repository),
        )
    }

    @Singleton
    @Provides
    fun provideCartUseCases(repository: ShopRepository): CartUseCases {
        return CartUseCases(
            addToCart = AddToCartUseCase(repository),
            getCartItemCount = GetCartItemCountUseCase(repository),
            removeFromCart = RemoveFromCartUseCase(repository),
            clearCart = ClearCartUseCase(repository),
            updateCartItemQuantity = UpdateCartItemQuantityUseCase(repository),
            getCartItems = GetCartItemsUseCase(repository),
            calculateTotal = CalculateTotal(),
            getCartItemsSync = GetCartItemsSyncUseCase(repository),
            getCartTotal = GetCartTotal(repository),
        )
    }
}