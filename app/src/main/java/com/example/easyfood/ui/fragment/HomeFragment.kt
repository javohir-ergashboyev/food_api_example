package com.example.easyfood.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.adapter.CategoryAdapter
import com.example.easyfood.adapter.PopularAdapter
import com.example.easyfood.databinding.FragmentHomeBinding
import com.example.easyfood.networkService.CheckNetworkConnectivity
import com.example.easyfood.randomModel.Meal
import com.example.easyfood.resource.Resource
import com.example.easyfood.ui.activity.DetailActivity
import com.example.easyfood.ui.activity.MainActivity
import com.example.easyfood.ui.activity.SearchActivity
import com.example.easyfood.ui.vm.HomeVM

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: HomeVM
    lateinit var randomMeal: Meal
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var popularAdapter: PopularAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        categoryAdapter = CategoryAdapter(requireContext())
        binding.rvCategories.adapter = categoryAdapter
        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(), 3)

        popularAdapter = PopularAdapter(requireContext())
        binding.rvPopular.adapter = popularAdapter
        binding.rvPopular.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)


        binding.randomImage.setOnClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("meal_img", randomMeal.strMealThumb)
            intent.putExtra("meal_title", randomMeal.strMeal)
            intent.putExtra("meal_id", randomMeal.idMeal)
            startActivity(intent)
        }

        val checkNetwork = CheckNetworkConnectivity(requireContext())
        checkNetwork.observe(requireActivity()) {
            if (it) {
                viewModel.getRandomMealList()
                viewModel.getCategories()
                viewModel.getPopulars("beef")
            }
        }

        //Random Meal
        viewModel.randomMeal.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    pbRemove(binding)
                    resource.data?.let {
                        Glide.with(requireContext()).load(it.meals[0].strMealThumb)
                            .into(binding.randomImage)

                        this.randomMeal = it.meals[0]
                    }
                }
                is Resource.Error -> {
                    pbRemove(binding)
                    Toast.makeText(requireContext(), "OPS something wrong", Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Loading -> {
                    pbShow(binding)
                }
            }
        }


        //Category
        viewModel.category.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        categoryAdapter.differ.submitList(it.categories)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "OPS something wrong", Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Loading -> {
                }
            }
        }

        //popular meals
        viewModel.populars.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        popularAdapter.differ.submitList(it.meals)

                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "OPS something wrong", Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Loading -> {
                }
            }
        }
        binding.goToSearch.setOnClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }

    private fun pbShow(binding: FragmentHomeBinding) {
        binding.pbRandomMeal.visibility = View.VISIBLE
    }

    private fun pbRemove(binding: FragmentHomeBinding) {
        binding.pbRandomMeal.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}