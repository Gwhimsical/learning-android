package com.example.iterativepractice.domain.usecases.cart

data class CartUseCases(
    val addToCart: AddToCartUseCase,
    val getCartItemCount: GetCartItemCountUseCase,
    val removeFromCart: RemoveFromCartUseCase,
    val updateCartItemQuantity: UpdateCartItemQuantityUseCase,
    val clearCart: ClearCartUseCase,
    val getCartItems: GetCartItemsUseCase,
    val calculateTotal: CalculateTotal,
    val getCartItemsSync: GetCartItemsSyncUseCase,
    val getCartTotal: GetCartTotal
)