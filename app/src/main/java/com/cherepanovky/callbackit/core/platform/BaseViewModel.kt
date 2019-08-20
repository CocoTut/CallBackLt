package com.cherepanovky.callbackit.core.platform


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cherepanovky.callbackit.core.exception.Failure
import com.cherepanovky.callbackit.core.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Failure
 */
abstract class BaseViewModel : ViewModel(), CoroutineScope {

    val failure: SingleLiveEvent<Failure> = SingleLiveEvent()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    protected open fun handleFailure(failure: Failure) {
        this.isLoading.value = false
        this.failure.value = failure
    }

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    /**
     * Use [handleFailure] method as [Failure] handler
     * */
    protected inline fun <T> Either<Failure, T>.handleSuccess(crossinline block: (T) -> Unit) {
        this.either(::handleFailure) { block(it) }
    }

    protected fun CoroutineScope.launchLoading(block: suspend () -> Unit) {
        launch {
            isLoading.value = true
            block()
            isLoading.value = false
        }
    }
}