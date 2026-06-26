package com.niraj.notificationdatacollector.utils

import com.niraj.notificationdatacollector.data.ContactProfileRepository

class ContactMatcher(
    private val repository: ContactProfileRepository
) {

    data class ContactResult(

        val senderType: String,

        val favoriteContact: Boolean

    )

    suspend fun match(
        senderName: String,
        senderPhone: String = ""
    ): ContactResult {

        var profile = repository.getBySenderName(
            senderName
        )

        if (profile == null && senderPhone.isNotBlank()) {

            profile = repository.getByPhone(
                senderPhone
            )
        }

        return if (profile != null) {

            ContactResult(

                senderType =
                    profile.senderType,

                favoriteContact =
                    profile.isFavoriteContact

            )

        } else {

            ContactResult(

                senderType = "UNKNOWN",

                favoriteContact = false

            )
        }
    }
}