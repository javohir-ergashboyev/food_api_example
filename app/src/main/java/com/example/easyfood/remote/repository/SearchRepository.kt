package com.example.easyfood.remote.repository

import com.example.easyfood.randomModel.MealList
import com.example.easyfood.remote.RetrofitInstance
import retrofit2.Response

class SearchRepository {
    suspend fun searchMeals(name: String): Response<MealList> =
        RetrofitInstance.getApiService().searchMeals(name)
}