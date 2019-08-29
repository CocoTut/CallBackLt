package ru.cherepanovk.core_db_api.data

interface DbApi {
    fun getAllEvents(): List<Reminder>
}