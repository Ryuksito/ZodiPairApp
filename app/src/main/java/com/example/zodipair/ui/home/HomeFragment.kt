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
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var sliderImageView: ImageView
    private lateinit var reactionImageView: ImageView
    private val apiManager = ApiManager()
    private var imageList: MutableList<String> = mutableListOf()
    private val cantUsers = 5

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        reactionImageView = view.findViewById(R.id.centerReactionIcon)
        sliderImageView = view.findViewById(R.id.sliderImageView)
        setupGestures(sliderImageView)
        fetchImages()
        return view
    }

    private fun fetchImages() {
        lifecycleScope.launch {
            try {
                val users = apiManager.getRandomUsers(cantUsers) // Pide 5 imágenes
                imageList.addAll(users.map { it.url_image }) // Añadir imágenes a la lista
                if (sliderImageView.drawable == null && imageList.isNotEmpty()) {
                    showImage() // Mostrar la primera imagen si ninguna está visible
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showImage() {
        if (imageList.size == 4){
            Log.d("Image Slider", "Lista casi vacia, recargando imagenes...")
            fetchImages()
        }
        if (imageList.isNotEmpty()) {
            // Mostrar la imagen actual
            val currentImage = imageList[0]
            Glide.with(this)
                .load(currentImage)
                .into(sliderImageView)

            // Eliminar la imagen mostrada de la lista
            imageList.removeAt(0)
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
                    return true
                }
                return super.onFling(e1, e2, velocityX, velocityY)
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                activateHeartAnimation(R.drawable.icon_hot_heart)
                animateZoomOut()
                return super.onDoubleTap(e)
            }
        })

        view.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
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
