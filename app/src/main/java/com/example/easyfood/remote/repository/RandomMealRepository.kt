package com.example.easyfood.remote.repository

import com.example.easyfood.randomModel.MealList
import com.example.easyfood.remote.RetrofitInstance
import retrofit2.Response

class RandomMealRepository {
    suspend fun getRandomMeal(): Response<MealList> =
        RetrofitInstance.getApiService().getRandomMeals()
}