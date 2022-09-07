package com.example.easyfood.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.easyfood.adapter.CategoryMainAdapter
import com.example.easyfood.databinding.FragmentCategoryBinding
import com.example.easyfood.networkService.CheckNetworkConnectivity
import com.example.easyfood.resource.Resource
import com.example.easyfood.ui.activity.MainActivity
import com.example.easyfood.ui.vm.CategoryVM

class CategoryFragment : Fragment() {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: CategoryVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentCategoryBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CategoryMainAdapter(requireContext())
        binding.rvCategoriesFragment.adapter = adapter

        viewModel = (activity as MainActivity).viewModelCategory
        val checkNetwork = CheckNetworkConnectivity(requireContext())
        checkNetwork.observe(requireActivity()) {
            if (it) {
                viewModel.getCategories()
            }
        }
        viewModel.category.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Success -> {
                    pbRemove(binding)
                    resources.data?.let {
                        adapter.differ.submitList(resources.data.categories)
                    }
                }
                is Resource.Error -> {
                    pbRemove(binding)
                    Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Loading -> {
                    pbShow(binding)
                }
            }
        }

    }

    private fun pbShow(binding: FragmentCategoryBinding) {
        binding.pbCategories.visibility = View.VISIBLE
    }

    private fun pbRemove(binding: FragmentCategoryBinding) {
        binding.pbCategories.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}