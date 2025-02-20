package myapplication.android.vkvideoviewer.presentation.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import myapplication.android.vkvideoviewer.R
import myapplication.android.vkvideoviewer.app.Constants
import myapplication.android.vkvideoviewer.databinding.ActivityMainBinding
import myapplication.android.vkvideoviewer.di.DaggerAppComponent
import myapplication.android.vkvideoviewer.di.component.activity.DaggerMainActivityComponent
import myapplication.android.vkvideoviewer.presentation.player.PlayerActivity

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = DaggerAppComponent.factory().create(this)
        DaggerMainActivityComponent.factory().create(appComponent).inject(this)
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
            putExtra(Constants.VIDEO_ID, videoId)
            putExtra(Constants.VIDEO_PAGE, videoPage)
            putExtra(Constants.VIDEO_TITLE, title)
            putExtra(Constants.VIDEO_VIEWS, views)
            putExtra(Constants.VIDEO_DOWNLOADS, downloads)
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
}