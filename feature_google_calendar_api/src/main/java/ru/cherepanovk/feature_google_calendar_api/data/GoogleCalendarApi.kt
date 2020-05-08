package ru.cherepanovk.feature_google_calendar_api.data

import android.content.Intent
import androidx.fragment.app.Fragment

interface GoogleCalendarApi {
    fun getChooseAccountIntent(): Intent

    fun chooseAccountViaFragment(fragment: Fragment)

    fun getChosenAccountName(requestCode: Int, resultCode: Int, data: Intent?): String?
}