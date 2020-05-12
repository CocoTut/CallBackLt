package ru.cherepanovk.feature_google_calendar_api.data

import android.content.Intent
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface GoogleCalendarApi {
    fun getChooseAccountIntent(): Intent

    fun chooseAccountViaFragment(fragment: Fragment)

    fun requestPermissionsForAccountViaFragment(fragment: Fragment, authIntent: Intent)

    fun isPermissionsForAccountGranted(requestCode: Int, resultCode: Int,  data: Intent?): Boolean

    fun getChosenAccountName(requestCode: Int, resultCode: Int, data: Intent?): String?

    suspend fun isAccountAvailable(account: String): GoogleAccountAvailability

    fun accountSaved(): StateFlow<Boolean>


}