package com.niraj.notificationdatacollector.ai

object PriorityLabelEngine {

    data class PriorityResult(

        val label: String,

        val priorityClass: Int

    )

    fun predictLabel(

        favoriteContact: Boolean,

        senderType: String,

        opened: Boolean,

        dismissed: Boolean,

        responseTime: Long

    ): PriorityResult {

        // -----------------------------
        // Favorite Contacts
        // -----------------------------
        if (favoriteContact) {

            return PriorityResult(

                label = "HIGH",

                priorityClass = 2

            )
        }

        // -----------------------------
        // Emergency Senders
        // -----------------------------
        if (

            senderType == "FAMILY" ||

            senderType == "EMERGENCY"

        ) {

            return PriorityResult(

                label = "HIGH",

                priorityClass = 2

            )
        }

        // -----------------------------
        // Opened Quickly
        // -----------------------------
        if (

            opened &&

            responseTime in 0..30_000

        ) {

            return PriorityResult(

                label = "HIGH",

                priorityClass = 2

            )
        }

        // -----------------------------
        // Opened Later
        // -----------------------------
        if (

            opened &&

            responseTime <= 300_000

        ) {

            return PriorityResult(

                label = "MEDIUM",

                priorityClass = 1

            )
        }

        // -----------------------------
        // Dismissed
        // -----------------------------
        if (dismissed) {

            return PriorityResult(

                label = "LOW",

                priorityClass = 0

            )
        }

        // -----------------------------
        // Default
        // -----------------------------
        return PriorityResult(

            label = "MEDIUM",

            priorityClass = 1

        )
    }
}