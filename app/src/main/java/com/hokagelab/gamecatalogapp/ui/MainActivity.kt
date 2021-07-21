package com.hokagelab.gamecatalogapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.hokagelab.gamecatalogapp.R
import com.hokagelab.gamecatalogapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = binding?.navigationView
        val navigationHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_host_fragment_activity_main) as NavHostFragment
        if (bottomNavigationView != null) {
            NavigationUI.setupWithNavController(
                bottomNavigationView,
                navigationHostFragment.navController
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_setting, menu)

        menu.findItem(R.id.actionSetting)?.intent  = Intent(Intent.ACTION_VIEW, Uri.parse("gamecatalogapp://actionsetting"))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}