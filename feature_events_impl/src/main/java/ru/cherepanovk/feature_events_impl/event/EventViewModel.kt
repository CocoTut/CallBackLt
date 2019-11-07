package ru.cherepanovk.feature_events_impl.event

import androidx.lifecycle.MutableLiveData
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.platform.SingleLiveEvent
import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.feature_events_impl.event.domain.CreateReminderAlarm
import ru.cherepanovk.feature_events_impl.event.domain.DeleteReminderFromDb
import ru.cherepanovk.feature_events_impl.event.domain.GetReminderFromDb
import ru.cherepanovk.feature_events_impl.event.domain.SaveReminderToDb
import java.util.*
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val getReminderFromDb: GetReminderFromDb,
    private val mapper: ReminderViewMapper,
    private val newReminderMapper: NewReminderMapper,
    private val saveReminderToDb: SaveReminderToDb,
    private val dateTimeHelper: DateTimeHelper,
    private  val deleteReminderFromDb: DeleteReminderFromDb,
    private val createReminderAlarm: CreateReminderAlarm
) : BaseViewModel() {

    val reminderView = MutableLiveData<ReminderView>()
    val toolbarTitleNewReminder = MutableLiveData<Boolean>()
    val success = SingleLiveEvent<Boolean>()
    val showDatePickerEvent = SingleLiveEvent<DateForPicker>()
    val showTimePickerEvent = SingleLiveEvent<TimeForPicker>()
    val eventDate = MutableLiveData<String>()
    val eventTime = MutableLiveData<String>()
    val buttonsVisibility = SingleLiveEvent<Boolean>()
    val hintTimeIsLessThanCurrent = MutableLiveData<Boolean>()
    val hintDateIsLessThanCurrent = MutableLiveData<Boolean>()

    private var id: String? = null

    init {
        loadReminder(id)
    }
    fun setReminderId(id: String?){
        this.id = id
    }

    fun loadReminder(id: String?) {
        toolbarTitleNewReminder.postValue(id == null)
        buttonsVisibility.postValue(id != null)
        if (id == null) {
            setCurrentDate()
            return
        }
        this.id = id

        launchLoading {
            getReminderFromDb(id) {
                it.handleSuccess { reminder ->
                    reminderView.postValue(mapper.map(reminder))
                }
            }
        }
    }

    fun saveReminder(reminderView: ReminderView) {
        reminderView.id = id
        launchLoading {
            saveReminderToDb(newReminderMapper.map(reminderView)) {
                it.handleSuccess {
                    createAlarm(reminderView)
                }

            }
        }


    }

    private fun createAlarm(reminderView: ReminderView) {
        launchLoading{
            createReminderAlarm(reminderView){
                it.handleSuccess {
                   success.postValue(true)
                }
            }
        }
    }

    fun onDateClick(eventDate: String) {
        val date = dateTimeHelper.getDateFromString(eventDate)
        val dateForPicker = DateForPicker(
            year = dateTimeHelper.getYearFromDate(date),
            month = dateTimeHelper.getMonthFromDate(date),
            day = dateTimeHelper.getDayFromDate(date)
        )
        showDatePickerEvent.postValue(dateForPicker)
    }

    fun onTimeClick(eventTime: String, eventDate: String) {
        val date = dateTimeHelper.getDateFromDateTimeString(eventDate, eventTime)
        val timeForPicker = TimeForPicker(
            hours = dateTimeHelper.getHoursFromDate(date),
            minutes = dateTimeHelper.getMinutesFromDate(date)
        )
        showTimePickerEvent.postValue(timeForPicker)
    }

    fun onDateSet(dateForPicker: DateForPicker) {
        val date = dateTimeHelper.getDateFromDatePicker(
            dateForPicker.year,
            dateForPicker.month,
            dateForPicker.day
        )
        eventDate.postValue(dateTimeHelper.getDateString(date))

        hintDateIsLessThanCurrent.postValue(isDateLessThanCurrent(date))
    }

    fun onTimeSet(timeForPicker: TimeForPicker) {
        val date = dateTimeHelper.getDateFromTimePicker(
            timeForPicker.hours,
            timeForPicker.minutes
        )
        eventTime.postValue(dateTimeHelper.getTimeString(date))

        hintTimeIsLessThanCurrent.postValue(isDateLessThanCurrent(date))
    }

    private fun isDateLessThanCurrent(date: Date): Boolean {
        val currentDate = dateTimeHelper.getCurrentDate()
        return date.before(currentDate)
    }

    private fun setCurrentDate() {
        val currentDate = dateTimeHelper.getCurrentDatePlusMinutes(1)
        eventDate.postValue(dateTimeHelper.getDateString(currentDate))
        eventTime.postValue(dateTimeHelper.getTimeString(currentDate))
    }

}