package com.niraj.notificationdatacollector.collector

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build

object UsageStatsCollector {

    data class UsageInfo(
        val foregroundApp: String,
        val lastUsedApp: String,
        val totalForegroundTime: Long,
        val lastTimeUsed: Long,
        val usageAvailable: Boolean
    )

    fun collect(
        context: Context
    ): UsageInfo {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            return UsageInfo(
                foregroundApp = "",
                lastUsedApp = "",
                totalForegroundTime = 0L,
                lastTimeUsed = 0L,
                usageAvailable = false
            )
        }

        val usageManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE)
                    as UsageStatsManager

        val endTime = System.currentTimeMillis()
        val beginTime = endTime - (1000 * 60 * 60)

        val usageStats =
            usageManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                beginTime,
                endTime
            )

        if (usageStats.isNullOrEmpty()) {

            return UsageInfo(
                foregroundApp = "",
                lastUsedApp = "",
                totalForegroundTime = 0L,
                lastTimeUsed = 0L,
                usageAvailable = false
            )
        }

        val latest =
            usageStats.maxByOrNull {
                it.lastTimeUsed
            }

        val foreground =
            latest?.packageName ?: ""

        val totalTime =
            latest?.totalTimeInForeground ?: 0L

        val lastUsed =
            latest?.lastTimeUsed ?: 0L

        var currentForeground = foreground

        val events =
            usageManager.queryEvents(beginTime, endTime)

        val event = UsageEvents.Event()

        while (events.hasNextEvent()) {

            events.getNextEvent(event)

            if (event.eventType ==
                UsageEvents.Event.ACTIVITY_RESUMED
            ) {

                currentForeground = event.packageName
            }
        }

        return UsageInfo(
            foregroundApp = currentForeground,
            lastUsedApp = foreground,
            totalForegroundTime = totalTime,
            lastTimeUsed = lastUsed,
            usageAvailable = true
        )
    }
}