package com.cherepanovky.callbackit.core.storage

import android.content.SharedPreferences
import java.util.*

private const val PREF_TOKEN = "token"
private const val PREF_IS_PUSH_REGISTER = "is_push_register"
private const val PREF_PUSH_ENABLED = "pushEnabled"
private const val PREF_PHONE = "phone"
private const val PREF_FIRST_LAUNCH = "firstLaunch"
private const val PREF_SECURE_CODE = "secureCode"
class PreferencesManager(private val preferences: SharedPreferences) {

    fun setToken(token: String?) {
        preferences.edit().putString(PREF_TOKEN, token).apply()
    }

    fun getToken(): String{
        val token = preferences.getString(PREF_TOKEN, null)
        return when(token){
            null -> ""
            else -> token
        }
    }


    fun isFirstLaunch(): Boolean {
        return preferences.getBoolean(PREF_FIRST_LAUNCH, true)
    }

    fun reset(){
        preferences.edit().clear().apply()
    }

    fun getSecureCode(): String? {
        return preferences.getString(PREF_SECURE_CODE, null)
    }

    fun setSecureCode(secureCode: String?) {
        preferences.edit().putString(PREF_SECURE_CODE, secureCode).apply()
    }

}