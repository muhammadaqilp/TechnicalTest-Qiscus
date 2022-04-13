package com.example.technicaltest_qiscus.util

import android.content.Context
import android.content.SharedPreferences
import com.example.technicaltest_qiscus.domain.model.User
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class UserPreferences(context: Context) {

    var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user", Context.MODE_PRIVATE)
    private var gson: Gson =
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    fun getCurrentUser(): User {
        return gson.fromJson(sharedPreferences.getString(CURRENT_USER, ""), User::class.java)
    }

    fun setCurrentUser(user: User) {
        sharedPreferences.edit()
            .putString(CURRENT_USER, gson.toJson(user))
            .apply()
    }

    fun setCurrentDeviceToken(token: String) {
        sharedPreferences.edit().putString(CURRENT_DEVICE_TOKEN, token).apply()
    }

    fun getCurrentDeviceToken(): String? {
        return sharedPreferences.getString(CURRENT_DEVICE_TOKEN, "")
    }

    companion object {
        const val CURRENT_USER = "current_user"
        const val CURRENT_DEVICE_TOKEN = "current_device_token"
    }

}