package ru.cherepanovk.feature_events_impl.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.cherepanovk.core.acc.Event
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.platform.SingleLiveEvent
import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.core_db_api.data.models.Reminder
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_events_impl.events.domain.*
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val getAllEventsFromOldDb: GetAllEventsFromOldDb,
    private val saveRemindersToDb: SaveRemindersToDb,
    private val getRemindersBetweenDates: GetRemindersFromDbBetweenDates,
    private val itemReminderMapper: ItemReminderMapper,
    private val getYearsFromDb: GetYearsFromDb,
    private val askGoogleAccount: AskGoogleAccount,
    private val dateHelper: DateTimeHelper,
    private val loadEventsOfCalendar: LoadEventsOfCalendar,
    private val preferencesApi: PreferencesApi,
    private val getGoogleAccountFromOldDb: GetGoogleAccountFromOldDb,
    private val moveReschedulesFromOldDb: MoveReschedulesFromOldDb
) : BaseViewModel() {

    private val _currentMonth = MutableLiveData<Int>()
    val currentMonth: LiveData<Int>
        get() = _currentMonth

    private val _currentYear = MutableLiveData<Int>()
    val currentYear: LiveData<Int>
        get() = _currentYear

    private val _itemsReminder = MediatorLiveData<List<ItemReminder>>()
    val itemsReminder: LiveData<List<ItemReminder>>
        get() = _itemsReminder

    private val _emptyListVisibility = MediatorLiveData<Boolean>()
    val emptyListVisibility: LiveData<Boolean>
        get() = _emptyListVisibility

    private val _years = MutableLiveData<List<String>>()
    val years: LiveData<List<String>>
        get() = _years

    private val _askGoogleCalendarAccount = SingleLiveEvent<Boolean>()
    val askGoogleCalendarAccount: LiveData<Boolean>
        get() = _askGoogleCalendarAccount

    private val _createNewReminder = MutableLiveData<Event<String>>()
    val createNewReminder: LiveData<Event<String>>
        get() = _createNewReminder

    private val _sortByDescending = MutableLiveData<Boolean>()
    val sortByDescending: LiveData<Boolean> = _sortByDescending

    private lateinit var remindersJob: Job

    init {

        if (!preferencesApi.isOldBaseMigrated()) {
            loadRemindersFromOldDb()
            migrateOldReschedules()
            loadGoogleAccountFromOldDb()
        }

        _sortByDescending.value = preferencesApi.getDescendingSort()

        loadData()

        _currentYear.postValue(dateHelper.getCurrentYear())

        _currentMonth.postValue(dateHelper.getCurrentMonth())

        _emptyListVisibility.addSource(itemsReminder) { items ->
            _emptyListVisibility.postValue(items.isEmpty())
        }
    }

    fun onBtnAddNewReminderClick() {
        _createNewReminder.postValue(Event(preferencesApi.getLastCalledPhoneNumber() ?: ""))
    }

    fun onMonthClick(month: Int) {
        remindersJob.cancel()
        _currentMonth.postValue(month)
        loadReminders(month, currentYear.value ?: dateHelper.getCurrentYear())
    }

    fun yearSelected(year: Int?) {
        remindersJob.cancel()
        _currentYear.value = year ?: dateHelper.getCurrentYear()
        loadReminders(currentMonth.value ?: dateHelper.getCurrentMonth(), currentYear.value!!)
    }

    fun checkGoogleAccount() {
        launchLoading {
            askGoogleAccount(UseCase.None()) {
                it.handleSuccess { needAsk ->
                    if (needAsk)
                        _askGoogleCalendarAccount.postValue(needAsk)
                }
            }
        }
    }


    fun updateReminders() {
        launchLoading {
            loadEventsOfCalendar(
                LoadEventsOfCalendar.Params(
                    dateHelper.getStartDate(
                        month = currentMonth.value ?: dateHelper.getCurrentMonth(),
                        year = dateHelper.getCurrentYear()
                    ),
                    dateHelper.getEndDate(
                        month = currentMonth.value ?: dateHelper.getCurrentMonth(),
                        year = dateHelper.getCurrentYear()
                    )
                )
            ) {

                it.handleOnlyFailure()
            }
        }
    }


    fun onSortClick() {
        _sortByDescending.value = !_sortByDescending.value!!
        preferencesApi.setDescendingSort(_sortByDescending.value!!)
        remindersJob.cancel()
        loadReminders(currentMonth.value ?: dateHelper.getCurrentMonth(), currentYear.value!!)
    }

    private fun loadGoogleAccountFromOldDb() {
        launch {
            getGoogleAccountFromOldDb(UseCase.None()) {
                it.handleSuccess { account ->
                    account?.let {
                        preferencesApi.setGoogleAccount(account)
                    }
                }
            }
        }
    }


    private fun loadData() {
        loadReminders(
            currentMonth.value ?: dateHelper.getCurrentMonth(),
            currentYear.value ?: dateHelper.getCurrentYear()
        )

        loadYears()
    }

    private fun loadReminders(month: Int, year: Int) {
        val dates = GetRemindersFromDbBetweenDates.Params(
            dateHelper.getStartDate(month, year),
            dateHelper.getEndDate(month, year)
        )
        launchLoading {
            getRemindersBetweenDates(dates) {
                it.handleSuccess { reminders ->
                    createItems(reminders)
                }
            }
        }

    }

    private fun createItems(reminders: Flow<List<Reminder>>) {
        remindersJob = launch {

            reminders.collect {
                val items = it.sortedWith(
                    if (sortByDescending.value == true)
                        compareByDescending { reminder -> reminder.dateTimeEvent }
                    else
                        compareBy { reminder -> reminder.dateTimeEvent }
                )
                    .map { reminder -> itemReminderMapper.map(reminder) }
                _itemsReminder.postValue(items)
            }

        }
    }

    private fun loadYears() {
        launchLoading {
            getYearsFromDb(UseCase.None()) {
                it.handleSuccess { listYears ->
                    launch {
                        listYears.collect { list ->
                            list.toMutableList().run {
                                if (list.contains(dateHelper.getCurrentYear().toString()).not())
                                    add(dateHelper.getCurrentYear().toString())
                                _years.postValue(this)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadRemindersFromOldDb() {
        launchLoading {
            getAllEventsFromOldDb(UseCase.None()) {
                it.handleSuccess { reminders ->
                    saveRemindersFromOldBase(reminders)
                    preferencesApi.setOldBaseMigrated(true)
                }
            }
        }
    }


    private fun saveRemindersFromOldBase(reminders: List<Reminder>) {
        launchLoading {
            saveRemindersToDb(reminders)
        }
    }

    private fun migrateOldReschedules() {
        launch {
            moveReschedulesFromOldDb(UseCase.None()) {}
        }
    }

}