package com.example.easyfood.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [FavoritesModel::class],
    version = 1
)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun getFavoriteDao(): MealDao

    companion object {
        @Volatile
        private var instance: FavoritesDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FavoritesDatabase::class.java,
                "favorites_db"
            ).build()
    }

}