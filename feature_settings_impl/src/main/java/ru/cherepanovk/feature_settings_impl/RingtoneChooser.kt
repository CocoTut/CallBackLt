package ru.cherepanovk.feature_settings_impl

import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.provider.Settings
import ru.cherepanovk.feature_settings_impl.RingtoneChooser.RequiredStater.START_ACTIVITY
import ru.cherepanovk.feature_settings_impl.RingtoneChooser.RequiredStater.START_ACTIVITY_FOR_RESULT
import javax.inject.Inject

class RingtoneChooser @Inject constructor() {
    fun chooseRingtone(context: Context, currentUri: String, channelId: String): Pair<RequiredStater, Intent> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Pair(START_ACTIVITY,
                Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE,context.packageName)
                    putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
                }
            )
        } else {
            Pair(START_ACTIVITY_FOR_RESULT,
                Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
                    putExtra(
                        RingtoneManager.EXTRA_RINGTONE_TITLE,
                        context.getString(R.string.title_select_ringtone)
                    )
                    putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
                    putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
                    putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION)
                    putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentUri)
                }

            )
        }
    }

    enum class RequiredStater {
        START_ACTIVITY, START_ACTIVITY_FOR_RESULT
    }
}