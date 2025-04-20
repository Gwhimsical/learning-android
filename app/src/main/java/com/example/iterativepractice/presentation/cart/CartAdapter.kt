package com.example.iterativepractice.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.iterativepractice.data.local.pojo.CartItemWithProduct
import com.example.iterativepractice.databinding.CartItemBinding

class CartAdapter(
    private val onQuantityChanged: (productId: Int, quantity: Int) -> Unit,
    private val onRemoveClick: (productId: Int) -> Unit
    ) : RecyclerView.Adapter<CartViewHolder>() {

    private var cart: List<CartItemWithProduct> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding, onQuantityChanged, onRemoveClick)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cart[position]
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int = cart.size

    fun submitList(newList: List<CartItemWithProduct>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = cart.size
            override fun getNewListSize() = newList.size

            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
                return cart[oldPos].productInfo.id == newList[newPos].productInfo.id
            }

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
                val oldItem = cart[oldPos]
                val newItem = newList[newPos]
                return oldItem.cartItem.quantity == newItem.cartItem.quantity &&
                        oldItem.productInfo.price == newItem.productInfo.price &&
                        oldItem.productInfo.name == newItem.productInfo.name
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        cart = newList
        diffResult.dispatchUpdatesTo(this)
    }
}