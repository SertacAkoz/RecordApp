package com.korucu.exampleapp.Utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class CustomSharedPreferences {

    companion object{
        private val TOKEN = "token"
        private var sharedPreferences:SharedPreferences? = null

        @Volatile private var instance:CustomSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context) : CustomSharedPreferences = instance ?: synchronized(lock){
            instance ?: createCustomSharedPreferences(context).also {
                instance = it
            }
        }
        private fun createCustomSharedPreferences(context: Context):CustomSharedPreferences{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }

    fun saveToken(tokenString:String){
        sharedPreferences?.edit(commit = true){
            println("SP-saveToken-tokenString --> ${tokenString}")
            putString(TOKEN,tokenString)
        }
    }

    fun getToken() = sharedPreferences?.getString(TOKEN,"null")


}