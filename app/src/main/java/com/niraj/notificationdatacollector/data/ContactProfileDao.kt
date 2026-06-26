package com.niraj.notificationdatacollector.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.niraj.notificationdatacollector.model.ContactProfileEntity

@Dao
interface ContactProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(
        contact: ContactProfileEntity
    ): Long

    @Update
    suspend fun update(
        contact: ContactProfileEntity
    )

    @Delete
    suspend fun delete(
        contact: ContactProfileEntity
    )

    @Query(
        """
        SELECT *
        FROM contact_profiles
        ORDER BY senderName ASC
        """
    )
    suspend fun getAll(): List<ContactProfileEntity>

    @Query(
        """
        SELECT *
        FROM contact_profiles
        WHERE senderName = :senderName
        LIMIT 1
        """
    )
    suspend fun getBySenderName(
        senderName: String
    ): ContactProfileEntity?

    @Query(
        """
        SELECT *
        FROM contact_profiles
        WHERE senderPhone = :senderPhone
        LIMIT 1
        """
    )
    suspend fun getByPhone(
        senderPhone: String
    ): ContactProfileEntity?

    @Query(
        """
        SELECT COUNT(*)
        FROM contact_profiles
        """
    )
    suspend fun getCount(): Int

    @Query(
        """
        DELETE FROM contact_profiles
        """
    )
    suspend fun deleteAll()
}