package com.niraj.notificationdatacollector.collector

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object TimeCollector {

    private fun calendar(): Calendar {
        return Calendar.getInstance()
    }

    private fun now(): Date {
        return Date()
    }

    fun getTimestamp(): Long {
        return System.currentTimeMillis()
    }

    fun getFormattedTime(): String {
        return SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            Locale.getDefault()
        ).format(now())
    }

    fun getDate(): String {
        return SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(now())
    }

    fun getDayOfWeek(): String {
        return SimpleDateFormat(
            "EEEE",
            Locale.getDefault()
        ).format(now())
    }

    fun getMonth(): String {
        return SimpleDateFormat(
            "MMMM",
            Locale.getDefault()
        ).format(now())
    }

    fun getYear(): Int {
        return calendar().get(Calendar.YEAR)
    }

    fun getHour(): Int {
        return calendar().get(Calendar.HOUR_OF_DAY)
    }

    fun getMinute(): Int {
        return calendar().get(Calendar.MINUTE)
    }

    fun getSecond(): Int {
        return calendar().get(Calendar.SECOND)
    }
}