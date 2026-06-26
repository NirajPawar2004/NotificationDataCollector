package com.niraj.notificationdatacollector.ai

object NotificationFrequencyEngine {

    private val notificationCount =
        mutableMapOf<String, Int>()

    fun increment(
        packageName: String
    ) {

        val count =
            notificationCount[packageName] ?: 0

        notificationCount[packageName] =
            count + 1
    }

    fun getFrequency(
        packageName: String
    ): Int {

        return notificationCount[packageName]
            ?: 0
    }

    fun reset(
        packageName: String
    ) {

        notificationCount.remove(
            packageName
        )
    }

    fun clear() {

        notificationCount.clear()
    }
}