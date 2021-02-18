package com.cherepanovky.callbackit.notifications

import android.app.NotificationManager
import android.content.Context
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
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.platform.BaseActivity
import ru.cherepanovk.feature_alarm_api.data.AlarmNotificationServiceLauncher
import ru.cherepanovk.feature_alarm_impl.di.DaggerFeatureAlarmComponent
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationParams
import ru.cherepanovk.feature_events_impl.event.EventOpenParams
import javax.inject.Inject

class NotificationActivity : BaseActivity() {
    @Inject
    lateinit var alarmNotificationServiceLauncher: AlarmNotificationServiceLauncher

    private val params: NotificationParams?
        get() = NotificationParams.fromBundle(intent.extras)

    override var navHost = R.id.nav_host_fragment_notification

    override fun inject(componentManager: ComponentManager) {
        DaggerNotificationActivityComponent.builder()
            .notificationActivityModule(NotificationActivityModule(fragmentContainer()))
            .featureAlarmApi(
                DaggerFeatureAlarmComponent.builder().contextProvider(componentManager.getOrThrow())
                    .build()
                    .also { componentManager.put(it) }
            )
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
        cancelNotification(params)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, CallBackItMainActivity::class.java))
        super.onBackPressed()
    }

    override fun onDestroy() {
        ComponentManager.remove(DaggerNotificationActivityComponent::class)
        super.onDestroy()
    }

    private fun setNavigation() {
        val navGraph = FeatureProxyInjector.getEventsFeature().eventsFeatureStarter()
            .getEventNavGraph(navController.navInflater)

        startDestination = navGraph.startDestination
        navGraph.apply {
            addArgument(
                EventOpenParams.REMINDER_ID,
                NavArgument.Builder()
                    .setType(NavType.StringType)
                    .setDefaultValue(params?.reminderId ?: "")
                    .build()
            )
            addArgument(
                EventOpenParams.PHONE_NUMBER,
                NavArgument.Builder()
                    .setType(NavType.StringType)
                    .setDefaultValue(params?.phoneNumber ?: "")
                    .build()
            )
            addArgument(
                EventOpenParams.RESCHEDULE_EVENT,
                NavArgument.Builder()
                    .setType(NavType.BoolType)
                    .setDefaultValue(intent.action == "com.cherepanovky.action.reschedule")
                    .build()
            )
        }

        navController.graph = navGraph

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (arguments == null)
                onBackPressed()
        }
    }


    private fun cancelNotification(params: NotificationParams?) {
        val notificationId = params?.notificationId ?: NotificationParams.NOTIFICATION_ID_DEFAULT
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)

        if (params?.alarmed == true)
            startService(alarmNotificationServiceLauncher.stopAlarmService())
        else
            startService(alarmNotificationServiceLauncher.stopAlarm())
    }
}
