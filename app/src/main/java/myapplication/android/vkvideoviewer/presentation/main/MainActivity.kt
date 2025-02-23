package myapplication.android.vkvideoviewer.presentation.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import myapplication.android.vkvideoviewer.App.Companion.app
import myapplication.android.vkvideoviewer.R
import myapplication.android.vkvideoviewer.databinding.ActivityMainBinding
import myapplication.android.vkvideoviewer.presentation.images.images.ImageFullScreenActivity
import myapplication.android.vkvideoviewer.presentation.videos.player.PlayerActivity

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val navigator = AppNavigator(this, R.id.main_container)
    private val presenter: MainPresenter by lazy {
        MainPresenter(
            app.router
        )
    }
    private val navigationHolder: NavigatorHolder by lazy { app.navigatorHolder }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomBar()
        if (savedInstanceState == null) {
            presenter.setupRootFragment(BottomScreen.videos())
        }
    }

    fun openImageFullScreenActivity(url: String){
        val intent = Intent(this, ImageFullScreenActivity::class.java).apply {
            putExtra(IMAGE_URL, url)
        }
        val options = ActivityOptions.makeCustomAnimation(
            this, R.anim.slide_in_up, R.anim.stay
        )
        startActivity(intent, options.toBundle())
    }

    fun openPlayerActivity(
        videoId: Int,
        videoPage: Int,
        title: String,
        views: Int,
        thumbnail: String,
        downloads: Int
    ) {
        val intent = Intent(this, PlayerActivity::class.java).apply {
            putExtra(VIDEO_ID, videoId)
            putExtra(VIDEO_PAGE, videoPage)
            putExtra(VIDEO_TITLE, title)
            putExtra(VIDEO_VIEWS, views)
            putExtra(THUMBNAIL, thumbnail)
            putExtra(VIDEO_DOWNLOADS, downloads)
        }
        val options = ActivityOptions.makeCustomAnimation(
            this, R.anim.slide_in_up, R.anim.stay
        )
        startActivity(intent, options.toBundle())
    }

    private fun initBottomBar() {
        binding.bottomNav.itemIconTintList = null
        binding.bottomNav.setOnItemSelectedListener { item ->
            val screen: Screen? = when (item.itemId) {
                R.id.action_video -> BottomScreen.videos()
                R.id.action_images -> BottomScreen.images()
                else -> null
            }
            screen?.let { presenter.navigateTo(it) }
            true
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigationHolder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val VIDEO_ID = "VideoId"
        const val VIDEO_TITLE = "VideoTitle"
        const val VIDEO_VIEWS = "VideoViews"
        const val VIDEO_DOWNLOADS = "VideoDownloads"
        const val VIDEO_PAGE = "VideoPage"
        const val IMAGE_URL = "ImageUrlId"
        const val THUMBNAIL = "Thumbnail"
    }

}