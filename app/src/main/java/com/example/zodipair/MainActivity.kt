package com.example.zodipair

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.zodipair.data.UserSessionManager
import com.example.zodipair.domain.use_cases.ApiManager
import com.example.zodipair.ui.home.HomeActivity
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


        lifecycleScope.launch {
            val getUser = apiManager.postUserValidation(userName, password)
            if(getUser.id != ""){
                val getProfile = apiManager.postGetProfile(getUser.id)
                UserSessionManager.setUserSession(getUser)
                UserSessionManager.setUserProfile(getProfile)

                val intent = Intent(this@MainActivity, HomeActivity::class.java);
                startActivity(intent);
            }else{
                Toast.makeText(this@MainActivity, "Incorrect User", Toast.LENGTH_SHORT).show()
            }
        }

    }
}