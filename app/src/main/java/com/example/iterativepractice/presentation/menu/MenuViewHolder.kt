package com.example.iterativepractice.presentation.menu

import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.iterativepractice.data.local.product.Product
import com.example.iterativepractice.databinding.MenuItemBinding
import java.text.NumberFormat
import java.util.Locale

class MenuViewHolder(
    private val binding: MenuItemBinding,
    private val onAddClick: (product: Product, quantity: Int) -> Unit,
    private val onQuantityChanged: (productId: Int, quantity: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product, selectedQuantity: Int) {
        binding.tvProductName.text = product.name

        val formattedPrice =
            NumberFormat.getCurrencyInstance(Locale.getDefault()).format(product.price)
        binding.tvProductPrice.text = formattedPrice

        setupQuantitySpinner(product, selectedQuantity)

        binding.btnAddToCart.setOnClickListener {
            val quantity = binding.spinnerQuantity.selectedItem as Int
            onAddClick(product, quantity)
        }
    }

    private fun setupQuantitySpinner(product: Product, selectedQuantity: Int) {
        val qtyOptions = (0..5).toList()
        val spinnerAdapter = ArrayAdapter(
            binding.root.context,
            android.R.layout.simple_spinner_item,
            qtyOptions
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerQuantity.adapter = spinnerAdapter

        val position = qtyOptions.indexOf(selectedQuantity)
        if (position != -1) {
            binding.spinnerQuantity.setSelection(position)
        }

        binding.spinnerQuantity.onItemSelectedListener = null

        binding.spinnerQuantity.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    val quantity = parent?.getItemAtPosition(position) as Int
                    onQuantityChanged(product.id, quantity)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }
}