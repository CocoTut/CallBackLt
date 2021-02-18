package ru.cherepanovk.core_preferences_api.data

interface PreferencesApi {
    fun isFirstStart(): Boolean

    fun setFirstStart()

    fun setGoogleAccount(account: String?)

    fun getGoogleAccount(): String

    fun setTrackingAllIncomingCalls(tracking: Boolean)

    fun getTrackingAllIncomingCalls(): Boolean

    fun setTrackingMissedIncomingCalls(tracking: Boolean)

    fun getTrackingMissedIncomingCalls(): Boolean

    fun setTrackingAllOutgoingCalls(tracking: Boolean)

    fun getTrackingAllOutgoingCalls(): Boolean

    fun setRingToneUri(uri: String)

    fun getRingToneUri(): String

    fun setOldBaseMigrated(migrated: Boolean)

    fun isOldBaseMigrated(): Boolean

    fun setLastCalledPhoneNumber(phoneNumber: String?)

    fun getLastCalledPhoneNumber(): String?

    fun setDescendingSort(sortByDescending: Boolean)

    fun getDescendingSort(): Boolean

    fun getWhatsApp(): Boolean

    fun setWhatsApp(enable: Boolean)

    fun setLongAlarmEnable(enable: Boolean)

    fun isLongAlarmEnable(): Boolean

    fun setRepeatAlarmTimes(times: Int)

    fun getRepeatAlarmTimes(): Int

    fun setDurationAlarmSeconds(seconds: Long)

    fun getDurationAlarmSeconds(): Long

    fun setDurationDelayAlarmSeconds(seconds: Long)

    fun getDurationDelayAlarmSeconds(): Long
}