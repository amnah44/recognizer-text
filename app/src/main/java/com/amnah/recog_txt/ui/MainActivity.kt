package com.amnah.recog_txt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.amnah.recog_txt.R
import com.amnah.recog_txt.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashScreen)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)


    }

    override fun onResume() {
        super.onResume()
        val navController = findNavController(R.id.parentContainer)
        _binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.parentContainer).navigateUp()
        return true
    }

}