package com.example.easyfood.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites"
)
data class FavoritesModel(
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    val mealName:String,
    val mealId:String,
    val mealCategory:String,
    val mealImage:String,
    val mealDescription:String
)
