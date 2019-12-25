package ru.cherepanovk.core.platform


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.functional.Either
import kotlin.coroutines.CoroutineContext


/**
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Failure
 */
abstract class BaseViewModel : ViewModel(), CoroutineScope {

    private val _failure = SingleLiveEvent<Failure>()
    val failure: LiveData<Failure>
        get() = _failure

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun handleFailure(failure: Failure) {
        this._isLoading.postValue(false)
        this._failure.postValue(failure)
    }

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    /**
     * Use [handleFailure] method as [Failure] handler
     * */
    protected inline fun <T> Either<Failure, T>.handleSuccess(crossinline block: (T) -> Unit) {
        this.either({ fn -> handleFailure(fn) }) { block(it) }
    }

    protected fun CoroutineScope.launchLoading(block: suspend () -> Unit) {
        launch {
            _isLoading.value = true
            block()
            _isLoading.value = false
        }
    }
}