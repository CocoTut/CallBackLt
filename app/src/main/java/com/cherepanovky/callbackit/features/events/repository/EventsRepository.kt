package com.cherepanovky.callbackit.features.events.repository

import com.cherepanovky.callbackit.core.storage.olddb.Event
import ru.cherepanovk.core_db_api.data.Reminder

interface EventsRepository {
    fun getAllEventsFromOldBase(): List<Reminder>

}