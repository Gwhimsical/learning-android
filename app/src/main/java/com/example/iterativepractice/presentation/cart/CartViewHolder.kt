package com.example.iterativepractice.presentation.cart

import androidx.recyclerview.widget.RecyclerView
import com.example.iterativepractice.data.local.pojo.CartItemWithProduct
import com.example.iterativepractice.databinding.CartItemBinding
import java.text.NumberFormat
import java.util.Locale

class CartViewHolder(
    private val binding: CartItemBinding,
    private val onQuantityChanged: (productId: Int, quantity: Int) -> Unit,
    private val onRemoveClick: (productId: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cartItemWithProduct: CartItemWithProduct) {
        val product = cartItemWithProduct.productInfo
        val cartItem = cartItemWithProduct.cartItem
        val quantity = cartItem.quantity

        val formattedPrice =
            NumberFormat.getCurrencyInstance(Locale.getDefault()).format(product.price)
        val formattedSubtotal =
            NumberFormat.getCurrencyInstance(Locale.getDefault()).format(product.price * quantity)

        binding.tvCartItemName.text = product.name
        binding.tvCartItemPrice.text = formattedPrice
        binding.tvQuantityValue.text = quantity.toString()
        binding.tvCartItemQuantityDisplay.text = quantity.toString()
        binding.tvSubtotalValue.text = formattedSubtotal

        binding.btnIncrement.setOnClickListener {
            val newQuantity = quantity + 1
            onQuantityChanged(product.id, newQuantity)
        }

        binding.btnDecrement.setOnClickListener {
            if (quantity > 1) {
                val newQuantity = quantity - 1
                onQuantityChanged(product.id, newQuantity)
            } else {
                onRemoveClick(product.id)
            }
        }

        binding.btnRemoveItem.setOnClickListener {
            onRemoveClick(product.id)
        }
    }
}