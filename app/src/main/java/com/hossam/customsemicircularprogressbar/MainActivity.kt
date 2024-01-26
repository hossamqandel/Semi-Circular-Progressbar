package com.hossam.customsemicircularprogressbar

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hossam.customsemicircularprogressbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding){
            customProgressBar.setProgressWidth(30f)
            customProgressBar.setProgressGradient(Color.parseColor("#194374"), Color.WHITE)
            customProgressBar.setMax(5000.0)
            customProgressBar.setProgressWithAnimation(3500.0, 1200)
        }

    }

}