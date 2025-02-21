package myapplication.android.vkvideoviewer.presentation.player

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import myapplication.android.vkvideoviewer.R
import myapplication.android.vkvideoviewer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    private var _binding: ActivityPlayerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(android.R.transition.move)
    }

    fun transition(){
        binding.root.transitionToEnd()
        val fragment: Fragment? =
            supportFragmentManager.fragments[0]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}