package ru.cherepanovk.feature_events_impl.event

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class EventAnalyticsPlugin @Inject constructor() {
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    fun onWhatsAppClick() {
        firebaseAnalytics.logEvent(EVENT_WHATSAPP_CLICK) {}
    }

    companion object {
        private const val EVENT_WHATSAPP_CLICK = "WhatsApp_button_click"
    }
}