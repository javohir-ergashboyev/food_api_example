package com.example.easyfood.remote.repository

import com.example.easyfood.randomModel.MealList
import com.example.easyfood.remote.RetrofitInstance
import retrofit2.Response

class PopularRepository {
    suspend fun getPopulars(category: String): Response<MealList> =
        RetrofitInstance.getApiService().getPopulars(category)
}