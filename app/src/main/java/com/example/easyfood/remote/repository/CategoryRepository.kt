package com.example.easyfood.remote.repository

import com.example.easyfood.categoryModel.CategoriesModel
import com.example.easyfood.remote.RetrofitInstance
import retrofit2.Response

class CategoryRepository {
    suspend fun getCategories(): Response<CategoriesModel> =
        RetrofitInstance.getApiService().getCategories()
}