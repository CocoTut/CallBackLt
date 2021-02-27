package ru.cherepanovk.core_preferences_impl.data

import android.content.SharedPreferences
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import javax.inject.Inject

class Preferences @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val analyticsPlugin: PreferencesAnalyticsPlugin
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

    override fun getWhatsApp(): Boolean {
        return sharedPreferences.getBoolean(WHATSAPP, true)
    }

    override fun setWhatsApp(enable: Boolean) {
        analyticsPlugin.sendWhatsAppEnableEvent(enable)
        sharedPreferences.edit().putBoolean(WHATSAPP, enable).apply()
    }

    override fun setLongAlarmEnable(enable: Boolean) {
        analyticsPlugin.sendLongAlarmEnableEvent(enable)
        sharedPreferences.edit().putBoolean(LONG_ALARM_ENABLED, enable).apply()
    }

    override fun isLongAlarmEnable(): Boolean =
        sharedPreferences.getBoolean(LONG_ALARM_ENABLED, true)

    override fun setRepeatAlarmTimes(times: Int) {
        sharedPreferences.edit().putInt(REPEAT_ALARM_TIMES, times).apply()
        if (times != REPEAT_ALARM_TIMES_DEFAULT) {
            analyticsPlugin.sendLongAlarmRepeatTimesValue(times)
        }
    }

    override fun getRepeatAlarmTimes(): Int =
        sharedPreferences.getInt(REPEAT_ALARM_TIMES, REPEAT_ALARM_TIMES_DEFAULT)


    override fun setDurationAlarmTimes(times: Int) {
        sharedPreferences.edit().putInt(DURATION_ALARM_TIMES, times).apply()
        if (times != DURATION_ALARM_SECONDS_DEFAULT) {
            analyticsPlugin.sendLongAlarmDurationValue(times)
        }
    }

    override fun getDurationAlarmTimes(): Int =
        sharedPreferences.getInt(DURATION_ALARM_TIMES, DURATION_ALARM_SECONDS_DEFAULT)

    override fun setDurationDelayAlarmMinutes(minutes: Int) {
        sharedPreferences.edit().putInt(DURATION_DELAY_ALARM_MINUTES, minutes).apply()
        if (minutes != DURATION_DELAY_ALARM_SECONDS_DEFAULT) {
            analyticsPlugin.sendLongAlarmDelayValue(minutes)
        }
    }

    override fun getDurationDelayAlarmMinutes(): Int =
        sharedPreferences.getInt(DURATION_DELAY_ALARM_MINUTES, DURATION_DELAY_ALARM_SECONDS_DEFAULT)


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
        private const val WHATSAPP = "WHATSAPP"
        private const val REPEAT_ALARM_TIMES = "REPEAT_ALARM_TIMES"
        private const val DURATION_ALARM_TIMES = "DURATION_ALARM_TIMES"
        private const val DURATION_DELAY_ALARM_MINUTES = "DURATION_DELAY_ALARM_MINUTES"
        private const val LONG_ALARM_ENABLED = "LONG_ALARM_ENABLED"

        private const val REPEAT_ALARM_TIMES_DEFAULT = 2
        private const val DURATION_ALARM_SECONDS_DEFAULT = 3
        private const val DURATION_DELAY_ALARM_SECONDS_DEFAULT = 1
    }
}