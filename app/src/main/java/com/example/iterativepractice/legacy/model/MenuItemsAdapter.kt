package com.example.iterativepractice.legacy.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.iterativepractice.R


class MenuItemsAdapter (
    private val onDeleteClicked: (MenuItem) -> Unit
) : ListAdapter<MenuItem, MenuItemsAdapter.MenuViewHolder>(MenuDiffCallback()){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.menu_item_deprecated, viewGroup, false)

        return MenuViewHolder(view, onDeleteClicked)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MenuViewHolder(view: View, private val onDeleteClicked: (MenuItem) -> Unit) : RecyclerView.ViewHolder(view) {
        private val deleteButton: Button = view.findViewById(R.id.deleteButton)
        private val nameTextView: TextView = view.findViewById(R.id.menuItemName)
        private val categoryTextView: TextView = view.findViewById(R.id.menuItemCategory)
        private val priceTextView: TextView = view.findViewById(R.id.menuItemPrice)
    // View holder should be in a separate class file!
        fun bind(menuItem: MenuItem) {
            nameTextView.text = menuItem.name
            categoryTextView.text = menuItem.category.toString()
            priceTextView.text = "${menuItem.price}"

            deleteButton.setOnClickListener {
                onDeleteClicked(menuItem)
            }
        }
    }

    /*fun removeItem(id: Int) {
        val list = currentList.toMutableList()
        list.removeIf { it.id == id }
        submitList(list)
    }*/

    class MenuDiffCallback : DiffUtil.ItemCallback<MenuItem>() {
        override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem == newItem
        }
    }
}