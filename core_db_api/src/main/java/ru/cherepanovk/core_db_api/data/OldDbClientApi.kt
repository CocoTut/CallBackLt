package ru.cherepanovk.core_db_api.data

interface OldDbClientApi {
    fun getAllEvents(): List<Reminder>
}