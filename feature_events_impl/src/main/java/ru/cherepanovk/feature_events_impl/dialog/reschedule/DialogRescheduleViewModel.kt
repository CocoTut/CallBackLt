package ru.cherepanovk.feature_events_impl.dialog.reschedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xwray.groupie.Group
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.platform.SingleLiveEvent
import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.core_db_api.data.models.Reminder
import ru.cherepanovk.core_db_api.data.models.Reschedule
import ru.cherepanovk.feature_events_impl.dialog.reschedule.domain.AddRescheduleToDb
import ru.cherepanovk.feature_events_impl.dialog.reschedule.domain.DeleteReschedulesFromDb
import ru.cherepanovk.feature_events_impl.dialog.reschedule.domain.GetReschedulesFromDb
import ru.cherepanovk.feature_events_impl.event.domain.GetReminderFromDb
import ru.cherepanovk.feature_events_impl.event.domain.SaveReminderToDb
import java.util.*
import javax.inject.Inject

class DialogRescheduleViewModel @Inject constructor(
    private val getReminderFromDb: GetReminderFromDb,
    private val saveReminderToDb: SaveReminderToDb,
    private val getReschedulesFromDb: GetReschedulesFromDb,
    private val rescheduleMapper: RescheduleMapper,
    private val deleteReschedulesFromDb: DeleteReschedulesFromDb,
    private val addRescheduleToDb: AddRescheduleToDb,
    private val rescheduleCreator: RescheduleCreator,
    private val dateTimeHelper: DateTimeHelper
) : BaseViewModel() {

    private val mutableRescheduleItems = MutableLiveData<List<Group>>()
    val rescheduleItems: LiveData<List<Group>> by this::mutableRescheduleItems

    private val mutableOpenMainScreen = SingleLiveEvent<Boolean>()
    val openMainScreen: LiveData<Boolean> by this::mutableOpenMainScreen

    private val mutableCreateClick = SingleLiveEvent<Boolean>()
    val createClick: LiveData<Boolean> by this::mutableCreateClick


    fun loadRescheduleItems() {
        launchLoading {
            getReschedulesFromDb(UseCase.None()) {
                it.handleSuccess { result ->
                    setReschedules(result)
                }
            }
        }
    }

    fun onItemClick(reminderId: String?, item: RescheduleItem) {
        reminderId?.let {
            launch {
                getReminderFromDb(reminderId) { result ->
                    result.handleSuccess { reminder ->
                        updateReminder(reminder, item)
                    }
                }
            }
        }

    }

    private fun setReschedules(result: Flow<List<Reschedule>>) {
        launch {
            result.collect { reschedules ->
                mutableRescheduleItems.postValue(
                    getItems(reschedules).toMutableList()
                        .apply { add(RescheduleCreateItem(::onAddRescheduleClick, ::onCreateClick)) }
                )
            }
        }
    }

    private fun getItems(reschedules: List<Reschedule>): List<Group> =
        reschedules.map { reschedule -> rescheduleMapper.map(reschedule, ::onDeleteItemClick) }


    private fun onDeleteItemClick(id: Int) {
        launchLoading {
            deleteReschedulesFromDb(id) {}
        }
    }

    private fun onAddRescheduleClick(unit: RescheduleUnitType, number: Int) {
        launch {
            addRescheduleToDb(rescheduleCreator.createReschedule(unit, number))
        }
    }

    private fun onCreateClick() {
        mutableCreateClick.postValue(true)
    }

    private fun updateReminder(reminder: Reminder, item: RescheduleItem) {
        launchLoading {
            saveReminderToDb(
                Reminder(
                    id = reminder.id,
                    description = reminder.description,
                    contactName = reminder.contactName,
                    phoneNumber = reminder.phoneNumber,
                    dateTimeEvent = dateTimeHelper.addTimeToDate(
                        Date(),
                        getMinutes(item.unit, item.number)
                    )
                )
            ) { it.handleSuccess { mutableOpenMainScreen.postValue(true) } }
        }
    }

    private fun getMinutes(unitType: RescheduleUnitType, number: Int): Int {
        return when (unitType) {
            RescheduleUnitType.MINUTES -> number
            RescheduleUnitType.HOURS -> number * 60
            RescheduleUnitType.DAYS -> number * 60 * 12
        }
    }
}