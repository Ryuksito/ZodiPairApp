package com.example.zodipair

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.zodipair.data.UserSessionManager
import com.example.zodipair.domain.use_cases.ApiManager
import com.example.zodipair.ui.home.HomeActivity
import com.example.zodipair.ui.user_validation.LoginActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val apiManager = ApiManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userName = "Leo Messi"
        val password = "password"

        val intent = Intent(this, LoginActivity::class.java);
        startActivity(intent);
        finish()

    }
}