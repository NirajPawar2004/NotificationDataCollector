package com.niraj.notificationdatacollector.utils

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.PowerManager
import android.view.WindowManager

object DeviceInfoUtil {

    data class DeviceInfo(
        val batteryLevel: Int,
        val charging: Boolean,
        val batterySaver: Boolean,
        val ringerMode: String,
        val mediaVolume: Int,
        val notificationVolume: Int,
        val alarmVolume: Int,
        val internetAvailable: Boolean,
        val wifiConnected: Boolean,
        val mobileDataConnected: Boolean,
        val connectionType: String,
        val screenOn: Boolean,
        val screenOrientation: String
    )

    fun collect(context: Context): DeviceInfo {

        val batteryIntent = context.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )

        val batteryLevel = batteryIntent?.getIntExtra(
            BatteryManager.EXTRA_LEVEL,
            -1
        ) ?: -1

        val status = batteryIntent?.getIntExtra(
            BatteryManager.EXTRA_STATUS,
            -1
        ) ?: -1

        val charging =
            status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL

        val powerManager =
            context.getSystemService(Context.POWER_SERVICE) as PowerManager

        val batterySaver = powerManager.isPowerSaveMode

        val audio =
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val ringerMode = when (audio.ringerMode) {
            AudioManager.RINGER_MODE_NORMAL -> "NORMAL"
            AudioManager.RINGER_MODE_VIBRATE -> "VIBRATE"
            AudioManager.RINGER_MODE_SILENT -> "SILENT"
            else -> "UNKNOWN"
        }

        val mediaVolume =
            audio.getStreamVolume(AudioManager.STREAM_MUSIC)

        val notificationVolume =
            audio.getStreamVolume(AudioManager.STREAM_NOTIFICATION)

        val alarmVolume =
            audio.getStreamVolume(AudioManager.STREAM_ALARM)

        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

        val network = connectivity.activeNetwork
        val capability = connectivity.getNetworkCapabilities(network)

        val internet =
            capability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false

        val wifi =
            capability?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                ?: false

        val mobile =
            capability?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                ?: false

        val connection = when {
            wifi -> "WiFi"
            mobile -> "Mobile"
            else -> "None"
        }

        val screenOn = powerManager.isInteractive

        val rotation =
            (context.getSystemService(Context.WINDOW_SERVICE)
                    as WindowManager)
                .defaultDisplay
                .rotation

        val orientation = when (rotation) {
            0 -> "Portrait"
            1 -> "Landscape"
            2 -> "Reverse Portrait"
            3 -> "Reverse Landscape"
            else -> "Unknown"
        }

        return DeviceInfo(
            batteryLevel = batteryLevel,
            charging = charging,
            batterySaver = batterySaver,
            ringerMode = ringerMode,
            mediaVolume = mediaVolume,
            notificationVolume = notificationVolume,
            alarmVolume = alarmVolume,
            internetAvailable = internet,
            wifiConnected = wifi,
            mobileDataConnected = mobile,
            connectionType = connection,
            screenOn = screenOn,
            screenOrientation = orientation
        )
    }
}