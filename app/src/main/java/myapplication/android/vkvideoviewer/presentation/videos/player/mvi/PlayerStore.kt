package myapplication.android.vkvideoviewer.presentation.videos.player.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviStore

class PlayerStore(
    reducer: PlayerReducer,
    actor: PlayerActor
): MviStore<
        PlayerPartialState,
        PlayerIntent,
        PlayerState,
        PlayerEffect>(reducer, actor) {
    override fun initialStateCreator(): PlayerState =
        PlayerState(ui = LceState.Loading)
}