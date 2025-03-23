package com.example.apitest.data.local.preferences

import android.content.Context
import android.content.SharedPreferences




class CityPreferences(context:Context) {

    private val sharedPreferences : SharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

    fun saveCityId(cityId: Long){
        val editor = sharedPreferences.edit()
        editor.putLong(KEY_CITY_ID, cityId)
        editor.apply()
    }

    fun getCityId(): Long?{
        val id = sharedPreferences.getLong(KEY_CITY_ID, INVALID_VALUE)
        return if (id != INVALID_VALUE) id else null
    }

    fun clearCityId(){
        val editor = sharedPreferences.edit()
        editor.remove(KEY_CITY_ID)
        editor.apply()
    }

    companion object {
        private const val PREFS_NAME = "CityIdPrefs"
        private const val KEY_CITY_ID = "city_id"
        private const val INVALID_VALUE = -1L
    }
}