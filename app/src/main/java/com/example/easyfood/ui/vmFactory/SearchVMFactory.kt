package com.example.easyfood.ui.vmFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easyfood.remote.repository.SearchRepository
import com.example.easyfood.ui.vm.SearchVM

class SearchVMFactory(private val repository: SearchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchVM(repository = repository) as T
    }
}