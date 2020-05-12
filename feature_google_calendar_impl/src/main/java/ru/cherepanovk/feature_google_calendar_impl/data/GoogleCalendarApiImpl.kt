package ru.cherepanovk.feature_google_calendar_impl.data

import android.accounts.AccountManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_google_calendar_api.data.GoogleAccountAvailability
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
@ExperimentalCoroutinesApi
@Singleton
class GoogleCalendarApiImpl @Inject constructor(
    private val googleAccountManager: GoogleAccountManager,
    private val preferencesApi: PreferencesApi
) : GoogleCalendarApi {


    private val savingAccountState: MutableStateFlow<Boolean> = MutableStateFlow(false)


    override fun getChooseAccountIntent(): Intent {
        return googleAccountManager.getGoogleAccountCredential().newChooseAccountIntent()
    }

    override fun chooseAccountViaFragment(fragment: Fragment) {
        fragment.startActivityForResult(getChooseAccountIntent(), REQUEST_ACCOUNT_PICKER)
    }

    override fun getChosenAccountName(requestCode: Int, resultCode: Int, data: Intent?): String? {

        return if (requestCode == REQUEST_ACCOUNT_PICKER
            && resultCode == AppCompatActivity.RESULT_OK
        )
            getAccountName(data)
        else null
    }

    private fun getAccountName(data: Intent?): String? {
        return data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
    }


    override suspend fun isAccountAvailable(account: String): GoogleAccountAvailability {
        savingAccountState.value = false
        return try {
            googleAccountManager.getGoogleCalendar(
                googleAccountManager.getGoogleAccountCredential().setSelectedAccountName(account)
            )
                .events()
                .list(account)
                .execute()
            savingAccountState.value = true
            GoogleAccountAvailability(true, null)


        } catch (e: UserRecoverableAuthIOException) {
            Timber.e("No permissions for GoogleAccount name: $account")
            GoogleAccountAvailability(
                false,
                e.intent
            )

        }
    }

    override fun requestPermissionsForAccountViaFragment(fragment: Fragment, authIntent: Intent) {
        fragment.startActivityForResult(authIntent, REQUEST_AUTHORIZATION)
    }

    override fun isPermissionsForAccountGranted(requestCode: Int, resultCode: Int,  data: Intent?): Boolean {
        savingAccountState.value = false
        val granted =  requestCode == REQUEST_AUTHORIZATION && resultCode == AppCompatActivity.RESULT_OK
        if (granted) {
            preferencesApi.setGoogleAccount(getAccountName(data))
            savingAccountState.value = granted
        }
        return requestCode == REQUEST_AUTHORIZATION && resultCode == AppCompatActivity.RESULT_OK
    }

    override fun accountSaved() = savingAccountState



    companion object {
        private const val REQUEST_ACCOUNT_PICKER = 18940
        private const val REQUEST_AUTHORIZATION = 1001
    }
}