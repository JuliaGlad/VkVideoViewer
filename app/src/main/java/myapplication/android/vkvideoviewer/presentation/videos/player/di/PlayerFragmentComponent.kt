package myapplication.android.vkvideoviewer.presentation.videos.player.di

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import dagger.Component
import myapplication.android.vkvideoviewer.di.AppComponent
import myapplication.android.vkvideoviewer.presentation.videos.player.PlayerFragment
import javax.inject.Scope

@PlayerFragmentScope
@Component(
    modules = [
        PlayerFragmentModule::class,
        PlayerFragmentLocalDIModule::class
    ],
    dependencies = [AppComponent::class]
)
interface PlayerFragmentComponent {

    @OptIn(UnstableApi::class)
    fun inject(playerFragment: PlayerFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): PlayerFragmentComponent
    }
}

@Scope
annotation class PlayerFragmentScope