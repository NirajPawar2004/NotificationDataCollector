package com.niraj.notificationdatacollector.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateTimeUtils {

    private val fullFormatter =
        SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            Locale.getDefault()
        )

    private val dateFormatter =
        SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        )

    private val timeFormatter =
        SimpleDateFormat(
            "HH:mm:ss",
            Locale.getDefault()
        )

    private val dayFormatter =
        SimpleDateFormat(
            "EEEE",
            Locale.getDefault()
        )

    private val monthFormatter =
        SimpleDateFormat(
            "MMMM",
            Locale.getDefault()
        )

    fun currentTimestamp(): Long {
        return System.currentTimeMillis()
    }

    fun currentDate(): Date {
        return Date()
    }

    fun formatTimestamp(
        timestamp: Long
    ): String {

        return fullFormatter.format(Date(timestamp))
    }

    fun formatDate(
        timestamp: Long
    ): String {

        return dateFormatter.format(Date(timestamp))
    }

    fun formatTime(
        timestamp: Long
    ): String {

        return timeFormatter.format(Date(timestamp))
    }

    fun dayOfWeek(
        timestamp: Long
    ): String {

        return dayFormatter.format(Date(timestamp))
    }

    fun month(
        timestamp: Long
    ): String {

        return monthFormatter.format(Date(timestamp))
    }

    fun year(
        timestamp: Long
    ): Int {

        return formatTimestamp(timestamp)
            .substring(0, 4)
            .toInt()
    }

    fun hour(
        timestamp: Long
    ): Int {

        return formatTimestamp(timestamp)
            .substring(11, 13)
            .toInt()
    }

    fun minute(
        timestamp: Long
    ): Int {

        return formatTimestamp(timestamp)
            .substring(14, 16)
            .toInt()
    }

    fun second(
        timestamp: Long
    ): Int {

        return formatTimestamp(timestamp)
            .substring(17, 19)
            .toInt()
    }

    fun elapsedTime(
        start: Long,
        end: Long
    ): Long {

        return end - start
    }

    fun humanReadableDuration(
        millis: Long
    ): String {

        val days =
            TimeUnit.MILLISECONDS.toDays(millis)

        val hours =
            TimeUnit.MILLISECONDS.toHours(millis) % 24

        val minutes =
            TimeUnit.MILLISECONDS.toMinutes(millis) % 60

        val seconds =
            TimeUnit.MILLISECONDS.toSeconds(millis) % 60

        return buildString {

            if (days > 0)
                append("${days}d ")

            if (hours > 0)
                append("${hours}h ")

            if (minutes > 0)
                append("${minutes}m ")

            append("${seconds}s")
        }
    }

    fun isToday(
        timestamp: Long
    ): Boolean {

        return formatDate(timestamp) ==
                formatDate(System.currentTimeMillis())
    }

    fun isSameDay(
        first: Long,
        second: Long
    ): Boolean {

        return formatDate(first) ==
                formatDate(second)
    }

    fun unixSeconds(): Long {

        return System.currentTimeMillis() / 1000
    }

    fun currentHour(): Int {

        return hour(System.currentTimeMillis())
    }

    fun currentMinute(): Int {

        return minute(System.currentTimeMillis())
    }

    fun currentSecond(): Int {

        return second(System.currentTimeMillis())
    }
}