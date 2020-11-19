package ru.cherepanovk.feature_events_impl

import androidx.fragment.app.Fragment
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject


private const val REQUEST_CONTACT = 10065

class ContactsPermissionChecker @Inject constructor() {
    fun checkContactPermission(fragment: Fragment, block: () -> Unit) {

        if (EasyPermissions.hasPermissions(
                fragment.requireContext(),
                android.Manifest.permission.READ_CONTACTS
            )
        ) {
            block()
            return
        }

        if (
            EasyPermissions.somePermissionPermanentlyDenied(
                fragment,
                mutableListOf(android.Manifest.permission.READ_CONTACTS)
            )
        ) {
            AppSettingsDialog.Builder(fragment).build().show()
            return
        }

        EasyPermissions.requestPermissions(
            fragment,
            fragment.getString(R.string.request_permissions_contacts),
            REQUEST_CONTACT, android.Manifest.permission.READ_CONTACTS
        )

    }

}