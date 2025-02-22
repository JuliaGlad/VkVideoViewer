package myapplication.android.vkvideoviewer.presentation.main

import com.github.terrakok.cicerone.androidx.FragmentScreen
import myapplication.android.vkvideoviewer.presentation.images.main.ImagesMainFragment
import myapplication.android.vkvideoviewer.presentation.saved.SavedFragment
import myapplication.android.vkvideoviewer.presentation.videos.video.VideoFragment

object BottomScreen {
    fun videos() = FragmentScreen { VideoFragment() }
    fun images() = FragmentScreen { ImagesMainFragment() }
    fun saved() = FragmentScreen { SavedFragment() }
}