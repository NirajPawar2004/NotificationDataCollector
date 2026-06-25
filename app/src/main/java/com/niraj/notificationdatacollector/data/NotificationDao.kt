package com.niraj.notificationdatacollector.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.niraj.notificationdatacollector.model.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    // ==========================================
    // Insert
    // ==========================================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(
        notification: NotificationEntity
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(
        notifications: List<NotificationEntity>
    )

    // ==========================================
    // Live Data
    // ==========================================

    @Query("SELECT * FROM notifications ORDER BY timestampMillis DESC")
    fun observeNotifications(): Flow<List<NotificationEntity>>

    // ==========================================
    // Get All
    // ==========================================

    @Query("SELECT * FROM notifications ORDER BY timestampMillis DESC")
    suspend fun getAllNotifications(): List<NotificationEntity>

    @Query("SELECT * FROM notifications WHERE id = :id LIMIT 1")
    suspend fun getNotificationById(
        id: Long
    ): NotificationEntity?

    // ==========================================
    // Statistics
    // ==========================================

    @Query("SELECT COUNT(*) FROM notifications")
    suspend fun getNotificationCount(): Int

    @Query("SELECT COUNT(DISTINCT packageName) FROM notifications")
    suspend fun getUniqueAppCount(): Int

    @Query(
        """
        SELECT COUNT(*)
        FROM notifications
        WHERE date = :today
        """
    )
    suspend fun getTodayNotificationCount(
        today: String
    ): Int

    @Query(
        """
        SELECT COUNT(*)
        FROM notifications
        WHERE packageName = :packageName
        """
    )
    suspend fun getNotificationCountForApp(
        packageName: String
    ): Int

    // ==========================================
    // Delete
    // ==========================================

    @Query("DELETE FROM notifications")
    suspend fun deleteAllNotifications()
}