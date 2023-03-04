package com.example.location.kotlin.login_api_kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.location.R

object SessionManager {
    const val userNameKey = "user_name"
    const val emailKey = "email"
    const val userId = ""


        fun storeData(context: Context, name: String, email: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor?.putString(userNameKey, name)
        editor?.putString(emailKey, email)
        editor.apply()

    }

    fun getUserName(context: Context):String{
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(userNameKey, "")!!
    }

    fun getEmail(context: Context):String{
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(emailKey, "")!!
    }

    fun getUserId(context: Context):String{
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(userId, "")!!
    }
   /* fun getData(context: Context){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        val hashMap: HashMap<String,String> = HashMap()
        hashMap.put("user_name", userNameKey)

    }*/
    fun clearData(context: Context) {
        val editor = context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        ).edit()
        editor.clear()
        editor.apply()
    }


}


