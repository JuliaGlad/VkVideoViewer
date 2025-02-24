package myapplication.android.vkvideoviewer.presentation.videos.video.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviReducer
import myapplication.android.vkvideoviewer.presentation.videos.model.VideoUiList
import java.util.Locale

class VideoReducer : MviReducer<
        VideoPartialState,
        VideoState> {
    override fun reduce(
        prevState: VideoState,
        partialState: VideoPartialState
    ): VideoState =
        when (partialState) {
            is VideoPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is VideoPartialState.Error -> updateError(prevState, partialState.throwable)
            VideoPartialState.Init -> updateInit(prevState)
            VideoPartialState.Loading -> updateLoading(prevState)
            is VideoPartialState.DataByQueryLoaded -> updateDataByQuery(prevState, partialState.query)
        }

    private fun updateDataByQuery(prevState: VideoState, query: String): VideoState =
        prevState.copy(
            ui = when (val state = prevState.ui) {
                is LceState.Content -> state.copy(
                    data = VideoUiList(
                        savedItems = state.data.savedItems,
                        items = if (query.isEmpty()) {
                            state.data.savedItems
                        } else {
                            state.data.savedItems.filter { item ->
                                item.title.lowercase(Locale.getDefault())
                                    .contains(query.lowercase(Locale.getDefault()))
                            }
                        },
                        isSearch = true
                    )
                )
                is LceState.Error -> state
                LceState.Loading -> state
            }
        )

    private fun updateInit(prevState: VideoState) = prevState.copy(page = 0)

    private fun updateDataLoaded(prevState: VideoState, data: VideoUiList) =
        prevState.copy(
            ui = LceState.Content(data),
            page = prevState.page + 1)

    private fun updateLoading(prevState: VideoState) =
        prevState.copy(ui = LceState.Loading)

    private fun updateError(prevState: VideoState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable))
}