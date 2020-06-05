package ru.cherepanovk.core.di.dependencies

import androidx.navigation.NavController
import androidx.navigation.NavGraph

interface FeatureNavigator {
    fun navigateToFeature(navController: NavController, featureNavGraph: NavGraph)
}