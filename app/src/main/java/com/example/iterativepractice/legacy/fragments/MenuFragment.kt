package com.example.iterativepractice.legacy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iterativepractice.R
import com.example.iterativepractice.legacy.model.MenuItemsAdapter
import com.example.iterativepractice.legacy.model.MenuViewModel
import com.example.iterativepractice.legacy.dialog.OrderCompleteBottomSheet
//import com.example.iterativepractice.dialog.OrderCompleteListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MenuFragment : Fragment()/*, OrderCompleteListener*/ {

    private val viewModel: MenuViewModel by viewModels()

    private lateinit var menuAdapter: MenuItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val menuView = inflater.inflate(R.layout.fragment_menu_deprecated, container, false)
        return menuView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        menuAdapter = MenuItemsAdapter(
            onDeleteClicked = { menuItem ->
                viewModel.deleteItem(menuItem)
            }
        )
        
        recyclerView.adapter = menuAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.menuItemsFlow.collect {
                menuItems -> menuAdapter.submitList(menuItems)
            }
        }

        val completeOrderButton = view.findViewById<Button>(R.id.button6)
        completeOrderButton.setOnClickListener {
            val bottomSheet = OrderCompleteBottomSheet()
            bottomSheet.show(childFragmentManager, "Confirm address bottom sheet")
        }

        val addressTextView = view.findViewById<TextView>(R.id.textView2)
        childFragmentManager.setFragmentResultListener("address", this) { _, bundle ->
            val result = bundle.getString("address")
            addressTextView?.text = result
        }
    }

    /*override fun onOrderComplete(savedInstanceState: Bundle?) {
        val addressTextView = view?.findViewById<TextView>(R.id.textView2)
        childFragmentManager.setFragmentResultListener("address", this) {
            _, bundle -> val result = bundle.getString("address")
            addressTextView?.text = result
            }
    }*/
}