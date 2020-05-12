package ru.cherepanovk.feature_google_calendar_impl.di

import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.cherepanovk.feature_google_calendar_api.data.GoogleAccountFeatureStarter
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import ru.cherepanovk.feature_google_calendar_impl.data.GoogleAccountFeatureStarterImpl
import ru.cherepanovk.feature_google_calendar_impl.data.GoogleCalendarApiImpl
import javax.inject.Singleton


@Module
abstract class CoreNetworkApiModule {
    @Binds
    @Singleton
    abstract fun bindGoogleCalendarApi(googleCalendarApiImpl: GoogleCalendarApiImpl): GoogleCalendarApi

    @Binds
    abstract fun bindFeatureStarter(googleAccountFeatureStarterImpl: GoogleAccountFeatureStarterImpl): GoogleAccountFeatureStarter

    companion object {
        @Provides
        @Singleton
        fun provideHttpTransport(): HttpTransport = AndroidHttp.newCompatibleTransport()

        @Provides
        @Singleton
        fun provideJsonFactory(): JsonFactory =
            JacksonFactory.getDefaultInstance()
    }
}




