package com.dk.myweatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dk.myweatherapp.databinding.ActivityMainBinding
import com.dk.myweatherapp.view.WeatherListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, WeatherListFragment.newInstance())
                .commit()
        }
    }

}