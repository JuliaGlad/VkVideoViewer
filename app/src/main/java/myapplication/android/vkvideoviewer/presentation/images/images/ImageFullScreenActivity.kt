package myapplication.android.vkvideoviewer.presentation.images.images

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import myapplication.android.vkvideoviewer.R
import myapplication.android.vkvideoviewer.databinding.ActivityImageFullScreenBinding

class ImageFullScreenActivity : AppCompatActivity() {

    private var _binding: ActivityImageFullScreenBinding? = null
    private val binding get()= _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityImageFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(android.R.transition.move)

        Glide.with(this)
            .load(intent.getStringExtra(IMAGE_URL)!!.toUri())
            .into(binding.photoView)

        binding.toolbar.setNavigationOnClickListener { finish() }
        toggleFullScreen()
    }

    private fun toggleFullScreen() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.apply {
                hide(WindowInsets.Type.systemBars())
                systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val IMAGE_URL = "ImageUrlId"
    }

}