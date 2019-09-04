package ru.cherepanovk.feature_events_api

import androidx.navigation.NavGraph
import androidx.navigation.NavInflater

interface EventsFeatureStarter {
    fun start()

    fun getNavGraph(navInflater: NavInflater): NavGraph
}