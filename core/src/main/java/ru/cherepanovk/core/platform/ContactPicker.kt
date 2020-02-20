package ru.cherepanovk.core.platform

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import javax.inject.Inject

private const val CURSOR_CONTACT_NAME = 0
private const val CURSOR_PHONE_NUMBER = 1

class ContactPicker @Inject constructor(private val context: Context) {

    private val contactsData: Array<String> by lazy {
        arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
    }

    fun getPhoneNumber(contactIntent: Intent?): String {
        return contactIntent?.data?.let { dataUri ->
            val cursor = context.contentResolver.query(
                dataUri, contactsData, null, null, null
            )
            cursor?.moveToNext()
            val phoneNumber = cursor?.getString(CURSOR_PHONE_NUMBER) ?: ""
            cursor?.close()
            return@let phoneNumber
        } ?: ""

    }

    fun getContactName(contactIntent: Intent?): String {
        return contactIntent?.data?.let { dataUri ->
            val cursor = context.contentResolver.query(
                dataUri, contactsData, null, null, null
            )
            cursor?.moveToNext()
            val contactName = cursor?.getString(CURSOR_CONTACT_NAME) ?: ""
            cursor?.close()
            return@let contactName
        } ?: ""
    }

    fun getContactNameByPhoneNumber(phoneNumber: String): String? {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) !=
            PERMISSION_GRANTED
        ) return null

        val uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(phoneNumber)
        )
        val cursor = context.contentResolver.query(
            uri,
            arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME),
            null,
            null,
            null
        )

        return cursor?.let {
            val contactName = if (cursor.moveToFirst())
                cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME))
            else null

            cursor.close()

            return@let contactName
        }
    }


}