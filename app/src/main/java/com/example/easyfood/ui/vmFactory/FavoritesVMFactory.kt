package com.example.easyfood.ui.vmFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easyfood.remote.repository.FavoritesRepository
import com.example.easyfood.ui.vm.FavoritesVM

class FavoritesVMFactory(private val repository: FavoritesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoritesVM(repository = repository) as T
    }
}