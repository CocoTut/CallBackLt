package ru.cherepanovk.core_db_api.di

import ru.cherepanovk.core_db_api.data.DbApi

interface CoreDbApi {

    fun getDbApi(): DbApi
}