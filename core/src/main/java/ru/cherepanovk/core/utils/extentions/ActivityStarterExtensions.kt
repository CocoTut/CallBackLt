package ru.cherepanovk.core.utils.extentions

import android.content.Context
import ru.cherepanovk.core.platform.ActivityStarter
import ru.cherepanovk.core.utils.getAppSettingsIntent

fun ActivityStarter.openSettings(context: Context) {
    startActivityForResult(getAppSettingsIntent(context), 0)
}