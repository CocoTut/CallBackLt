package ru.cherepanovk.feature_google_calendar_impl.addaccount

import com.google.firebase.analytics.ktx.logEvent
import ru.cherepanovk.core.analytics.BaseAnalyticsPlugin
import javax.inject.Inject

class AddGoogleAccountAnalyticsPlugin @Inject constructor() : BaseAnalyticsPlugin() {

    fun addAccountButtonClicked() {
        firebaseAnalytics.logEvent(ADD_ACCOUNT_EVENT){ }
    }

    fun skipAccountButtonClicked() {
        firebaseAnalytics.logEvent(SKIP_ACCOUNT_EVENT){ }
    }

    fun googleAccountAddedSuccessfully() {
        firebaseAnalytics.logEvent(ACCOUNT_ADDED_SUCCESSFULLY_EVENT){ }
    }

    fun googleAccountAddedFailure() {
        firebaseAnalytics.logEvent(ACCOUNT_FAILURE_EVENT){ }
    }

    fun dialogShown() {
        firebaseAnalytics.logEvent(DIALOG_SHOWN_EVENT){ }
    }

    private companion object {
        const val DIALOG_SHOWN_EVENT = "dialog_add_account_has_shown"
        const val ADD_ACCOUNT_EVENT = "dialog_add_account_ok_clicked"
        const val SKIP_ACCOUNT_EVENT = "dialog_add_account_cancel_clicked"
        const val ACCOUNT_ADDED_SUCCESSFULLY_EVENT = "dialog_add_account_added_successfully"
        const val ACCOUNT_FAILURE_EVENT = "dialog_add_account_failure"
    }
}