package ru.cherepanovk.feature_google_calendar_api.data

import androidx.navigation.NavGraph
import androidx.navigation.NavInflater

interface GoogleAccountFeatureStarter {
    fun getAddGoogleAccountGraph(navInflater: NavInflater): NavGraph
}