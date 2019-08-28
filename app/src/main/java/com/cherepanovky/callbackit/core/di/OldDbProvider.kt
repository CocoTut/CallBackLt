package com.cherepanovky.callbackit.core.di

import com.cherepanovky.callbackit.core.storage.olddb.DbHelper
import com.cherepanovky.callbackit.core.storage.olddb.LocalBase

interface OldDbProvider {
    fun getOldDb(): LocalBase
    fun getDbHelper(): DbHelper
}