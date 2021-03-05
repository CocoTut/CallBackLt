package ru.cherepanovk.core.platform

import android.view.View
import ru.cherepanovk.core.R
import ru.cherepanovk.core.exception.Failure
import javax.inject.Inject

//Do not add @Reusable or @Singleton
class ErrorHandler @Inject constructor(
    private val notifyMessageShower: NotifyMessageShower
) {

    fun onHandleFailure(view: View, failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection ->
                notifyMessageShower.notify(view, R.string.error_no_connection)
            is Failure.ServerError ->
                notifyMessageShower.notify(view, R.string.error_api)
            is Failure.BadRequest ->
                failure.error?.let { notifyMessageShower.notify(view, it) } ?: notifyMessageShower.notify(view,R.string.error_api)
            is Failure.TimeOut ->
                notifyMessageShower.notify(view, R.string.error_server_timeout)
            is Failure.CreateNotificationError ->
                notifyMessageShower.notify(view, R.string.error_create_notification)
            is Failure.DataBaseError ->
                notifyMessageShower.notify(view, R.string.error_io)
            is Failure.NoGoogleAccount ->
                notifyMessageShower.notify(view, R.string.error_no_account)
            is Failure.UrlError ->
                notifyMessageShower.notify(view, R.string.error_url)
            is Failure.UnexpectedError ->
                notifyMessageShower.notify(view, R.string.error_unexpected)
            is Failure.NoEmailApplication ->
                notifyMessageShower.notify(view, R.string.error_no_email_application)
            is Failure.WhatsAppNotInstalled ->
                notifyMessageShower.notify(view, R.string.error_no_whatsapp)
        }
    }

    fun onHandleFailure(view: View, failure: Failure?, action: () -> Unit) {
        when (failure) {
            is Failure.WhatsAppNotInstalled ->
                notifyMessageShower.notifyWithAction(
                    view,
                    R.string.error_no_whatsapp,
                    R.string.error_no_whatsapp_action,
                    action
                )
        }
    }
}