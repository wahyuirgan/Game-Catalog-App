package com.hokagelab.gamecatalogapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.hokagelab.gamecatalogapp.BuildConfig
import com.hokagelab.gamecatalogapp.R
import com.hokagelab.gamecatalogapp.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.tvVersionApp?.text = getString(R.string.label_version).plus(" ").plus(BuildConfig.VERSION_NAME)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }, 2000)
    }
}