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
import com.cherepanovky.callbackit.databinding.ActivityNotificationBinding
import com.cherepanovky.callbackit.notifications.di.DaggerNotificationActivityComponent
import ru.cherepanovk.core.di.DI
import ru.cherepanovk.core.platform.BaseActivity
import ru.cherepanovk.core.platform.viewBinding
import ru.cherepanovk.feature_alarm_api.data.AlarmNotificationServiceLauncher
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationParams
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_events_impl.event.EventOpenParams
import javax.inject.Inject

class NotificationActivity : BaseActivity() {

    private val binding: ActivityNotificationBinding by viewBinding(ActivityNotificationBinding::inflate)

    @Inject
    lateinit var alarmNotificationServiceLauncher: AlarmNotificationServiceLauncher

    private val params: NotificationParams?
        get() = NotificationParams.fromBundle(intent.extras)



    override var navHost = R.id.nav_host_fragment_notification

    override fun inject() {
        DaggerNotificationActivityComponent.builder()
            .featureAlarmApi(DI.getFeature(FeatureAlarmApi::class.java))
            .build()
            .inject(this)
    }

    override fun fragmentContainer(): View = binding.notificationFragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inject()
        setNavigation()
        cancelNotification(params)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, CallBackItMainActivity::class.java))
        super.onBackPressed()
    }

    private fun setNavigation() {
        val navGraph = DI.getFeature(EventsFeatureApi::class.java)
            .eventsFeatureStarter()
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
