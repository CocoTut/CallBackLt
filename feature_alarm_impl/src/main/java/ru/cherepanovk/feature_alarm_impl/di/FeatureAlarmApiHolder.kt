package ru.cherepanovk.feature_alarm_impl.di

import ru.cherepanovk.core.di.BaseFeatureComponentHolder
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.dependencies.AppConfigProvider
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi

class FeatureAlarmApiHolder(
    featureComponentContainer: FeatureComponentContainer
) : BaseFeatureComponentHolder<FeatureAlarmApi>(featureComponentContainer) {
    override fun buildFeature(): FeatureAlarmApi {
        return DaggerFeatureAlarmComponent.builder()
            .appConfigProvider(getDependency(AppConfigProvider::class.java))
            .contextProvider(getDependency(ContextProvider::class.java))
            .corePreferencesApi(getDependency(CorePreferencesApi::class.java))
            .coreDbApi(getDependency(CoreDbApi::class.java))
            .build()
    }
}