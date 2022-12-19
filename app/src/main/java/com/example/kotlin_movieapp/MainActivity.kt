package com.example.kotlin_movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_movieapp.databinding.ActivityMainBinding
import com.example.kotlin_movieapp.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}