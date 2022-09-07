package com.example.easyfood.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.categoryModel.CategoriesModel
import com.example.easyfood.remote.repository.CategoryRepository
import com.example.easyfood.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class CategoryVM(private val repository: CategoryRepository) : ViewModel() {

    val category: MutableLiveData<Resource<CategoriesModel>> = MutableLiveData()

    fun getCategories() = viewModelScope.launch {
        category.postValue(Resource.Loading())
        val response = repository.getCategories()
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

}