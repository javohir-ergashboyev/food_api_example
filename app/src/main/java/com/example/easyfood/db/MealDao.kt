package com.example.easyfood.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: FavoritesModel)

    @Delete()
    suspend fun deleteMeal(meal: FavoritesModel)

    @Query("SELECT * FROM favorites")
    fun getAllFavoriteMeal(): LiveData<List<FavoritesModel>>

}