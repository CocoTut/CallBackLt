package ru.cherepanovk.core.utils

import java.text.SimpleDateFormat
import java.time.Year
import java.util.*
import javax.inject.Inject


const val EVENT_DATE_FORMAT = "EEE, dd MMM yyyy"
const val EVENT_TIME_FORMAT = "HH:mm"

class DateTimeHelper @Inject constructor() {

    fun getDateFromString(date: String): Date {
        val formatter = SimpleDateFormat(EVENT_DATE_FORMAT, Locale.getDefault())
        return formatter.parse(date)
    }

    fun getTimeFromString(time: String): Date {
        val formatter = SimpleDateFormat(EVENT_TIME_FORMAT, Locale.getDefault())
        return formatter.parse(time)
    }

    fun getDateFromDateTimeString(date: String, time: String): Date {
        val pattern = "$EVENT_DATE_FORMAT $EVENT_TIME_FORMAT"
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val eventDate = "$date $time"
        return formatter.parse(eventDate)
    }

    fun getDateFromDatePicker(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        return calendar.time
    }

    fun getDateFromTimePicker(hours: Int, minutes: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)
        return calendar.time
    }

    fun getDateString(date: Date): String {
        return getStringFromDate(date, EVENT_DATE_FORMAT)
    }

    fun getTimeString(date: Date): String {
        return getStringFromDate(date, EVENT_TIME_FORMAT)
    }

    fun getYearFromDate(date: Date): Int {
        return getFieldFromDate(date, Calendar.YEAR)
    }

    fun getMonthFromDate(date: Date): Int {
        return getFieldFromDate(date, Calendar.MONTH)
    }

    fun getDayFromDate(date: Date): Int {
        return getFieldFromDate(date, Calendar.DAY_OF_MONTH)
    }

    fun getHoursFromDate(date: Date): Int {
        return getFieldFromDate(date, Calendar.HOUR_OF_DAY)
    }

    fun getMinutesFromDate(date: Date): Int {
        return getFieldFromDate(date, Calendar.MINUTE)
    }

    fun getCurrentDatePlusMinutes(minutes: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, minutes)
        return calendar.time
    }

    private fun getFieldFromDate(date: Date, field: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(field)
    }

    private fun getStringFromDate(date: Date, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(date)
    }
}