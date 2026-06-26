package com.niraj.notificationdatacollector.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niraj.notificationdatacollector.data.NotificationRepository
import com.niraj.notificationdatacollector.model.NotificationEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val repository: NotificationRepository
) : ViewModel() {

    private val _notifications =
        MutableStateFlow<List<NotificationEntity>>(emptyList())

    val notifications: StateFlow<List<NotificationEntity>>
        get() = _notifications

    init {
        refresh()
    }

    fun refresh() {

        viewModelScope.launch {

            _notifications.value =
                repository.getAll()

        }
    }

    fun insertNotification(
        notification: NotificationEntity
    ) {

        viewModelScope.launch {

            repository.insert(notification)

            refresh()
        }
    }

    fun deleteAllNotifications() {

        viewModelScope.launch {

            repository.deleteAll()

            refresh()
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

        if (
            modelClass.isAssignableFrom(
                NotificationViewModel::class.java
            )
        ) {

            return NotificationViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}