package ru.cherepanovk.core.di.dependencies

import ru.cherepanovk.core.config.AppConfig

interface AppConfigProvider {
    fun getAppConfig(): AppConfig
}