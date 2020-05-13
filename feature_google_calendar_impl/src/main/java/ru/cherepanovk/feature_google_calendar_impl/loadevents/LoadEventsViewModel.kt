package ru.cherepanovk.feature_google_calendar_impl.loadevents

import com.google.api.client.util.DateTime
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.feature_google_calendar_impl.loadevents.domain.LoadEventsOfCalendar
import javax.inject.Inject

class LoadEventsViewModel @Inject constructor(
    private val loadEventsOfCalendar: LoadEventsOfCalendar,
    private val dateTimeHelper: DateTimeHelper
) : BaseViewModel() {


    fun loadEvents() {
        launchLoading {
            loadEventsOfCalendar(
                LoadEventsOfCalendar.Params(
                    startDate = getDate(0),
                    endTime = getDate(12)
                )
            )
        }
    }

    private fun getDate(month: Int): DateTime = DateTime(
        dateTimeHelper.getStartDate(
            month = month,
            year = dateTimeHelper.getCurrentYear()
        )
    )
}