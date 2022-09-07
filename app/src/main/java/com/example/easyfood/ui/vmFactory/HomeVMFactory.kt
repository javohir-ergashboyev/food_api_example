package com.example.easyfood.ui.vmFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easyfood.remote.repository.CategoryRepository
import com.example.easyfood.remote.repository.PopularRepository
import com.example.easyfood.remote.repository.RandomMealRepository
import com.example.easyfood.ui.vm.HomeVM

class HomeVMFactory(
    private val mealRepository: RandomMealRepository,
    private val categoryRepository: CategoryRepository,
    private val popularRepository: PopularRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeVM(
            mealRepository = mealRepository,
            categoryRepository = categoryRepository,
            popularRepository = popularRepository
        ) as T
    }
}