package ru.cherepanovk.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Settings
import ru.cherepanovk.core.R


fun getAppSettingsIntent(context: Context): Intent {
    val uri = Uri.parse("package:" + context.packageName)
    return Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
}

fun getDialIntent(phoneNumber: String?): Intent {
    return Intent().apply {
        action = Intent.ACTION_DIAL
        data = Uri.parse("tel:$phoneNumber")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}

fun getContactsPickerIntent() =
    Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI).apply {
        type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
    }

fun getPrivacyUrlIntent(context: Context) =
    Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.privacyURL)))

fun getRateUrl(context: Context) =
    Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.rateURL)))