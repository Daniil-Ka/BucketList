package com.tms.bucketlist.ui.profile


/**import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.github.javafaker.Faker
import com.github.javafaker.PhoneNumber
import com.tms.bucketlist.domain.Category
import com.tms.bucketlist.domain.Privacy
import com.tms.bucketlist.domain.Target
*/

/** Хранит информацию о локальном пользователе и все его данные на устройстве */
/**var name: String,
    var age: String,
    var sex: String,
    var email: String,
    var phoneNumber: String,
    var photo: Drawable?,
    var data: SharedPreferences?,
*/

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable


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
        get() = sharedPreferences.getInt("name", 0)
        set(value) {
            sharedPreferences.edit().putInt("name", value).apply()
        }

    var sex: String
        get() = sharedPreferences.getString("sex", "") ?: ""
        set(value) {
            sharedPreferences.edit().putString("sex", value).apply()
        }

    var enail: String
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