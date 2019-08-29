package ru.cherepanovk.core_db_api.di

import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_api.data.OldDbClientApi

interface CoreDbApi {
    fun getOldDbClient(): OldDbClientApi

    fun getDbApi(): DbApi
}