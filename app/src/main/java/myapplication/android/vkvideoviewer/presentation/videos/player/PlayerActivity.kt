package myapplication.android.vkvideoviewer.presentation.videos.player

import android.R
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.appcompat.app.AppCompatActivity
import myapplication.android.vkvideoviewer.databinding.ActivityPlayerBinding


class PlayerActivity : AppCompatActivity() {

    private var _binding: ActivityPlayerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.move)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}