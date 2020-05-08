package ru.cherepanovk.feature_google_calendar_impl.data

import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.CalendarScopes
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleCalendarApiImpl @Inject constructor(
    private val transport: HttpTransport,
    private val jsonFactory: JsonFactory,
    private val context: Context
) : GoogleCalendarApi {

    override fun getChooseAccountIntent(): Intent {
        return getGoogleAccountCredential().newChooseAccountIntent()
    }

    override fun chooseAccountViaFragment(fragment: Fragment) {
        fragment.startActivityForResult(getChooseAccountIntent(), REQUEST_ACCOUNT_PICKER)
    }

    override fun getChosenAccountName(requestCode: Int, resultCode: Int, data: Intent?): String? {

      return if (requestCode == REQUEST_ACCOUNT_PICKER
            && resultCode == AppCompatActivity.RESULT_OK
        )
           data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)

        else null
    }

    private fun getGoogleAccountCredential(): GoogleAccountCredential {
        return GoogleAccountCredential.usingOAuth2(
            context, listOf(CalendarScopes.CALENDAR)
        )
            .setBackOff(ExponentialBackOff())
    }

    companion object {
        private const val REQUEST_ACCOUNT_PICKER = 18940
    }
}