package com.cherepanovky.callbackit.config

import ru.cherepanovk.core.config.AppConfig
import javax.inject.Inject
import javax.inject.Singleton
import com.cherepanovky.callbackit.BuildConfig

@Singleton
class AppConfigImpl @Inject constructor() : AppConfig {
    override val isLoggingEnabled: Boolean = BuildConfig.DEBUG
    override val appVersion: String = BuildConfig.VERSION_NAME
    override val whatsappEnabled: Boolean = BuildConfig.DEBUG
}