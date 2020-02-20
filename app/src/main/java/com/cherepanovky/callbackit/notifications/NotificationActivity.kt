package com.cherepanovky.callbackit.notifications

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import com.cherepanovky.callbackit.CallBackItMainActivity
import com.cherepanovky.callbackit.R
import com.cherepanovky.callbackit.di.FeatureProxyInjector
import com.cherepanovky.callbackit.notifications.di.DaggerNotificationActivityComponent
import com.cherepanovky.callbackit.notifications.di.NotificationActivityModule
import kotlinx.android.synthetic.main.activity_notification.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.platform.BaseActivity
import ru.cherepanovk.core_domain_impl.notifications.NotificationParams
import ru.cherepanovk.feature_events_impl.event.EventOpenParams

class NotificationActivity : BaseActivity() {
    override var navHost = R.id.nav_host_fragment_notification

    override fun inject(componentManager: ComponentManager) {
        DaggerNotificationActivityComponent.builder()
            .notificationActivityModule(NotificationActivityModule(fragmentContainer()))
            .build()
            .also { componentManager.put(it) }
            .inject(this)

    }

    override fun fragmentContainer(): View = notificationFragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        inject(ComponentManager)
        setNavigation()
    }

    private fun setNavigation() {
        val navGraph = FeatureProxyInjector.getEventsFeature().eventsFeatureStarter()
            .getEventNavGraph(navController.navInflater)

        val params = NotificationParams.fromBundle(intent.extras)
        startDestination = navGraph.startDestination
        navGraph.addArgument(
            EventOpenParams.REMINDER_ID,
            NavArgument.Builder()
                .setType(NavType.StringType)
                .setDefaultValue(params?.reminderId ?: "")
                .build()
        )

        navGraph.addArgument(
            EventOpenParams.PHONE_NUMBER,
            NavArgument.Builder()
                .setType(NavType.StringType)
                .setDefaultValue(params?.phoneNumber ?: "")
                .build()
        )
        navController.graph = navGraph
    }

    override fun onBackPressed() {
        startActivity(Intent(this, CallBackItMainActivity::class.java))
        super.onBackPressed()
    }

    override fun onDestroy() {
        ComponentManager.remove(DaggerNotificationActivityComponent::class)
        super.onDestroy()
    }
}
