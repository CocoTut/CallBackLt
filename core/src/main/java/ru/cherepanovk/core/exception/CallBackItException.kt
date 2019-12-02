package ru.cherepanovk.core.exception

import java.lang.Exception

sealed class CallBackItException : Throwable() {
    object CreateNotificationException : CallBackItException()
}