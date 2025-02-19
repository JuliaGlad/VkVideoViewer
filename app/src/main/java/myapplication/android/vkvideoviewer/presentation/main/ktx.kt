package myapplication.android.vkvideoviewer.presentation.main

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.cancellation.CancellationException

inline fun <T> runCatchingNonCancellation(block: () -> T): Result<T>{
    return try {
        Result.success(block())
    } catch (e: CancellationException){
        throw e
    } catch (e: Exception){
        Result.failure(e)
    }
}

suspend fun <T, R> asyncAwait(
    s1: suspend CoroutineScope.() -> T,
    transform: suspend (T) -> R
): R {
    return coroutineScope{
        val result = async(block = s1)
        transform(result.await())
    }
}