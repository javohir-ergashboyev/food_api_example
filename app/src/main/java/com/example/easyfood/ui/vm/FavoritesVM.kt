package com.example.easyfood.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.db.FavoritesModel
import com.example.easyfood.remote.repository.FavoritesRepository
import kotlinx.coroutines.launch

class FavoritesVM(private val repository: FavoritesRepository) : ViewModel() {

    fun saveMeal(meal: FavoritesModel) = viewModelScope.launch {
        repository.insertMeal(meal)
    }

    fun deleteMeal(meal: FavoritesModel) = viewModelScope.launch {
        repository.deleteMeal(meal)
    }

    fun getFavorites() = repository.getAllFavoriteMeal()
}