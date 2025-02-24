package myapplication.android.vkvideoviewer.presentation.images.images.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.vkvideoviewer.domain.usecase.image.GetImagesByCategoryUseCase
import myapplication.android.vkvideoviewer.domain.usecase.image.GetImagesUseCase
import myapplication.android.vkvideoviewer.presentation.images.images.mapper.toUi
import myapplication.android.vkvideoviewer.presentation.main.asyncAwait
import myapplication.android.vkvideoviewer.presentation.main.runCatchingNonCancellation
import myapplication.android.vkvideoviewer.presentation.mvi.MviActor

class ImagesActor(
    private val getImagesUseCase: GetImagesUseCase,
    private val getImagesByCategoryUseCase: GetImagesByCategoryUseCase
) : MviActor<
        ImagesPartialState,
        ImagesIntent,
        ImagesState,
        ImagesEffect>() {
    override fun resolve(
        intent: ImagesIntent,
        state: ImagesState
    ): Flow<ImagesPartialState> =
        when (intent) {
            ImagesIntent.GetImages -> loadImages(state.page + 1)
            is ImagesIntent.GetImagesByCategory -> loadImagesByCategory(
                intent.category,
                state.page + 1
            )
            ImagesIntent.Init -> init()
        }

    private fun init() = flow { emit(ImagesPartialState.Init) }

    private fun loadImagesByCategory(category: String, page: Int) =
        flow {
            kotlin.runCatching {
                getImagesByCategory(category, page)
            }.fold(
                onSuccess = { data ->
                    emit(ImagesPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(ImagesPartialState.Error(throwable))
                }
            )
        }

    private fun loadImages(page: Int) =
        flow {
            kotlin.runCatching {
                getImages(page)
            }.fold(
                onSuccess = { data ->
                    emit(ImagesPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(ImagesPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getImagesByCategory(category: String, page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getImagesByCategoryUseCase.invoke(category.lowercase(), page) }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()

    private suspend fun getImages(page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getImagesUseCase.invoke(page) }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()
}