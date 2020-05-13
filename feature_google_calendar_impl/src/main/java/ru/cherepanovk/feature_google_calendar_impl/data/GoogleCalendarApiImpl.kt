package ru.cherepanovk.feature_google_calendar_impl.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import javax.inject.Inject
@ExperimentalCoroutinesApi
class GoogleCalendarApiImpl @Inject constructor(
   private val accountPermissionManager: AccountPermissionManager
) : GoogleCalendarApi {

    override fun savingAccount() = accountPermissionManager.savingAccount()

}