package ru.cherepanovk.feature_google_calendar_impl.data

import android.accounts.AccountManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import ru.cherepanovk.feature_google_calendar_api.data.GoogleAccountAvailability
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@Reusable
class AccountPermissionManager @Inject constructor(
    private val googleAccountManager: GoogleAccountManager
) {

    private val savingAccountState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun requestPermissionsForAccountViaFragment(fragment: Fragment, authIntent: Intent) {
        fragment.startActivityForResult(
            authIntent,
            REQUEST_AUTHORIZATION
        )
    }


    fun isPermissionsForAccountGranted(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        savingAccountState.value = false
        return requestCode == REQUEST_AUTHORIZATION && resultCode == AppCompatActivity.RESULT_OK
    }

    fun chooseAccountViaFragment(fragment: Fragment) {
        fragment.startActivityForResult(
            getChooseAccountIntent(),
            REQUEST_ACCOUNT_PICKER
        )
    }

    fun getChosenAccountName(requestCode: Int, resultCode: Int, data: Intent?): String? {

        return if (requestCode == REQUEST_ACCOUNT_PICKER
            && resultCode == AppCompatActivity.RESULT_OK
        )
            getAccountName(data)
        else null
    }

    suspend fun isAccountAvailable(account: String): GoogleAccountAvailability {

        return withContext(Dispatchers.IO) {
            savingAccountState.value = false
            try {
                googleAccountManager.getGoogleCalendar(
                    googleAccountManager.getGoogleAccountCredential()
                        .setSelectedAccountName(account)
                )
                    .events()
                    .list(account)
                    .execute()
                GoogleAccountAvailability(true, null)


            } catch (e: UserRecoverableAuthIOException) {
                Timber.e("No permissions for GoogleAccount name: $account")
                GoogleAccountAvailability(
                    false,
                    e.intent
                )

            }
        }
    }

    fun savingAccount(): StateFlow<Boolean> = savingAccountState

    fun accountSaved(saved: Boolean) {
        savingAccountState.value = saved
    }

    private fun getChooseAccountIntent(): Intent {
        return googleAccountManager.getGoogleAccountCredential().newChooseAccountIntent()
    }

    private fun getAccountName(data: Intent?): String? {
        return data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
    }


    companion object {
        private const val REQUEST_ACCOUNT_PICKER = 18940
        private const val REQUEST_AUTHORIZATION = 1001
    }
}