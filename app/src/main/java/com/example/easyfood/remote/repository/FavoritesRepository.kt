package com.example.easyfood.remote.repository

import com.example.easyfood.db.FavoritesDatabase
import com.example.easyfood.db.FavoritesModel

class FavoritesRepository(val db: FavoritesDatabase) {

    suspend fun insertMeal(meal: FavoritesModel) = db.getFavoriteDao().insertMeal(meal)
    suspend fun deleteMeal(meal: FavoritesModel) = db.getFavoriteDao().deleteMeal(meal)
    fun getAllFavoriteMeal() = db.getFavoriteDao().getAllFavoriteMeal()
}