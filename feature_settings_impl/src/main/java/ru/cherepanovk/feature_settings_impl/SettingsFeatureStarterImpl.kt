package ru.cherepanovk.feature_settings_impl

import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import ru.cherepanovk.feature_settings_api.SettingsFeatureStarter
import javax.inject.Inject

class SettingsFeatureStarterImpl @Inject constructor() : SettingsFeatureStarter {

    override fun getNavGraph(navInflater: NavInflater): NavGraph {
        return navInflater.inflate(R.navigation.nav_graph_settings)
    }
}