package com.example.apitest.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.apitest.data.local.dao.CitiesDao
import com.example.apitest.data.local.entities.CitiesDbEntity

@Database(
    version = 1,
    entities = [
        CitiesDbEntity::class
    ]
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun getCitiesDao(): CitiesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDataBaseOrNull() = INSTANCE

        fun getDataBase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cities_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}