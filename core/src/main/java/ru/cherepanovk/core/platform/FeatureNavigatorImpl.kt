package ru.cherepanovk.core.platform

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import ru.cherepanovk.core.di.dependencies.FeatureNavigator
import javax.inject.Inject

class FeatureNavigatorImpl @Inject constructor() : FeatureNavigator {
    override fun navigateToFeature(navController: NavController, featureNavGraph: NavGraph) {
        navController.graph.addDestination(featureNavGraph)
        navController.navigate(featureNavGraph.id)
    }
}