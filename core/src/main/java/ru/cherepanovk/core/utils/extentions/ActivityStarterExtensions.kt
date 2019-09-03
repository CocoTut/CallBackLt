package ru.cherepanovk.core.utils.extentions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import ru.cherepanovk.core.platform.ActivityStarter

fun ActivityStarter.openSettings(context: Context) {
    val uri = Uri.parse("package:" + context.packageName)
    val appSettingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
    startActivityForResult(appSettingsIntent, 0)
}