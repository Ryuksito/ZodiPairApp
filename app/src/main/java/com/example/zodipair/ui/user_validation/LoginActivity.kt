package com.example.zodipair.ui.user_validation

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.zodipair.R
import com.example.zodipair.data.UserSessionManager
import com.example.zodipair.domain.use_cases.ApiManager
import com.example.zodipair.ui.home.HomeActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val apiManager = ApiManager()
    private var isPasswordVisible = false // Estado inicial del campo de contraseña

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameField = findViewById<EditText>(R.id.username_input)
        val passwordField = findViewById<EditText>(R.id.password_input)
        val showPasswordButton = findViewById<ImageView>(R.id.show_password_button)
        val loginButton = findViewById<Button>(R.id.login_button)
        val registerButton = findViewById<TextView>(R.id.register_button)

        // Manejar el botón de "ojo" para mostrar/ocultar contraseña
        showPasswordButton.setOnClickListener {
            togglePasswordVisibility(passwordField, showPasswordButton)
        }

        loginButton.setOnClickListener {
            val userName = usernameField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (userName.isNotEmpty() && password.isNotEmpty()) {
                validateUser(userName, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            // Lógica para ir a la actividad de registro
            Toast.makeText(this, "Register button clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun togglePasswordVisibility(passwordField: EditText, showPasswordButton: ImageView) {
        if (isPasswordVisible) {
            // Cambiar a modo oculto
            passwordField.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            showPasswordButton.setImageResource(R.drawable.eye_crossed_24)
        } else {
            // Cambiar a modo visible
            passwordField.inputType = InputType.TYPE_CLASS_TEXT
            showPasswordButton.setImageResource(R.drawable.eye_24)
        }
        isPasswordVisible = !isPasswordVisible
        // Mover el cursor al final
        passwordField.setSelection(passwordField.text.length)
    }

    private fun validateUser(userName: String, password: String) {
        lifecycleScope.launch {
            try {
                val getUser = apiManager.postUserValidation(userName, password)
                Log.d("UserValidation", getUser.toString())
                if (getUser.id.isNotEmpty()) {
                    val getProfile = apiManager.postGetProfile(getUser.id)
                    Log.d("Profile", getProfile.toString())

                    if (getProfile.id != -1) {
                        UserSessionManager.setUserSession(getUser)
                        UserSessionManager.setUserProfile(getProfile)

                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish() // Finaliza esta actividad para que no pueda volver con el botón atrás
                    } else {
                        showToast("Incorrect User")
                    }
                } else {
                    showToast("Incorrect User")
                }
            } catch (e: Exception) {
                Log.e("LoginError", "Error validating user", e)
                showToast("Something went wrong, please try again")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
