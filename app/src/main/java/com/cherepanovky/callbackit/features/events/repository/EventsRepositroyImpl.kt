package com.cherepanovky.callbackit.features.events.repository

import com.cherepanovky.callbackit.core.storage.olddb.Event
import com.cherepanovky.callbackit.core.storage.olddb.LocalBase
import javax.inject.Inject

class EventsRepositroyImpl @Inject constructor(
    private val oldBase: LocalBase
) : EventsRepository {
    override fun getAllEventsFromOldBase(): List<Event> {
        return oldBase.allEvents
    }
}