package myapplication.android.vkvideoviewer.presentation.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import myapplication.android.vkvideoviewer.R
import myapplication.android.vkvideoviewer.databinding.ActivityMainBinding
import myapplication.android.vkvideoviewer.presentation.player.PlayerActivity

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun openPlayerActivity(
        videoId: Int,
        videoPage: Int,
        title: String,
        views: Int,
        downloads: Int
    ) {
        val intent = Intent(this, PlayerActivity::class.java).apply {
            putExtra(VIDEO_ID, videoId)
            putExtra(VIDEO_PAGE, videoPage)
            putExtra(VIDEO_TITLE, title)
            putExtra(VIDEO_VIEWS, views)
            putExtra(VIDEO_DOWNLOADS, downloads)
        }
        val options = ActivityOptions.makeCustomAnimation(
            this, R.anim.slide_in_up, R.anim.stay
        )
        startActivity(intent, options.toBundle())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val VIDEO_ID = "VideoId"
        const val VIDEO_TITLE = "VideoTitle"
        const val VIDEO_VIEWS = "VideoViews"
        const val VIDEO_DOWNLOADS = "VideoDownloads"
        const val VIDEO_PAGE = "VideoPage"
    }

}