package com.niraj.notificationdatacollector.utils

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context

object ForegroundAppUtil {

    fun getForegroundApp(
        context: Context
    ): String {

        val usageStatsManager =
            context.getSystemService(
                Context.USAGE_STATS_SERVICE
            ) as UsageStatsManager

        val endTime =
            System.currentTimeMillis()

        val startTime =
            endTime - 60_000

        val events =
            usageStatsManager.queryEvents(
                startTime,
                endTime
            )

        val event =
            UsageEvents.Event()

        var packageName = "UNKNOWN"

        while (events.hasNextEvent()) {

            events.getNextEvent(event)

            if (
                event.eventType ==
                UsageEvents.Event.MOVE_TO_FOREGROUND
            ) {

                packageName =
                    event.packageName
            }
        }

        return packageName
    }
}