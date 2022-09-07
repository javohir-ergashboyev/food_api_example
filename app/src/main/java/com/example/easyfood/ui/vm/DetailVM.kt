package com.example.easyfood.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.randomModel.MealList
import com.example.easyfood.remote.repository.DetailRepository
import com.example.easyfood.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailVM(private val repository: DetailRepository) : ViewModel() {
    val currentMeal: MutableLiveData<Resource<MealList>> = MutableLiveData()

    fun getMealDetails(id: String) = viewModelScope.launch {
        currentMeal.postValue(Resource.Loading())
        val response = repository.getRandomMeal(id)
        currentMeal.postValue(handleMealResponse(response))
    }

    private fun handleMealResponse(response: Response<MealList>): Resource<MealList> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}