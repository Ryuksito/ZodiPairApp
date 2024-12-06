package com.example.zodipair.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zodipair.GlideApp
import com.example.zodipair.R
import com.example.zodipair.domain.models.GetProfileModel
import com.example.zodipair.ui.profile.UsersProfileActivity

class RequestsAdapter(private val data: List<GetProfileModel>, private val userNames: List<String>, private val requestTypes: List<Boolean>) :
    RecyclerView.Adapter<RequestsAdapter.RequestViewHolder>() {

    inner class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImg: ImageView = itemView.findViewById(R.id.iconProfile)
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val age: TextView = itemView.findViewById(R.id.user_age)
        val genderIcon: ImageView = itemView.findViewById(R.id.icon_gender)
        val targetGender: ImageView = itemView.findViewById(R.id.icon_target_gender)
        val zodiacIcon: ImageView = itemView.findViewById(R.id.user_icon)
        val requestIcon: ImageView = itemView.findViewById(R.id.request_icon)
        val bodyItem: LinearLayout = itemView.findViewById(R.id.userItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_request, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val profile: GetProfileModel = data[position]
        val userName: String = userNames[position]

        // Configurar el nombre del usuario
        holder.userName.text = userName

        // Configurar la edad
        holder.age.text = profile.age.toString()

        // Cargar la imagen del perfil con Glide
        GlideApp.with(holder.itemView.context)
            .load(profile.img)
            .circleCrop()
            .into(holder.profileImg)

        // Configurar el ícono de género
        holder.genderIcon.setImageResource(
            if (profile.gender == "male") R.drawable.mars_36 else R.drawable.venus_36
        )

        // Configurar el ícono del género objetivo
        holder.targetGender.setImageResource(
            if (profile.target_gender == "male") R.drawable.mars_36 else R.drawable.venus_36
        )

        // Configurar el ícono del signo zodiacal
        holder.zodiacIcon.setImageResource(getZodiacIcon(profile.zodiac_symbol))

        holder.requestIcon.setImageResource(getRequestIcon(requestTypes[position]))

        holder.bodyItem.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, UsersProfileActivity::class.java).apply {
                putExtra("PROFILE_DATA", profile)
                putExtra("USER_NAME", userName)
            }
            context.startActivity(intent)
        }
    }

    private fun getRequestIcon(isHotHeart: Boolean): Int{
        return when(isHotHeart){
            true -> R.drawable.icon_hot_heart
            false -> R.drawable.icon_heart
        }
    }

    // Método auxiliar para obtener el recurso del ícono zodiacal
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


    override fun getItemCount(): Int = data.size
}
