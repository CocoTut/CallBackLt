package com.cherepanovky.callbackit.features.events.repository

import com.cherepanovky.callbackit.core.storage.olddb.Event

interface EventsRepository {
    fun getAllEventsFromOldBase(): List<Event>

}