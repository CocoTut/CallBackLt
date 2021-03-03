package ru.cherepanovk.feature_events_impl.event

import androidx.lifecycle.SavedStateHandle
import ru.cherepanovk.core.di.viewmodel.ViewModelAssistedFactory
import ru.cherepanovk.core.platform.ContactPicker
import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_events_impl.event.domain.CreateReminderAlarm
import ru.cherepanovk.feature_events_impl.event.domain.GetReminderFromDb
import ru.cherepanovk.feature_events_impl.event.domain.SaveReminderToDb
import javax.inject.Inject

class EventViewModelFactory @Inject constructor(
    private val getReminderFromDb: GetReminderFromDb,
    private val mapper: ReminderViewMapper,
    private val newReminderMapper: NewReminderMapper,
    private val saveReminderToDb: SaveReminderToDb,
    private val dateTimeHelper: DateTimeHelper,
    private val createReminderAlarm: CreateReminderAlarm,
    private val contactPicker: ContactPicker,
    private val preferencesApi: PreferencesApi,
    private val analyticsPlugin: EventAnalyticsPlugin
) : ViewModelAssistedFactory<EventViewModel> {
    override fun create(handle: SavedStateHandle): EventViewModel =
        EventViewModel(
            getReminderFromDb,
            mapper,
            newReminderMapper,
            saveReminderToDb,
            dateTimeHelper,
            createReminderAlarm,
            contactPicker,
            preferencesApi,
            analyticsPlugin,
            handle
    )
}