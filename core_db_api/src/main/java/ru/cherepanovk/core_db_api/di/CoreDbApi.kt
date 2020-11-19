package ru.cherepanovk.core_db_api.di

import ru.cherepanovk.core_db_api.data.RemindersDbApi
import ru.cherepanovk.core_db_api.data.RescheduleDbApi

interface CoreDbApi {

    fun getRemindersDbApi(): RemindersDbApi

    fun getRescheduleDbApi(): RescheduleDbApi
}