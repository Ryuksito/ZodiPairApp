package com.example.zodipair.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.zodipair.R
import com.example.zodipair.databinding.ActivityHomeBinding
import com.example.zodipair.ui.chats.ChatsActivity
import com.example.zodipair.ui.profile.ProfileActivity
import com.example.zodipair.ui.settings.SettingsActivity
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var drawerLayout: DrawerLayout
    private var isDarkMode: Boolean = true
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        isDarkMode = initializeTheme()

        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    private fun init() {
        // ToolBar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        // ToolBar

        // BottonNavigationBar
        val navView: BottomNavigationView = binding.navView
        navView.selectedItemId = R.id.navigation_home
        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_matches, R.id.navigation_home, R.id.navigation_chat
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_matches -> {
                    navController.navigate(R.id.navigation_matches)
                    true
                }
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    true
                }
                R.id.navigation_chat -> {
                    val intent = Intent(this, ChatsActivity::class.java)
                    startActivity(intent)
                    false
                }
                else -> false
            }
        }
        // BottonNavigationBar

        // Drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)

        val statusBarHeight = getStatusBarHeight()
        navigationView.setPadding(0, statusBarHeight, 0, 0)

        val profileButton = findViewById<android.widget.ImageView>(R.id.ivProfile)
        profileButton.setOnClickListener {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START)
            } else {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        // Button ThemeSwitch
        val themeSwitch = navigationView.findViewById<SwitchMaterial>(R.id.drawer_theme_switch)
        themeSwitch.isChecked = isDarkMode
        // Button ThemeSwitch
        // Drawer

        // OnClickListeners
        themeSwitch.setOnClickListener {
            toggleTheme()
        }
        navigationView.findViewById<LinearLayout>(R.id.row_profile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        navigationView.findViewById<LinearLayout>(R.id.row_settings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        // OnClickListeners
    }

    private fun initializeTheme(): Boolean {
        sharedPreferences = getSharedPreferences("theme_preferences", MODE_PRIVATE)
        isDarkMode = sharedPreferences.getBoolean("dark_mode", true)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        return isDarkMode
    }

    private fun toggleTheme() {
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        val newMode = if (isDarkMode) {
            AppCompatDelegate.MODE_NIGHT_NO // Cambiar a tema claro
        } else {
            AppCompatDelegate.MODE_NIGHT_YES // Cambiar a tema oscuro
        }

        sharedPreferences.edit().putBoolean("dark_mode", !isDarkMode).apply()

        AppCompatDelegate.setDefaultNightMode(newMode)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    @SuppressLint("DiscouragedApi")
    private fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }

}
