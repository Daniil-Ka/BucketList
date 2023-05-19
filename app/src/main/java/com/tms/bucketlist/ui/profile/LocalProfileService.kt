package com.tms.bucketlist.ui.profile

import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.github.javafaker.Faker
import com.github.javafaker.PhoneNumber
import com.tms.bucketlist.domain.Category
import com.tms.bucketlist.domain.Privacy
import com.tms.bucketlist.domain.Target

/** Хранит информацию о локальном пользователе и все его данные на устройстве */
class LocalProfileService private constructor(
    var name: String,
    var age: String,
    var sex: String,
    var email: String,
    var phoneNumber: String,
    var photo: Drawable?,
    var data: SharedPreferences?,
) {

    companion object {
        val instance = LocalProfileService("", "", "", "", "", null, null)
    }

    init {
        //data = getSharedPreferences()
    }

    fun Update(name: String, age: String, sex: String, email: String, phoneNumber: String) {
        this.name = name
        this.age = age
        this.sex = sex
        this.email = email
        this.phoneNumber = phoneNumber
        photo = Drawable.createFromPath("res/drawable/circle.xml")
    }

}