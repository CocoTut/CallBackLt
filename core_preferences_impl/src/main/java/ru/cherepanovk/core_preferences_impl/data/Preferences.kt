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
        sharedPreferences.edit().putBoolean(FIRST_START, false).apply()
    }

    override fun setGoogleAccount(account: String?) {
       sharedPreferences.edit().putString(GOOGLE_ACCOUNT, account).apply()
    }

    override fun getGoogleAccount(): String {
       return sharedPreferences.getString(GOOGLE_ACCOUNT, "") ?: ""
    }

    override fun setTrackingAllIncomingCalls(tracking: Boolean) {
        sharedPreferences.edit().putBoolean(INCOMING_ON, tracking).apply()
    }

    override fun getTrackingAllIncomingCalls(): Boolean {
       return sharedPreferences.getBoolean(INCOMING_ON, true)
    }

    override fun setTrackingMissedIncomingCalls(tracking: Boolean) {
        sharedPreferences.edit().putBoolean(INCOMING_MISSED_ON, tracking).apply()
    }

    override fun getTrackingMissedIncomingCalls(): Boolean {
        return sharedPreferences.getBoolean(INCOMING_MISSED_ON, true)
    }

    override fun setTrackingAllOutgoingCalls(tracking: Boolean) {
        sharedPreferences.edit().putBoolean(OUTGOING_ON, tracking).apply()
    }

    override fun getTrackingAllOutgoingCalls(): Boolean {
        return sharedPreferences.getBoolean(OUTGOING_ON, true)
    }

    override fun setRingToneUri(uri: String) {
        sharedPreferences.edit().putString(PICKED_RINGTONE, uri).apply()
    }

    override fun getRingToneUri(): String {
        return sharedPreferences.getString(PICKED_RINGTONE, "") ?:""
    }

    override fun setOldBaseMigrated(migrated: Boolean) {
        sharedPreferences.edit().putBoolean(OLD_BASE_MIGRATED, migrated).apply()
    }

    override fun isOldBaseMigrated() =
        sharedPreferences.getBoolean(OLD_BASE_MIGRATED, false)

    override fun setLastCalledPhoneNumber(phoneNumber: String?) {
        sharedPreferences.edit().putString(LAST_CALLED_PHONE_NUMBER, phoneNumber).apply()
    }

    override fun getLastCalledPhoneNumber() =
        sharedPreferences.getString(LAST_CALLED_PHONE_NUMBER, null)

    override fun setDescendingSort(sortByDescending: Boolean) {
        sharedPreferences.edit().putBoolean(SORT_BY_DESCENDING, sortByDescending).apply()
    }

    override fun getDescendingSort(): Boolean {
        return sharedPreferences.getBoolean(SORT_BY_DESCENDING, false)
    }



    companion object {
        private const val FIRST_START = "FIRST_START"
        private const val PICKED_RINGTONE = "picked_ringtone"
        private const val GOOGLE_ACCOUNT = "google_account"
        private const val OUTGOING_ON = "OUT_ON"
        private const val INCOMING_ON = "IN_ON"
        private const val INCOMING_MISSED_ON = "IN_MISSED_ON"
        private const val OLD_BASE_MIGRATED = "OLD_BASE_MIGRATED"
        private const val LAST_CALLED_PHONE_NUMBER = "LAST_CALLED_PHONE_NUMBER"
        private const val SORT_BY_DESCENDING = "SORT_BY_DESCENDING"
    }
}