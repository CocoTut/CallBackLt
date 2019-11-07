package ru.cherepanovk.core_domain_api.di

import ru.cherepanovk.core_domain_api.data.AlarmApi

interface CoreDomainApi {

    fun alarmApi(): AlarmApi
}