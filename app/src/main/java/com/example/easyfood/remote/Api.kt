package com.example.easyfood.remote

import com.example.easyfood.categoryModel.CategoriesModel
import com.example.easyfood.randomModel.MealList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("random.php")
    suspend fun getRandomMeals(): Response<MealList>

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoriesModel>

    @GET("lookup.php")
    suspend fun getMealDetails(
        @Query("i") id: String
    ): Response<MealList>

    @GET("filter.php")
    suspend fun getPopulars(
        @Query("c") category: String
    ): Response<MealList>

    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") mealName: String
    ): Response<MealList>
}