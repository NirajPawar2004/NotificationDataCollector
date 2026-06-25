package com.niraj.notificationdatacollector.collector

import android.content.Context
import android.content.res.Configuration
import android.media.AudioManager
import android.os.PowerManager

object DeviceCollector {

    fun isScreenOn(context: Context): Boolean {

        val powerManager =
            context.getSystemService(Context.POWER_SERVICE) as PowerManager

        return powerManager.isInteractive
    }

    fun getScreenOrientation(context: Context): String {

        return when (context.resources.configuration.orientation) {

            Configuration.ORIENTATION_PORTRAIT -> "Portrait"

            Configuration.ORIENTATION_LANDSCAPE -> "Landscape"

            else -> "Unknown"
        }
    }

    fun getRingerMode(context: Context): String {

        val audioManager =
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        return when (audioManager.ringerMode) {

            AudioManager.RINGER_MODE_NORMAL -> "Normal"

            AudioManager.RINGER_MODE_VIBRATE -> "Vibrate"

            AudioManager.RINGER_MODE_SILENT -> "Silent"

            else -> "Unknown"
        }
    }

    fun getMediaVolume(context: Context): Int {

        val audioManager =
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    }

    fun getNotificationVolume(context: Context): Int {

        val audioManager =
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        return audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION)
    }

    fun getAlarmVolume(context: Context): Int {

        val audioManager =
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        return audioManager.getStreamVolume(AudioManager.STREAM_ALARM)
    }
}