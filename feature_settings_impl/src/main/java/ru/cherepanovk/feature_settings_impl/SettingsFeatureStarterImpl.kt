package ru.cherepanovk.feature_settings_impl

import androidx.navigation.NavGraph
import androidx.navigation.NavInflater

object SettingsFeatureStarterImpl {

    fun getNavGraph(navInflater: NavInflater): NavGraph {
        return navInflater.inflate(R.navigation.nav_graph_settings)
    }
}