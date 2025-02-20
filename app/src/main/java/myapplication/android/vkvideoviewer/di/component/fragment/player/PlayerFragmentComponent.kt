package myapplication.android.vkvideoviewer.di.component.fragment.player

import dagger.Component
import myapplication.android.vkvideoviewer.di.AppComponent
import myapplication.android.vkvideoviewer.presentation.player.PlayerFragment
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

    fun inject(playerFragment: PlayerFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): PlayerFragmentComponent
    }
}

@Scope
annotation class PlayerFragmentScope