package ru.cherepanovk.feature_google_calendar_impl.data

import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import ru.cherepanovk.feature_google_calendar_api.data.GoogleAccountFeatureStarter
import ru.cherepanovk.feature_google_calendar_impl.R
import javax.inject.Inject

class GoogleAccountFeatureStarterImpl @Inject constructor() : GoogleAccountFeatureStarter {
    override fun getAddGoogleAccountGraph(navInflater: NavInflater): NavGraph {
        return navInflater.inflate(R.navigation.nav_graph_add_google_account)
    }
}