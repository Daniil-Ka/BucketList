package com.tms.bucketlist

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tms.bucketlist.databinding.ActivityMainBinding

class MainActivityV2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_v2)
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = targets_toolbar_framgent()
        transaction.replace(R.id.frames, fragment)
        transaction.commit()
    }
}