package com.niraj.notificationdatacollector.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.niraj.notificationdatacollector.model.NotificationEntity

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(
        notification: NotificationEntity
    ): Long

    @Update
    suspend fun update(
        notification: NotificationEntity
    )

    @Query(
        """
        SELECT *
        FROM notifications
        ORDER BY timestamp DESC
        """
    )
    suspend fun getAll(): List<NotificationEntity>

    @Query(
        """
        SELECT *
        FROM notifications
        WHERE notificationId = :id
        LIMIT 1
        """
    )
    suspend fun getById(
        id: Long
    ): NotificationEntity?

    @Query(
        """
        SELECT COUNT(*)
        FROM notifications
        """
    )
    suspend fun getCount(): Int

    @Query(
        """
        SELECT COUNT(DISTINCT packageName)
        FROM notifications
        """
    )
    suspend fun getUniqueApps(): Int

    @Query(
        """
        SELECT COUNT(*)
        FROM notifications
        WHERE date(timestamp / 1000, 'unixepoch') =
              date(:timestamp / 1000, 'unixepoch')
        """
    )
    suspend fun getTodayCount(
        timestamp: Long
    ): Int

    @Query(
        """
        DELETE FROM notifications
        """
    )
    suspend fun deleteAll()
}