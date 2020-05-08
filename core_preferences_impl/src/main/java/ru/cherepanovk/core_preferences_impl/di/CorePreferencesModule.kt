package ru.cherepanovk.core_preferences_impl.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.core_preferences_impl.data.Preferences

@Module
abstract class CorePreferencesModule {
    @Binds
    abstract fun bindPreferences(preferences: Preferences): PreferencesApi

    companion object {
        private const val PREFS_NAME = "callbackit_preference"
        @Provides
        fun provideSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
    }
}