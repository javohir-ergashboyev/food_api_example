package com.example.easyfood.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.adapter.MealAdapter
import com.example.easyfood.databinding.ActivityMealsBinding
import com.example.easyfood.remote.repository.PopularRepository
import com.example.easyfood.resource.Resource
import com.example.easyfood.ui.vm.MealVM
import com.example.easyfood.ui.vmFactory.MealVMFactory

class MealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealsBinding
    private lateinit var adapter: MealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        adapter = MealAdapter(this)
        binding.rvMeals.adapter = adapter
        binding.rvMeals.layoutManager = GridLayoutManager(this, 2)

        val repository = PopularRepository()
        val vmFactory = MealVMFactory(repository)
        val viewModel = ViewModelProvider(this, vmFactory)[MealVM::class.java]

        viewModel.getMeals(intent.getStringExtra("cat_name").toString())
        viewModel.meals.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        adapter.differ.submitList(it.meals)
                    }
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {
                }
            }
        }
    }

}