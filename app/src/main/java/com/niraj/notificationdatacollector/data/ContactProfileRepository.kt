package com.niraj.notificationdatacollector.data

import com.niraj.notificationdatacollector.model.ContactProfileEntity

class ContactProfileRepository(
    private val dao: ContactProfileDao
) {

    suspend fun insert(
        contact: ContactProfileEntity
    ): Long {

        return dao.insert(contact)
    }

    suspend fun update(
        contact: ContactProfileEntity
    ) {

        dao.update(contact)
    }

    suspend fun delete(
        contact: ContactProfileEntity
    ) {

        dao.delete(contact)
    }

    suspend fun getAll(): List<ContactProfileEntity> {

        return dao.getAll()
    }

    suspend fun getBySenderName(
        senderName: String
    ): ContactProfileEntity? {

        return dao.getBySenderName(senderName)
    }

    suspend fun getByPhone(
        senderPhone: String
    ): ContactProfileEntity? {

        return dao.getByPhone(senderPhone)
    }

    suspend fun getCount(): Int {

        return dao.getCount()
    }

    suspend fun deleteAll() {

        dao.deleteAll()
    }
}