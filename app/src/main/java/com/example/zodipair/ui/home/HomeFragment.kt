package com.example.zodipair.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.zodipair.R
import com.example.zodipair.domain.use_cases.ApiManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.example.zodipair.domain.models.RandomUsersResponse
import kotlinx.coroutines.launch
import com.example.zodipair.data.UserSessionManager
import com.example.zodipair.domain.models.Consts

class HomeFragment : Fragment() {

    private lateinit var sliderImageView: ImageView
    private lateinit var reactionImageView: ImageView
    private val apiManager = ApiManager()
    private val cantUsers = 5
    private var randUsers: MutableList<RandomUsersResponse> = mutableListOf()

    private lateinit var userNameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var iconImageView: ImageView
    private lateinit var genderImageView: ImageView
    private lateinit var targetGenderImageView: ImageView
    private lateinit var descriptionTextView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        reactionImageView = view.findViewById(R.id.centerReactionIcon)

        userNameTextView = view.findViewById(R.id.user_name)
        ageTextView = view.findViewById(R.id.age_text)
        iconImageView = view.findViewById(R.id.user_icon)
        genderImageView = view.findViewById(R.id.icon_gender)
        targetGenderImageView = view.findViewById(R.id.icon_target_gender)
        descriptionTextView = view.findViewById(R.id.user_description)

        sliderImageView = view.findViewById(R.id.sliderImageView)
        setupGestures(sliderImageView)
        fetchImages()
        return view
    }

    private fun fetchImages() {
        lifecycleScope.launch {
            Log.d("GetRandUsers", "Fetch")
            try {
                val users = apiManager.getRandomUsers(cantUsers, UserSessionManager.uuid ?: "") // Pide 5 imágenes
                randUsers.addAll(users)
                if (sliderImageView.drawable == null && randUsers.isNotEmpty()) {
                    showImage() // Mostrar la primera imagen si ninguna está visible
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun updateUserInfo(){
        val currenUser = randUsers[0]
        userNameTextView.text = currenUser.user_name
        ageTextView.text = currenUser.age.toString()
        updateZodiacIcon(currenUser)

        val genderIcon = when (currenUser.gender){
            Consts.MALE.value -> R.drawable.mars_36
            Consts.FEMALE.value -> R.drawable.venus_36
            else -> R.drawable.mars_36
        }
        genderImageView.setImageResource(genderIcon)

        val targetGenderIcon = when (currenUser.target_gender){
            Consts.MALE.value -> R.drawable.mars_36
            Consts.FEMALE.value -> R.drawable.venus_36
            else -> R.drawable.mars_36
        }
        targetGenderImageView.setImageResource(targetGenderIcon)

        descriptionTextView.text = currenUser.description

    }

    private fun updateZodiacIcon(currentUser: RandomUsersResponse) {
        val zodiacIcon = when (currentUser.zodiac_symbol) {
            Consts.ARIES.value -> R.drawable.aries
            Consts.TAURUS.value -> R.drawable.taurus
            Consts.GEMINI.value -> R.drawable.gemini
            Consts.CANCER.value -> R.drawable.cancer
            Consts.LEO.value -> R.drawable.leo
            Consts.VIRGO.value -> R.drawable.virgo
            Consts.LIBRA.value -> R.drawable.libra
            Consts.SCORPIO.value -> R.drawable.scorpion
            Consts.SAGITTARIUS.value -> R.drawable.sagittarius
            Consts.CAPRICORN.value -> R.drawable.capricorn
            Consts.AQUARIUS.value -> R.drawable.aquarius
            Consts.PISCES.value -> R.drawable.pisces
            else -> R.drawable.aries
        }
        iconImageView.setImageResource(zodiacIcon)
    }


    private fun showImage() {
        updateUserInfo()
        if (randUsers.size == 4){
            Log.d("Image Slider", "Lista casi vacia, recargando imagenes...")
            fetchImages()
        }
        if (randUsers.isNotEmpty()) {
            // Mostrar la imagen actual
            val currentImage = randUsers[0].profile_img
            Glide.with(this)
                .load(currentImage)
                .into(sliderImageView)

            // Eliminar la imagen mostrada de la lista
            randUsers.removeAt(0)
            Log.d("Image Slider", "Imagen mostrada y eliminada: $currentImage")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupGestures(view: View) {
        val gestureDetector = GestureDetector(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val deltaX = e2.x - (e1?.x ?: 0f) // Diferencia en el eje X
                if (deltaX > 100) {
                    activateHeartAnimation(R.drawable.icon_broken_heart)
                    animateSlideOut(true) // Deslizar a la derecha
                    return true
                } else if (deltaX < -100) {
                    activateHeartAnimation(R.drawable.icon_heart)
                    animateSlideOut(false) // Deslizar a la izquierda
                    updateRequests(false)
                    return true
                }
                return super.onFling(e1, e2, velocityX, velocityY)
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                activateHeartAnimation(R.drawable.icon_hot_heart)
                animateZoomOut()
                updateRequests(true)
                return super.onDoubleTap(e)
            }
        })

        view.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }

    private fun updateRequests(isHotLove: Boolean){
        lifecycleScope.launch {
            UserSessionManager.uuid?.let {
                val r = apiManager.putAddRequest(randUsers[0].user_id,
                    it, isHotLove)
                Log.d("Requests", r.toString())
            }
        }
    }

    private fun activateHeartAnimation(drawableRes: Int) {
        reactionImageView.setImageResource(drawableRes)

        reactionImageView.visibility = View.VISIBLE

        val pulseAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.pulse)
        pulseAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                reactionImageView.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        reactionImageView.startAnimation(pulseAnimation)
    }

    private fun animateZoomOut(){
        val animationOut = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out)
        animationOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: android.view.animation.Animation) {}

            override fun onAnimationEnd(animation: android.view.animation.Animation) {
                showImage()
                animateSlideIn(true)
            }

            override fun onAnimationRepeat(animation: android.view.animation.Animation) {}
        })
        sliderImageView.startAnimation(animationOut)
    }

    private fun animateSlideOut(toRight: Boolean) {
        val animationOut = if (toRight) {
            AnimationUtils.loadAnimation(requireContext(), R.anim.move_out_right)
        } else {
            AnimationUtils.loadAnimation(requireContext(), R.anim.move_out_left)
        }

        animationOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: android.view.animation.Animation) {}

            override fun onAnimationEnd(animation: android.view.animation.Animation) {
                showImage()
                animateSlideIn(!toRight)
            }

            override fun onAnimationRepeat(animation: android.view.animation.Animation) {}
        })

        sliderImageView.startAnimation(animationOut)
    }

    private fun animateSlideIn(fromRight: Boolean) {
        val animationIn = if (fromRight) {
            AnimationUtils.loadAnimation(requireContext(), R.anim.move_in_right)
        } else {
            AnimationUtils.loadAnimation(requireContext(), R.anim.move_in_left)
        }
        sliderImageView.startAnimation(animationIn)
    }
}
