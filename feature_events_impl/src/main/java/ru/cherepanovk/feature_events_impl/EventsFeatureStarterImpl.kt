package ru.cherepanovk.feature_events_impl

import android.content.Context
import android.content.Intent
import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import ru.cherepanovk.feature_events_api.EventsFeatureStarter
import javax.inject.Inject

class EventsFeatureStarterImpl
@Inject constructor(private val context: Context): EventsFeatureStarter {

    override fun getNavGraph(navInflater: NavInflater): NavGraph {
        return navInflater.inflate(R.navigation.nav_graph_events)
    }

    override fun start() {
        val cls = EventsActivity::class.java
        val intent = Intent(context, cls)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}