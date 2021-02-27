package ru.cherepanovk.feature_google_calendar_impl.loadevents

import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.feature_google_calendar_impl.loadevents.domain.LoadEventsOfCalendar
import java.util.*
import javax.inject.Inject

class LoadEventsViewModel @Inject constructor(
    private val loadEventsOfCalendar: LoadEventsOfCalendar,
    private val dateTimeHelper: DateTimeHelper
) : BaseViewModel() {



    fun loadEvents() {
        launchLoading {
            loadEventsOfCalendar(
                LoadEventsOfCalendar.Params(
                    startDate = getStartDate(),
                    endTime = getEndDate()
                )
            )
        }
    }
    private fun getStartDate(): Date =
            dateTimeHelper.getStartDate(
                month = JANUARY,
                year = dateTimeHelper.getPreviousYear()
            )


    private fun getEndDate(): Date =
        dateTimeHelper.getStartDate(
            month = DECEMBER,
            year = dateTimeHelper.getCurrentYear()
        )

    private companion object {
        const val JANUARY = 0
        const val DECEMBER = 11
    }
}