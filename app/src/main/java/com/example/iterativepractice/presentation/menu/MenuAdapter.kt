package com.example.iterativepractice.presentation.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.iterativepractice.data.local.product.Product
import com.example.iterativepractice.databinding.MenuItemBinding

class MenuAdapter(
    private val onAddClick: (product: Product, quantity: Int) -> Unit,
    private val onQuantityChanged: (productId: Int, quantity: Int) -> Unit
) : RecyclerView.Adapter<MenuViewHolder>() {

    private var products: List<Product> = emptyList()

    private var quantities: Map<Int, Int> = emptyMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MenuViewHolder(binding, onAddClick, onQuantityChanged)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val product = products[position]
        val selectedQuantity = quantities[product.id] ?: 0
        holder.bind(product, selectedQuantity)
    }

    override fun getItemCount(): Int = products.size

    fun submitList(newList: List<Product>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = products.size
            override fun getNewListSize() = newList.size

            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
                return products[oldPos].id == newList[newPos].id
            }

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
                return products[oldPos] == newList[newPos]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        products = newList
        diffResult.dispatchUpdatesTo(this)
    }
}
