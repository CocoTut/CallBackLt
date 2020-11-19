package ru.cherepanovk.feature_events_api

import androidx.navigation.NavGraph
import androidx.navigation.NavInflater

interface EventsFeatureStarter {
    fun start()

    fun getEventsNavGraph(navInflater: NavInflater): NavGraph

    fun getEventNavGraph(navInflater: NavInflater): NavGraph
}