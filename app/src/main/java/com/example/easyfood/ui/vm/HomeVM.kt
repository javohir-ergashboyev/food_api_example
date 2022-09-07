package com.example.easyfood.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.categoryModel.CategoriesModel
import com.example.easyfood.randomModel.MealList
import com.example.easyfood.remote.repository.CategoryRepository
import com.example.easyfood.remote.repository.PopularRepository
import com.example.easyfood.remote.repository.RandomMealRepository
import com.example.easyfood.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeVM(
    private val mealRepository: RandomMealRepository,
    private val categoryRepository: CategoryRepository,
    private val popularRepository: PopularRepository
) : ViewModel() {

    var randomMeal: MutableLiveData<Resource<MealList>> = MutableLiveData()
    var category: MutableLiveData<Resource<CategoriesModel>> = MutableLiveData()
    var populars: MutableLiveData<Resource<MealList>> = MutableLiveData()

    fun getRandomMealList() = viewModelScope.launch {
        randomMeal.postValue(Resource.Loading())
        val response = mealRepository.getRandomMeal()
        randomMeal.postValue(handleRandomResponse(response))
    }

    private fun handleRandomResponse(response: Response<MealList>): Resource<MealList> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getCategories() = viewModelScope.launch {
        category.postValue(Resource.Loading())
        val response = categoryRepository.getCategories()
        category.postValue(handleCategoryResponse(response))
    }

    private fun handleCategoryResponse(response: Response<CategoriesModel>): Resource<CategoriesModel> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getPopulars(cat: String) = viewModelScope.launch {
        populars.postValue(Resource.Loading())
        val response = popularRepository.getPopulars(cat)
        populars.postValue(handlePopularsResponse(response))
    }

    private fun handlePopularsResponse(response: Response<MealList>): Resource<MealList> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}