package com.cherepanovky.callbackit.di.holders

import android.app.Application
import android.content.Context
import com.cherepanovky.callbackit.config.AppConfigImpl
import com.cherepanovky.callbackit.config.GooglePlayServicesAvailabilityImpl
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.config.AppConfig
import ru.cherepanovk.core.config.GooglePlayServicesAvailability
import ru.cherepanovk.core.di.FeatureComponentHolder
import ru.cherepanovk.core.di.dependencies.AppConfigProvider
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.GooglePlayServicesAvailabilityProvider
import ru.cherepanovk.core_db_impl.di.CoreDbApiHolderModule
import ru.cherepanovk.core_preferences_impl.di.PreferencesFeatureHolderModule
import ru.cherepanovk.feature_alarm_impl.di.FeatureAlarmApiHolderModule
import ru.cherepanovk.feature_events_impl.events.di.EventsFeatureApiHolderModule
import ru.cherepanovk.feature_google_calendar_impl.di.GoogleCalendarApiHolderModule
import ru.cherepanovk.feature_settings_impl.di.SettingsHolderModule
import javax.inject.Singleton

@Module(includes = [
    SettingsHolderModule::class,
    PreferencesFeatureHolderModule::class,
    GoogleCalendarApiHolderModule::class,
    FeatureAlarmApiHolderModule::class,
    CoreDbApiHolderModule::class,
    EventsFeatureApiHolderModule::class
])
class GlobalFeatureHoldersModule {

    @Provides
    @Singleton
    @IntoMap
    @ClassKey(ContextProvider::class)
    fun provideContextProvider(application: Application): FeatureComponentHolder<*> {
        return object : FeatureComponentHolder<ContextProvider> {
            override fun getFeatureComponent(): ContextProvider {
               return  object : ContextProvider {
                    override fun getContext(): Context {
                        return application
                    }
                }
            }
        }
    }

    @Provides
    @Singleton
    @IntoMap
    @ClassKey(GooglePlayServicesAvailabilityProvider::class)
    fun provideGooglePlayServicesAvailabilityProvider(
        application: Application
    ): FeatureComponentHolder<*> {
        return object : FeatureComponentHolder<GooglePlayServicesAvailabilityProvider> {
            override fun getFeatureComponent(): GooglePlayServicesAvailabilityProvider {
                return object : GooglePlayServicesAvailabilityProvider {
                    override fun getGooglePlayServicesAvailable(): GooglePlayServicesAvailability {
                        return GooglePlayServicesAvailabilityImpl(application)
                    }
                }
            }
        }
    }

    @Provides
    @Singleton
    @IntoMap
    @ClassKey(AppConfigProvider::class)
    fun provideAppConfigProvider(): FeatureComponentHolder<*> {
        return object : FeatureComponentHolder<AppConfigProvider> {
            override fun getFeatureComponent(): AppConfigProvider {
                return object : AppConfigProvider {
                    override fun getAppConfig(): AppConfig {
                        return AppConfigImpl()
                    }
                }
            }
        }
    }
}