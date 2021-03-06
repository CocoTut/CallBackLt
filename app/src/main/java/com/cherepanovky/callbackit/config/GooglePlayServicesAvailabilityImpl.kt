package com.cherepanovky.callbackit.config

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import ru.cherepanovk.core.config.GooglePlayServicesAvailability
import javax.inject.Inject

class GooglePlayServicesAvailabilityImpl @Inject constructor(
    private val context: Context
) : GooglePlayServicesAvailability {

    override fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
        return resultCode == ConnectionResult.SUCCESS
    }
}