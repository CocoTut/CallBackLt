package ru.cherepanovk.feature_events_impl.event

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.platform.ContactPicker
import ru.cherepanovk.core.platform.SingleLiveEvent
import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.feature_events_impl.event.domain.CreateReminderAlarm
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
    private val createReminderAlarm: CreateReminderAlarm,
    private val contactPicker: ContactPicker
) : BaseViewModel() {

    private val _reminderView = MutableLiveData<ReminderView>()
    val reminderView: LiveData<ReminderView>
        get() = _reminderView

    private val _toolbarTitleNewReminder = MutableLiveData<Boolean>()
    val toolbarTitleNewReminder: LiveData<Boolean>
        get() = _toolbarTitleNewReminder


    private val _success = SingleLiveEvent<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

    private val _showDatePickerEvent = SingleLiveEvent<DateForPicker>()
    val showDatePickerEvent: LiveData<DateForPicker>
        get() = _showDatePickerEvent

    private val _showTimePickerEvent = SingleLiveEvent<TimeForPicker>()
    val showTimePickerEvent: LiveData<TimeForPicker>
        get() = _showTimePickerEvent

    private val _eventDate = MutableLiveData<String>()
    val eventDate: LiveData<String>
        get() = _eventDate

    private val _eventTime = MutableLiveData<String>()
    val eventTime: LiveData<String>
        get() = _eventTime

    private val _buttonsVisibility = SingleLiveEvent<Boolean>()
    val buttonsVisibility: LiveData<Boolean>
        get() = _buttonsVisibility

    private val _hintTimeIsLessThanCurrent = MutableLiveData<Boolean>()
    val hintTimeIsLessThanCurrent: LiveData<Boolean>
        get() = _hintTimeIsLessThanCurrent

    private val _hintDateIsLessThanCurrent = MutableLiveData<Boolean>()
    val hintDateIsLessThanCurrent: LiveData<Boolean>
        get() = _hintDateIsLessThanCurrent

    private val _contactName = MutableLiveData<String>()
    val contactName: LiveData<String>
        get() = _contactName

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    private var id: String? = null

    init {
        loadReminder(id)
    }

    fun setReminderId(id: String?) {
        this.id = id
    }

    fun loadReminder(id: String?) {
        _toolbarTitleNewReminder.postValue(id == null)
        _buttonsVisibility.postValue(id != null)
        if (id == null || id.isEmpty()) {
            setCurrentDate()
            return
        }
        this.id = id

        launchLoading {
            getReminderFromDb(id) { it.handleSuccess { reminder -> handleReminder(reminder) } }
        }
    }

    fun trySetPhoneNumber(phoneNumber: String?) {
        if (phoneNumber == null || phoneNumber.isEmpty()) return
        _phoneNumber.postValue(phoneNumber)
        _contactName.postValue(contactPicker.getContactNameByPhoneNumber(phoneNumber))
    }

    fun setContact(contactIntent: Intent?) {
        _phoneNumber.postValue(contactPicker.getPhoneNumber(contactIntent))
        _contactName.postValue(contactPicker.getContactName(contactIntent))

    }

    fun saveReminder(reminderView: ReminderView) {
        reminderView.id = id
        launchLoading {
            saveReminderToDb(newReminderMapper.map(reminderView)) {
                it.handleSuccess { reminder -> createAlarm(reminder) }

            }
        }
    }


    private fun handleReminder(reminder: Reminder) {
        _reminderView.postValue(mapper.map(reminder))
    }


    private fun createAlarm(reminder: Reminder) {
        launchLoading {
            createReminderAlarm(reminder) {
                it.handleSuccess {
                    _success.postValue(true)
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
        _showDatePickerEvent.postValue(dateForPicker)
    }

    fun onTimeClick(eventTime: String, eventDate: String) {
        val date = dateTimeHelper.getDateFromDateTimeString(eventDate, eventTime)
        val timeForPicker = TimeForPicker(
            hours = dateTimeHelper.getHoursFromDate(date),
            minutes = dateTimeHelper.getMinutesFromDate(date)
        )
        _showTimePickerEvent.postValue(timeForPicker)
    }

    fun onDateSet(dateForPicker: DateForPicker) {
        val date = dateTimeHelper.getDateFromDatePicker(
            dateForPicker.year,
            dateForPicker.month,
            dateForPicker.day
        )
        _eventDate.postValue(dateTimeHelper.getDateString(date))

        _hintDateIsLessThanCurrent.postValue(isDateLessThanCurrent(date))
    }

    fun onTimeSet(timeForPicker: TimeForPicker) {
        val date = dateTimeHelper.getDateFromTimePicker(
            timeForPicker.hours,
            timeForPicker.minutes
        )
        _eventTime.postValue(dateTimeHelper.getTimeString(date))

        _hintTimeIsLessThanCurrent.postValue(isDateLessThanCurrent(date))
    }

    private fun isDateLessThanCurrent(date: Date): Boolean {
        val currentDate = dateTimeHelper.getCurrentDate()
        return date.before(currentDate)
    }

    private fun setCurrentDate() {
        val currentDate = dateTimeHelper.getCurrentDatePlusMinutes(1)
        _eventDate.postValue(dateTimeHelper.getDateString(currentDate))
        _eventTime.postValue(dateTimeHelper.getTimeString(currentDate))
    }

}