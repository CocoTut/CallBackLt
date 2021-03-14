package com.cherepanovky.callbackit.config

import com.cherepanovky.callbackit.BuildConfig
import ru.cherepanovk.core.config.AppConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfigImpl @Inject constructor() : AppConfig {
    override val isLoggingEnabled: Boolean = BuildConfig.DEBUG
    override val appVersion: String = BuildConfig.VERSION_NAME
    override val flashAlarmPastTime: Boolean = BuildConfig.DEBUG
}