package ru.cherepanovk.core_preferences_impl.data

import com.google.firebase.analytics.ktx.logEvent
import ru.cherepanovk.core.analytics.BaseAnalyticsPlugin
import javax.inject.Inject

class PreferencesAnalyticsPlugin @Inject constructor() : BaseAnalyticsPlugin() {


    fun sendWhatsAppEnableEvent(checked: Boolean) {
        firebaseAnalytics.logEvent(
            if (checked) EVENT_WHATSAPP_ENABLED else EVENT_WHATSAPP_DISABLED
        ){ param(PARAM_WHATSAPP, checked.toString()) }
    }

    fun sendLongAlarmEnableEvent(checked: Boolean) {
        firebaseAnalytics.logEvent(if (checked) EVENT_LONG_ALARM_ENABLED else EVENT_LONG_ALARM_DISABLED){
            param(PARAM_LONG_ALARM_ENABLED, checked.toString())
        }
    }

    fun sendLongAlarmDurationValue(duration: Int) {
        firebaseAnalytics.logEvent(EVENT_LONG_ALARM_CHANGE_DURATION){
            param(PARAM_LONG_ALARM_DELAY, duration.toString())
        }
    }

    fun sendLongAlarmDelayValue(delay: Int) {
        firebaseAnalytics.logEvent(EVENT_LONG_ALARM_CHANGE_DELAY){
            param(PARAM_LONG_ALARM_DURATION, delay.toString())
        }
    }

    fun sendLongAlarmRepeatTimesValue(times: Int) {
        firebaseAnalytics.logEvent(EVENT_LONG_ALARM_CHANGE_TIMES){
            param(PARAM_LONG_ALARM_REPEAT_TIMES, times.toString())
        }
    }

    fun removeAccount() {
        firebaseAnalytics.logEvent(EVENT_REMOVE_ACCOUNT){}
    }

    fun ringtoneChanged() {
        firebaseAnalytics.logEvent(EVENT_RINGTONE_CHANGED){}
    }

    companion object {
        private const val EVENT_WHATSAPP_ENABLED = "WhatsApp_feature_enabled"
        private const val EVENT_WHATSAPP_DISABLED = "WhatsApp_feature_disabled"
        private const val PARAM_WHATSAPP = "enabled"

        private const val EVENT_LONG_ALARM_CHANGE_DURATION = "long_alarm_feature_duration"
        private const val EVENT_LONG_ALARM_CHANGE_DELAY = "long_alarm_feature_interval"
        private const val EVENT_LONG_ALARM_CHANGE_TIMES = "long_alarm_feature_repeat_times"
        private const val EVENT_LONG_ALARM_ENABLED = "long_alarm_feature_enabled"
        private const val EVENT_LONG_ALARM_DISABLED = "long_alarm_feature_disabled"

        private const val PARAM_LONG_ALARM_ENABLED = "enabled"
        private const val PARAM_LONG_ALARM_DURATION = "duration"
        private const val PARAM_LONG_ALARM_DELAY = "interal"
        private const val PARAM_LONG_ALARM_REPEAT_TIMES = "repeat_times"

        private const val EVENT_REMOVE_ACCOUNT = "settings_remove_account"
        private const val EVENT_RINGTONE_CHANGED = "settings_ringtone_changed"
    }
}