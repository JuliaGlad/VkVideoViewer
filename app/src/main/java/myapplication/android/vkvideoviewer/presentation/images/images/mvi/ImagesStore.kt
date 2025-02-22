package myapplication.android.vkvideoviewer.presentation.images.images.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviStore

class ImagesStore(
    reducer: ImagesReducer,
    actor: ImagesActor
): MviStore<
        ImagesPartialState,
        ImagesIntent,
        ImagesState,
        ImagesEffect>(reducer, actor) {
    override fun initialStateCreator(): ImagesState = ImagesState(ui = LceState.Loading)
}