package com.example.easyfood.ui.vmFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easyfood.remote.repository.DetailRepository
import com.example.easyfood.ui.vm.DetailVM

class DetailVMFactory(private val repository: DetailRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailVM(repository = repository) as T
    }
}