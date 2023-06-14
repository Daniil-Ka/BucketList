package com.tms.bucketlist

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tms.bucketlist.databinding.ActivityMainBinding
import com.tms.bucketlist.domain.Target
import com.tms.bucketlist.ui.profile.ProfileData

import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_targets, R.id.navigation_chats, R.id.navigation_profile
            )
        )
        val name = findViewById<TextView>(R.id.nameTop)
        name.text = ProfileData.getInstance(baseContext).name
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        supportActionBar?.hide();

        prefs = this.getSharedPreferences("com.bucket.list", Context.MODE_PRIVATE)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    // сохранение и загрузка

    private val dataTag = "bucketlist_targets"
    override fun onStart() {
        super.onStart()
        var json: String = prefs.getString(dataTag, "[]") ?: "[]"

        var targets = Json.decodeFromString<MutableList<Target>>(json)

        TargetsRepository.instance.targets.clear()
        TargetsRepository.instance.notifyChanges()

        for (target in targets)
            TargetsRepository.instance.addTarget(target)
    }

    override fun onStop() {
        super.onStop()
        var data = TargetsRepository.instance.targets
        var json = Json.encodeToString(data)
        prefs.edit().putString(dataTag, json).apply();
    }
}