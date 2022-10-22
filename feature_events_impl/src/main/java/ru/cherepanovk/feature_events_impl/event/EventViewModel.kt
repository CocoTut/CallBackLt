package ru.cherepanovk.feature_events_impl.event

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import ru.cherepanovk.core.config.AppConfig
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.platform.ContactPicker
import ru.cherepanovk.core.platform.SingleLiveEvent
import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.core_db_api.data.models.Reminder
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_events_impl.event.domain.CreateReminderAlarm
import ru.cherepanovk.feature_events_impl.event.domain.GetReminderFromDb
import ru.cherepanovk.feature_events_impl.event.domain.SaveReminderToDb
import java.util.*
import javax.inject.Inject

class EventViewModel(
    private val getReminderFromDb: GetReminderFromDb,
    private val mapper: ReminderViewMapper,
    private val newReminderMapper: NewReminderMapper,
    private val saveReminderToDb: SaveReminderToDb,
    private val dateTimeHelper: DateTimeHelper,
    private val createReminderAlarm: CreateReminderAlarm,
    private val contactPicker: ContactPicker,
    private val preferencesApi: PreferencesApi,
    private val analyticsPlugin: EventAnalyticsPlugin,
    private val savedStateHandle: SavedStateHandle
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

    private val _eventDate = savedStateHandle.getLiveData<String>(EVENT_DATE)
    val eventDate: LiveData<String>
        get() = _eventDate

    private val _eventDateContentDescription = savedStateHandle.getLiveData<String>(
        EVENT_DATE_CONTENT_DESCRIPTION)
    val eventDateContentDescription: LiveData<String>
        get() = _eventDateContentDescription

    private val _eventTime = savedStateHandle.getLiveData<String>(EVENT_TIME)
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

    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

    private val _whatsappEnabled = MutableLiveData<Boolean>()
    val whatsappEnabled: LiveData<Boolean>
        get() = _whatsappEnabled

    private val _openRescheduleEvent = SingleLiveEvent<String>()
    val openRescheduleEvent: LiveData<String>
        get() = _openRescheduleEvent

    private var id: String? = null

    init {
        _whatsappEnabled.value = preferencesApi.getWhatsApp()
    }

    fun loadReminder(id: String?) {
        _toolbarTitleNewReminder.postValue(!hasId(id))
        _buttonsVisibility.postValue(hasId(id))
        setEventId(id)
        if (id != null && id.isNotEmpty()) {
            launchLoading {
                getReminderFromDb(id) { it.handleSuccess { reminder -> handleReminder(reminder) } }
            }
        }

    }

    fun setEventId(id: String?) {
        if (!hasId(id)) {
            setCurrentDate()
            return
        }
        this.id = id
    }

    fun trySetPhoneNumber(phoneNumber: String?) {
        preferencesApi.setLastCalledPhoneNumber(null)
        if (phoneNumber == null || phoneNumber.isEmpty()) return
        _phoneNumber.postValue(phoneNumber!!)
        contactPicker.getContactNameByPhoneNumber(phoneNumber)?.let {
            _contactName.postValue(it)
        }

    }

    fun setContact(contactIntent: Intent?) {
        _phoneNumber.postValue(contactPicker.getPhoneNumber(contactIntent))
        _contactName.postValue(contactPicker.getContactName(contactIntent))

    }

    fun saveReminder(reminderView: ReminderView) {
        reminderView.id = id
        launchLoading {
            saveReminderToDb(newReminderMapper.map(reminderView)) {
                it.handleSuccess { reminder -> _success.postValue(true) }

            }
        }
    }

    fun openReschedule(open: Boolean?) {
        if (open != null && id != null && open)
            _openRescheduleEvent.postValue(id!!)
    }

    private fun hasId(id: String?) =
        id != null && id.isNotBlank()


    private fun handleReminder(reminder: Reminder) {
        mapper.map(reminder).let {
            _reminderView.postValue(it)

            if (_eventDate.value == null) {
                saveDate(it.date)
                _eventDate.postValue(it.date)
            }
            if (_eventTime.value == null) {
                saveTime(it.time)
                _eventTime.postValue(it.time)
            }

            if (_eventDateContentDescription.value == null && it.dateContentDescription != null) {
                saveDateContentDescription(it.dateContentDescription)
                _eventDateContentDescription.postValue(it.dateContentDescription)
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
        _showDatePickerEvent.value = dateForPicker
    }

    fun onTimeClick(eventTime: String, eventDate: String) {
        val date = dateTimeHelper.getDateFromDateTimeString(eventDate, eventTime)
        val timeForPicker = TimeForPicker(
            hours = dateTimeHelper.getHoursFromDate(date),
            minutes = dateTimeHelper.getMinutesFromDate(date)
        )
        _showTimePickerEvent.value = timeForPicker
    }

    fun onDateSet(dateForPicker: DateForPicker) {
        val date = dateTimeHelper.getDateFromDatePicker(
            dateForPicker.year,
            dateForPicker.month,
            dateForPicker.day
        )
        dateTimeHelper.getDateString(date).let {
            _eventDate.postValue(it)
            saveDate(it)
        }

        dateTimeHelper.getFullDateString(date).let {
            _eventDateContentDescription.postValue(it)
            saveDateContentDescription(it)
        }

        _hintDateIsLessThanCurrent.postValue(isDateLessThanCurrent(date))
    }

    fun onTimeSet(timeForPicker: TimeForPicker) {
        val date = dateTimeHelper.getDateFromTimePicker(
            timeForPicker.hours,
            timeForPicker.minutes
        )
        dateTimeHelper.getTimeString(date).let {
            _eventTime.postValue(it)
            saveTime(it)
        }


        _hintTimeIsLessThanCurrent.postValue(isDateLessThanCurrent(date))
    }

    fun onWhatsAppButtonClick() {
        analyticsPlugin.onWhatsAppClick()
    }

    private fun isDateLessThanCurrent(date: Date): Boolean {
        val currentDate = dateTimeHelper.getCurrentDate()
        return date.before(currentDate)
    }

    private fun setCurrentDate() {
        getCurrentDateString().let {
            _eventDate.postValue(it)
            saveDate(it)
        }

        getCurrentTimeString().let {
            _eventTime.postValue(it)
            saveTime(it)
        }

    }

    private fun saveDate(date: String) {
        savedStateHandle[EVENT_DATE] = date
    }

    private fun saveDateContentDescription(date: String) {
        savedStateHandle[EVENT_DATE_CONTENT_DESCRIPTION] = date
    }

    private fun saveTime(time: String) {
        savedStateHandle[EVENT_TIME] = time
    }

    private fun getCurrentDate(): Date = dateTimeHelper.getCurrentDatePlusMinutes(1)

    private fun getCurrentDateString(): String = dateTimeHelper.getDateString(getCurrentDate())

    private fun getCurrentTimeString(): String = dateTimeHelper.getTimeString(getCurrentDate())

    companion object {
        private const val EVENT_DATE = "event_date"
        private const val EVENT_DATE_CONTENT_DESCRIPTION = "event_date_content_description"
        private const val EVENT_TIME = "event_time"
    }

}