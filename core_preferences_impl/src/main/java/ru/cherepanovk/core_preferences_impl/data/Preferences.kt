package ru.cherepanovk.core_preferences_impl.data

import android.content.SharedPreferences
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import javax.inject.Inject

class Preferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
): PreferencesApi {
    override fun isFirstStart(): Boolean {
        return sharedPreferences.getBoolean(FIRST_START, true)
    }

    override fun setFirstStart() {
        sharedPreferences.edit().putBoolean(FIRST_START, true).apply()
    }

    override fun setGoogleAccount(account: String?) {
       sharedPreferences.edit().putString(GOOGLE_ACCOUNT, account).apply()
    }

    override fun getGoogleAccount(): String {
       return sharedPreferences.getString(GOOGLE_ACCOUNT, "") ?: ""
    }

    companion object {
        private const val FIRST_START = "FIRST_START"
        private const val PICKED_RINGTONE = "picked_ringtone"
        private const val GOOGLE_ACCOUNT = "google_account"
    }
}