package space.pixelsg.tutuor.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

open class CommonViewModel : ViewModel() {
    val error = MutableSharedFlow<String>(0)

    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        viewModelScope.launch {
            error.emit(throwable.localizedMessage ?: throwable.message ?: "Something went wrong")
        }
    }

    fun <T> Flow<T>.shareWhileSubscribed(replay: Int = 1, expiration: Long = Long.MAX_VALUE) =
        this.shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(replayExpirationMillis = expiration),
            replay
        )
}
