package com.example.technicaltest_qiscus.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun getLastMessageTimestamp(utcDate: Date?): String? {
        return if (utcDate != null) {
            val todayCalendar = Calendar.getInstance()
            val localCalendar = Calendar.getInstance()
            localCalendar.time = utcDate
            when {
                getDateStringFromDate(todayCalendar.time)
                        == getDateStringFromDate(localCalendar.time) -> {
                    getTimeStringFromDate(utcDate)
                }
                todayCalendar[Calendar.DATE] - localCalendar[Calendar.DATE] == 1 -> {
                    "Yesterday"
                }
                else -> {
                    getDateStringFromDate(utcDate)
                }
            }
        } else {
            null
        }
    }

    fun getTimeStringFromDate(date: Date): String? {
        val dateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
        return dateFormat.format(date)
    }

    fun getDateStringFromDate(date: Date): String? {
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return dateFormat.format(date)
    }
}