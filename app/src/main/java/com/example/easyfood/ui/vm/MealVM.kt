package com.example.easyfood.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.randomModel.MealList
import com.example.easyfood.remote.repository.PopularRepository
import com.example.easyfood.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MealVM(private val repository: PopularRepository) : ViewModel() {
    val meals: MutableLiveData<Resource<MealList>> = MutableLiveData()
    fun getMeals(category: String) = viewModelScope.launch {
        meals.postValue(Resource.Loading())
        val response = repository.getPopulars(category)
        meals.postValue(handleMealsResponse(response))
    }

    private fun handleMealsResponse(response: Response<MealList>): Resource<MealList> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}