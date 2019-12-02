package ru.cherepanovk.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings


fun getAppSettingsIntent(context: Context): Intent {
    val uri = Uri.parse("package:" + context.packageName)
    return Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
}

fun getDialIntent(phoneNumber: String?) : Intent {
    return Intent().apply {
        action = Intent.ACTION_DIAL
        data = Uri.parse("tel:$phoneNumber")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}