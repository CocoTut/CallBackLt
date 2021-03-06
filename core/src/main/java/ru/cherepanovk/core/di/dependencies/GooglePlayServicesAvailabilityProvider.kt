package ru.cherepanovk.core.di.dependencies

import ru.cherepanovk.core.config.GooglePlayServicesAvailability

interface GooglePlayServicesAvailabilityProvider {
    fun getGooglePlayServicesAvailable(): GooglePlayServicesAvailability
}