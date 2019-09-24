package ru.cherepanovk.feature_events_impl.event

import androidx.lifecycle.MutableLiveData
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.platform.SingleLiveEvent
import ru.cherepanovk.feature_events_impl.event.domain.GetReminderFromDb
import ru.cherepanovk.feature_events_impl.event.domain.SaveReminderToDb
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val getReminderFromDb: GetReminderFromDb,
    private val mapper: ReminderViewMapper,
    private val newReminderMapper: NewReminderMapper,
    private val saveReminderToDb: SaveReminderToDb
) : BaseViewModel() {

    val reminderView = MutableLiveData<ReminderView>()
    val toolbarTitleNewReminder = MutableLiveData<Boolean>()
    val success = SingleLiveEvent<Boolean>()


    private var id: String? = null



    fun loadReminder(id: String?) {
        toolbarTitleNewReminder.postValue(id == null)
        if (id == null) return
        this.id = id

        launchLoading {
            getReminderFromDb(id){
                it.handleSuccess {reminder->
                    reminderView.postValue(mapper.map(reminder))
                }
            }
        }
    }

    fun saveReminder(reminderView: ReminderView){
        reminderView.id = id
        launchLoading {
            saveReminderToDb(newReminderMapper.map(reminderView)){
                it.handleSuccess {
                   success.postValue(true)
                }
            }
        }
    }

}