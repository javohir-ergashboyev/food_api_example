package com.example.easyfood.remote.repository

import com.example.easyfood.randomModel.MealList
import com.example.easyfood.remote.RetrofitInstance
import retrofit2.Response

class DetailRepository {
    suspend fun getRandomMeal(id: String): Response<MealList> =
        RetrofitInstance.getApiService().getMealDetails(id)
}