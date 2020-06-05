package ru.cherepanovk.core.platform

import ru.cherepanovk.core.R
import ru.cherepanovk.core.exception.Failure
import javax.inject.Inject

//Do not add @Reusable or @Singleton
class ErrorHandler @Inject constructor(
    private val notifyMessageShower: NotifyMessageShower
) {

    fun onHandleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection ->
                notifyMessageShower.notify(R.string.error_no_connection)
            is Failure.ServerError ->
                notifyMessageShower.notify(R.string.error_api)
            is Failure.BadRequest ->
                failure.error?.let { notifyMessageShower.notify(it) } ?: notifyMessageShower.notify(R.string.error_api)
            is Failure.TimeOut ->
                notifyMessageShower.notify(R.string.error_server_timeout)
            is Failure.CreateNotificationError ->
                notifyMessageShower.notify(R.string.error_create_notification)
            is Failure.DataBaseError ->
                notifyMessageShower.notify(R.string.error_io)
            is Failure.NoGoogleAccount ->
                notifyMessageShower.notify(R.string.error_no_account)
        }
    }

}