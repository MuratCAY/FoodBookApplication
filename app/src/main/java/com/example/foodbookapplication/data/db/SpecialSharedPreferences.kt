package com.example.foodbookapplication.data.db

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SpecialSharedPreferences {

    companion object {

        private const val TIME = "time"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: SpecialSharedPreferences? = null

        private val lock = Any()
        operator fun invoke(context: Context): SpecialSharedPreferences =
            instance ?: synchronized(lock) {
                instance ?: createSharedPreferences(context).also {
                    instance = it
                }
            }

        private fun createSharedPreferences(context: Context): SpecialSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPreferences()
        }
    }

    fun saveTime(time: Long) {
        sharedPreferences?.edit(commit = true) {
            putLong(TIME, time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(TIME, 0)
}