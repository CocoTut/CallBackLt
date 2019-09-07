package ru.cherepanovk.feature_settings_api

import androidx.navigation.NavGraph
import androidx.navigation.NavInflater

interface SettingsFeatureStarter {
    fun getNavGraph(navInflater: NavInflater): NavGraph
}