package myapplication.android.vkvideoviewer.presentation.video

import android.os.Bundle
import androidx.fragment.app.Fragment
import myapplication.android.vkvideoviewer.di.DaggerAppComponent
import myapplication.android.vkvideoviewer.di.component.fragment.video.DaggerVideoFragmentComponent

class VideoFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerVideoFragmentComponent.factory().create(appComponent).inject(this)
    }

}