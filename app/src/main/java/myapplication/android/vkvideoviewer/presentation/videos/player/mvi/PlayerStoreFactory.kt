package myapplication.android.vkvideoviewer.presentation.videos.player.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlayerStoreFactory(
    private val actor: PlayerActor,
    private val reducer: PlayerReducer
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerStore(reducer, actor) as T
    }

}