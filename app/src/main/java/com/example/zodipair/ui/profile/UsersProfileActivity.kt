package com.example.zodipair.ui.profile

import ImageGalleryAdapter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zodipair.GlideApp
import com.example.zodipair.R
import com.example.zodipair.domain.models.GetProfileModel
import com.example.zodipair.utils.ImageAdapter

class UsersProfileActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageGalleryAdapter


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val profile = intent.getParcelableExtra("PROFILE_DATA", GetProfileModel::class.java)
        val userName = intent.getStringExtra("USER_NAME")

        profile?.let {
            // Rellena los datos del perfil
            findViewById<TextView>(R.id.user_name).text = userName
            findViewById<TextView>(R.id.user_age).text = it.age.toString()

            GlideApp.with(this)
                .load(it.img)
                .circleCrop()
                .into(findViewById(R.id.drawer_profile_image))

            findViewById<ImageView>(R.id.user_icon).setImageResource(getZodiacIcon(profile.zodiac_symbol))
            findViewById<ImageView>(R.id.icon_gender).setImageResource(getGenderIcon(profile.gender))
            findViewById<ImageView>(R.id.icon_target_gender).setImageResource(getGenderIcon(profile.target_gender))

            val images = profile.imgs
            val recyclerView: RecyclerView = findViewById(R.id.gallery_recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(this) // Configuración en lista vertical
            recyclerView.adapter = images?.let { it1 -> ImageAdapter(it1) }
        }

    }

    private fun getGenderIcon(gender: String): Int{
        return when (gender) {
            "male" -> R.drawable.mars_36
            "female" -> R.drawable.venus_36
            else -> R.drawable.mars_36
        }
    }

    private fun getZodiacIcon(zodiac: String): Int {
        return when (zodiac.lowercase()) {
            "aries" -> R.drawable.aries
            "taurus" -> R.drawable.taurus
            "gemini" -> R.drawable.gemini
            "cancer" -> R.drawable.cancer
            "leo" -> R.drawable.leo
            "virgo" -> R.drawable.virgo
            "libra" -> R.drawable.libra
            "scorpio" -> R.drawable.scorpion
            "sagittarius" -> R.drawable.sagittarius
            "capricorn" -> R.drawable.capricorn
            "aquarius" -> R.drawable.aquarius
            "pisces" -> R.drawable.pisces
            else -> R.drawable.aries
        }
    }
}