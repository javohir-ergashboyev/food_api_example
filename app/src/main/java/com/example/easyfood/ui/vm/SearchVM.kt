package com.example.easyfood.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.randomModel.MealList
import com.example.easyfood.remote.repository.SearchRepository
import com.example.easyfood.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchVM(private val repository: SearchRepository) : ViewModel() {
    val searchLiveData: MutableLiveData<Resource<MealList>> = MutableLiveData()

    fun searchMeal(name: String) = viewModelScope.launch {
        searchLiveData.postValue(Resource.Loading())
        val response = repository.searchMeals(name)
        searchLiveData.postValue(handleSearchResponse(response))
    }

    private fun handleSearchResponse(response: Response<MealList>): Resource<MealList> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}