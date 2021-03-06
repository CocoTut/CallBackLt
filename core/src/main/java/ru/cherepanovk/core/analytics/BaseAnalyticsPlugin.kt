package ru.cherepanovk.core.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

abstract class BaseAnalyticsPlugin {
    protected val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
}