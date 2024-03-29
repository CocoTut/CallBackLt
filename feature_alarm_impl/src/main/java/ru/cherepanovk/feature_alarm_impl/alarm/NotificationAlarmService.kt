package ru.cherepanovk.feature_alarm_impl.alarm

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import kotlinx.coroutines.*
import ru.cherepanovk.core.di.DI
import ru.cherepanovk.core.exception.CallBackItException
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_alarm_api.data.AlarmNotificationServiceLauncher
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_alarm_impl.callservices.AlarmNotificationServiceLauncherImpl.Companion.STOP_FOREGROUND_ACTION
import ru.cherepanovk.feature_alarm_impl.callservices.AlarmNotificationServiceLauncherImpl.Companion.STOP_PLAY_ALARM
import ru.cherepanovk.feature_alarm_impl.di.FeatureAlarmComponent
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationCreator
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationParams
import timber.log.Timber
import javax.inject.Inject

class NotificationAlarmService : Service(), CoroutineScope by CoroutineScope(Dispatchers.IO) {

    @Inject
    lateinit var preferencesApi: PreferencesApi

    @Inject
    lateinit var alarmNotificationServiceLauncher: AlarmNotificationServiceLauncher

    private val mediaPlayer = MediaPlayer()
    private val vibrator: Vibrator by lazy { getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    private val vibratePattern = longArrayOf(0, VIBRATE_DURATION, VIBRATE_PAUSE)
    private var alarmed = false
    private var playingCounter = 0
    private var countPlaying = 0
    private var mediaPlayerPlayed = false

    override fun onCreate() {
        DI.getComponent(FeatureAlarmApi::class.java, FeatureAlarmComponent::class.java)
            .getNotificationAlarmServiceComponent()
            .injectNotificationAlarmService(this)
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (!isNeedStartForeground(intent)) {
            stopForeground(false)
        } else {
            val notification = getNotification(intent)
            if (!alarmed) {
                alarmed = true
                startForegroundAction(notification.getNotification())
                startAlarm(intent)
            } else {
                notification.createNotification()
            }
        }


        return super.onStartCommand(intent, flags, startId)
    }

    private fun isNeedStartForeground(intent: Intent?): Boolean {
        return when (intent?.action) {
            STOP_FOREGROUND_ACTION -> {
                stopAlarm()
                false
            }
            STOP_PLAY_ALARM -> {
                mediaPlayer.stop()
                false
            }
            else -> true
        }
    }


    private fun stopAlarm() {
        if (mediaPlayerPlayed) mediaPlayer.stop()
        vibrator.cancel()
        cancel()
        stopSelf()
    }

    private fun startAlarm(intent: Intent?) {
        launch {
            var mediaSourceSet = false

            val ringtone = Uri.parse(preferencesApi.getRingToneUri())


            mediaPlayer.apply {
                try {
                    setDataSource(this@NotificationAlarmService, ringtone)
                    mediaSourceSet = true
                } catch (throwable: Throwable) {
                    Timber.e(throwable)
                }

                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)

                        .build()
                )
            }


            mediaPlayer.setOnCompletionListener {
                playingCounter++
                if (playingCounter >= preferencesApi.getDurationAlarmTimes()) {
                    mediaPlayer.stop()
                    vibrator.cancel()
                    if (countPlaying >= preferencesApi.getRepeatAlarmTimes()) {
                        stopAlarmAndShowNotification(intent)
                    }
                } else {
                    if (mediaPlayerPlayed) mediaPlayer.start()
                }
            }


            repeat(preferencesApi.getRepeatAlarmTimes()) { times ->
                countPlaying++
                playingCounter = 0
                mediaPlayerPlayed = playRingtone(mediaSourceSet)
                if (mediaPlayerPlayed) {
                    vibrate()
                } else {
                    repeat(preferencesApi.getDurationAlarmTimes()) {
                        vibrate()
                        delay(VIBRATE_DURATION + VIBRATE_PAUSE)
                        vibrator.cancel()
                    }
                }

                delay(preferencesApi.getDurationDelayAlarmMinutes() * MINUTE)
                if (mediaPlayerPlayed.not() && times >= preferencesApi.getRepeatAlarmTimes() - 1) {
                    stopAlarmAndShowNotification(intent)
                }
            }
        }

    }

    private fun stopAlarmAndShowNotification(intent: Intent?) {
        getNotification(intent).createNotification()
        stopAlarm()
    }

    private fun playRingtone(mediaSourceSet: Boolean): Boolean {
        var mediaPlayerPrepared = false
        if (mediaSourceSet) {
            mediaPlayer.apply {
                try {
                    prepare()
                    isLooping = false
                    start()
                    mediaPlayerPrepared = true
                } catch (throwable: Throwable) {
                    Timber.e(throwable)
                }

            }
        }
        return mediaPlayerPrepared
    }

    private fun vibrate() {
        if (isSilentMode()) return

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createWaveform(
                    vibratePattern,
                    REPEAT_INDEFINITELY
                )
            )
        } else {
            vibrator.vibrate(vibratePattern, REPEAT_INDEFINITELY)
        }
    }

    private fun isSilentMode(): Boolean {
        val am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return when (am.ringerMode) {
            AudioManager.RINGER_MODE_VIBRATE -> false
            AudioManager.RINGER_MODE_NORMAL -> false
            else -> true
        }
    }

    private fun startForegroundAction(notification: Notification) {
        startForeground(
            NOTIFICATION_ID,
            notification
        )
    }

    private fun getNotification(intent: Intent?): NotificationCreator {

        val paramsIn = NotificationParams.fromBundle(intent?.extras)
            ?: throw CallBackItException.CreateNotificationException

        val params = NotificationParams(
            reminderId = paramsIn.reminderId,
            contactName = paramsIn.contactName,
            description = paramsIn.description,
            notificationId = paramsIn.notificationId,
            phoneNumber = paramsIn.phoneNumber,
            alarmed = !mediaPlayer.isPlaying
        )

        val builder = NotificationCreator.Builder(this)
            .addCallAction(params)
            .addRescheduleAction(params)
            .addOpenReminderAction(params)

        if (!alarmed) {
            builder.addStopAlarmAction(getPendingIntent())
            builder.setMuted(true)
        }
        return builder.setMessage(params)
            .build()

    }

    private fun getPendingIntent(): PendingIntent {
        val actionIntent = alarmNotificationServiceLauncher.stopAlarmService()

        return PendingIntent.getService(
            this,
            STOP_CALL_NOTIFICATION_SERVICE_FOREGROUND_REQUEST_CODE,
            actionIntent,
            getPendingFlags()
        )

    }

    private fun getPendingFlags(): Int {
        return  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
    }


    companion object {
        const val STOP_CALL_NOTIFICATION_SERVICE_FOREGROUND_REQUEST_CODE = 1403021988

        private const val MINUTE = 1000L * 60
        private const val NOTIFICATION_ID = 22091984
        private const val REPEAT_INDEFINITELY = 0
        private const val VIBRATE_DURATION = 1000L
        private const val VIBRATE_PAUSE = 300L
    }
}