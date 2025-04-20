package com.example.iterativepractice.presentation.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.iterativepractice.data.local.product.Product
import com.example.iterativepractice.databinding.ItemProductRowBinding

class ProductAdapter(
    private val onUpdate: (Product) -> Unit,
    private val onDelete: (Product) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    private var products: List<Product> = emptyList()

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        with(holder.binding) {
            productNameInput.setText(product.name)
            productPriceInput.setText(product.price.toString())

            updateButton.setOnClickListener {
                val updated = product.copy(
                    name = productNameInput.text.toString(),
                    price = productPriceInput.text.toString().toDoubleOrNull() ?: 0.0
                )
                onUpdate(updated)
            }

            deleteButton.setOnClickListener {
                onDelete(product)
            }
        }
    }

    override fun getItemCount(): Int = products.size
}
