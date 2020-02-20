package ru.cherepanovk.core.utils.extentions

import android.content.Context
import ru.cherepanovk.core.platform.ActivityStarter
import ru.cherepanovk.core.utils.getAppSettingsIntent
import ru.cherepanovk.core.utils.getContactsPickerIntent

fun ActivityStarter.openSettings(context: Context) {
    startActivityForResult(getAppSettingsIntent(context), 0)
}

fun ActivityStarter.pickContacts(requestCode: Int) {
    startActivityForResult(getContactsPickerIntent(), requestCode)
}