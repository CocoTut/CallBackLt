package ru.cherepanovk.feature_events_impl.dialog

import androidx.lifecycle.LiveData
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.platform.SingleLiveEvent
import ru.cherepanovk.feature_events_impl.event.domain.DeleteReminderFromDb
import javax.inject.Inject

class DeleteReminderViewModel @Inject constructor(
    private val deleteReminderFromDb: DeleteReminderFromDb
) : BaseViewModel() {

    private val _openMainScreen = SingleLiveEvent<Boolean>()
    val openMainScreen: LiveData<Boolean>
        get() = _openMainScreen

    fun deleteReminder(id: String?) {
        id?.let {
            launchLoading {
                deleteReminderFromDb(id) {
                    it.handleSuccess {
                        _openMainScreen.postValue(true)
                    }
                }
            }
        } ?: showError()

    }

    private fun showError() {
        handleFailure(Failure.DataBaseError)
    }
}