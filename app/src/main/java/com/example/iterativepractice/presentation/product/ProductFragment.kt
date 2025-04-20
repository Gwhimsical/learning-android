package com.example.iterativepractice.presentation.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iterativepractice.databinding.FragmentProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ProductAdapter(
            onUpdate = { viewModel.updateProduct(it) },
            onDelete = { viewModel.deleteProduct(it) }
        )

        binding.productRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.productRecycler.adapter = adapter

        binding.addButton.setOnClickListener {
            val name = binding.inputName.text.toString()
            val price = binding.inputPrice.text.toString().toDoubleOrNull()

            if (name.isNotBlank() && price != null) {
                viewModel.addProduct(name, price)
                binding.inputName.text.clear()
                binding.inputPrice.text.clear()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products.collect { productList ->
                adapter.submitList(productList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
