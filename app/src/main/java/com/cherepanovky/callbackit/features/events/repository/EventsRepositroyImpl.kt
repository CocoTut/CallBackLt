package com.cherepanovky.callbackit.features.events.repository

import ru.cherepanovk.core_db_api.data.OldDbClientApi
import ru.cherepanovk.core_db_api.data.Reminder
import javax.inject.Inject

class EventsRepositroyImpl @Inject constructor(
    private val oldBase: OldDbClientApi
) : EventsRepository {
    override fun getAllEventsFromOldBase(): List<Reminder> {
        return oldBase.getAllEvents()
    }
}