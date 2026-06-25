package com.niraj.notificationdatacollector.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niraj.notificationdatacollector.data.NotificationRepository
import com.niraj.notificationdatacollector.model.NotificationEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val repository: NotificationRepository
) : ViewModel() {

    val notifications: StateFlow<List<NotificationEntity>> =
        repository.allNotifications.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertNotification(
        notification: NotificationEntity
    ) {
        viewModelScope.launch {
            repository.insert(notification)
        }
    }

    fun insertNotifications(
        notifications: List<NotificationEntity>
    ) {
        viewModelScope.launch {
            repository.insertAll(notifications)
        }
    }

    fun deleteAllNotifications() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}

class NotificationViewModelFactory(
    private val repository: NotificationRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}