package ru.cherepanovk.feature_events_impl.events.data

import ru.cherepanovk.core_db_api.data.DbApi
import javax.inject.Inject

class GoogleAccountRepositoryImpl @Inject constructor(private val dataBase: DbApi) : GoogleAccountRepository {
    override fun getGoogleAccount() = dataBase.getGoogleAccountFromOldDb()
}