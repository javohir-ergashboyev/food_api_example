package com.example.easyfood.ui.vmFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easyfood.remote.repository.PopularRepository
import com.example.easyfood.ui.vm.MealVM

class MealVMFactory(private val repository: PopularRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealVM(repository = repository) as T
    }

}