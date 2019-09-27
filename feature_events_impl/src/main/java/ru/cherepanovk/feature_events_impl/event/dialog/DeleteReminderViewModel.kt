package ru.cherepanovk.feature_events_impl.event.dialog

import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.platform.SingleLiveEvent
import ru.cherepanovk.feature_events_impl.event.domain.DeleteReminderFromDb
import javax.inject.Inject

class DeleteReminderViewModel @Inject constructor(
    private  val deleteReminderFromDb: DeleteReminderFromDb
) : BaseViewModel() {

    val openMainScreen = SingleLiveEvent<Boolean>()

    fun deleteReminder(id: String?) {
        id?.let {
            launchLoading {
                deleteReminderFromDb(id){
                    it.handleSuccess {
                        openMainScreen.call()
                    }
                }
            }
        } ?: showError()

    }

    private fun showError() {
        handleFailure(Failure.DataBaseError)
    }
}