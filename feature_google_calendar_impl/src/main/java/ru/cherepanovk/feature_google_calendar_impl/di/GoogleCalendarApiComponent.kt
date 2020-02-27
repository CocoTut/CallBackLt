package ru.cherepanovk.feature_google_calendar_impl.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi
import javax.inject.Singleton

@Component(
    modules = [CoreNetworkApiModule::class],
    dependencies = [ContextProvider::class]
)
@Singleton
interface GoogleCalendarApiComponent  : CoreGoogleCalendarApi