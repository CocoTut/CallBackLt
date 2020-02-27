package ru.cherepanovk.feature_google_calendar_impl.di

import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import ru.cherepanovk.feature_google_calendar_impl.data.GoogleCalendarApiImpl
import javax.inject.Singleton


@Module(includes = [GoogleClientModule::class])
abstract class CoreNetworkApiModule {
    @Binds
    abstract fun bindGoogleCalendarApi(googleCalendarApiImpl: GoogleCalendarApiImpl): GoogleCalendarApi
}

@Module
object GoogleClientModule {
    @Provides
    @JvmStatic
    @Singleton
    fun provideHttpTransport() = AndroidHttp.newCompatibleTransport()

    @Provides
    @JvmStatic
    @Singleton
    fun provideJsonFactory(): JsonFactory =
        JacksonFactory.getDefaultInstance()
}




