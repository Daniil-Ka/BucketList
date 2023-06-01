package com.tms.bucketlist.ui.profile
import android.content.Context
import android.content.SharedPreferences


class ProfileData private constructor(context: Context){
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("profileData", Context.MODE_PRIVATE)

    companion object {
        private var instance: ProfileData? = null

        fun getInstance(context: Context): ProfileData {
            return instance ?: synchronized(this) {
                instance ?: ProfileData(context).also { instance = it }
            }
        }
    }

    var name: String
        get() = sharedPreferences.getString("name", "") ?: ""
        set(value) {
            sharedPreferences.edit().putString("name", value).apply()
        }

    var age: Int
        get() = sharedPreferences.getInt("age", 0)
        set(value) {
            sharedPreferences.edit().putInt("age", value).apply()
        }

    var sex: String
        get() = sharedPreferences.getString("sex", "") ?: ""
        set(value) {
            sharedPreferences.edit().putString("sex", value).apply()
        }

    var email: String
        get() = sharedPreferences.getString("email", "") ?: ""
        set(value) {
            sharedPreferences.edit().putString("email", value).apply()
        }

    var phoneNumber: String
        get() = sharedPreferences.getString("phoneNumber", "") ?: ""
        set(value) {
            sharedPreferences.edit().putString("phoneNumber", value).apply()
        }
}