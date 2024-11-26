package com.example.zodipair.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.zodipair.R
import com.example.zodipair.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ToolBar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // NavBar
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        navView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_matches, R.id.navigation_home, R.id.navigation_chat
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)

        // Aplica el paddingTop para respetar la barra de estado
        val statusBarHeight = getStatusBarHeight()
        navigationView.setPadding(0, statusBarHeight, 0, 0)

        // Configurar clic en la imagen de perfil para abrir el drawer
        val profileButton = findViewById<android.widget.ImageView>(R.id.ivProfile)
        profileButton.setOnClickListener {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START)
            } else {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        // Referenciar vistas personalizadas dentro del NavigationView
        val profileImage = navigationView.findViewById<ImageView>(R.id.drawer_profile_image)
        val username = navigationView.findViewById<TextView>(R.id.drawer_username)
        val changeThemeButton = navigationView.findViewById<Button>(R.id.drawer_change_theme)
        val settingsButton = navigationView.findViewById<Button>(R.id.drawer_settings)

        // Configurar acciones para los elementos personalizados
        profileImage.setOnClickListener {
            // Acción para la imagen de perfil
        }

        changeThemeButton.setOnClickListener {
            // Acción para cambiar tema
        }

        settingsButton.setOnClickListener {
            // Acción para configuraciones
        }

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
