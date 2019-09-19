package ru.cherepanovk.feature_events_impl.event

import androidx.lifecycle.MutableLiveData
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.feature_events_impl.event.domain.GetReminderFromDb
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val getReminderFromDb: GetReminderFromDb,
    private val mapper: ReminderViewMapper
) : BaseViewModel() {

    val reminderView = MutableLiveData<ReminderView>()
    val toolbarTitleNewReminder = MutableLiveData<Boolean>()


    private lateinit var id: String



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

}