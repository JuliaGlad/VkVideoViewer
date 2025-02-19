package myapplication.android.vkvideoviewer.di.component.fragment.video

import dagger.Component
import myapplication.android.vkvideoviewer.di.AppComponent
import myapplication.android.vkvideoviewer.presentation.video.VideoFragment
import javax.inject.Scope

@VideoFragmentScope
@Component(
    modules = [VideoFragmentModule::class],
    dependencies = [AppComponent::class]
)
interface VideoFragmentComponent {

    fun inject(videoFragment: VideoFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): VideoFragmentComponent
    }
}

@Scope
annotation class VideoFragmentScope