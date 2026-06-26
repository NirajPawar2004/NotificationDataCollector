package com.niraj.notificationdatacollector.utils

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.PowerManager

object DeviceInfoUtil {

    data class DeviceInfo(

        val batteryLevel: Int,

        val charging: Boolean,

        val screenOn: Boolean,

        val phoneLocked: Boolean,

        val internetStatus: Boolean,

        val doNotDisturb: Boolean

    )

    fun collect(
        context: Context
    ): DeviceInfo {

        val batteryIntent = context.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )

        val batteryLevel =
            batteryIntent?.getIntExtra(
                BatteryManager.EXTRA_LEVEL,
                -1
            ) ?: -1

        val batteryStatus =
            batteryIntent?.getIntExtra(
                BatteryManager.EXTRA_STATUS,
                -1
            ) ?: -1

        val charging =
            batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING ||
                    batteryStatus == BatteryManager.BATTERY_STATUS_FULL

        val powerManager =
            context.getSystemService(
                Context.POWER_SERVICE
            ) as PowerManager

        val screenOn =
            powerManager.isInteractive

        val keyguardManager =
            context.getSystemService(
                Context.KEYGUARD_SERVICE
            ) as KeyguardManager

        val phoneLocked =
            keyguardManager.isKeyguardLocked

        val connectivity =
            context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager

        val network =
            connectivity.activeNetwork

        val capability =
            connectivity.getNetworkCapabilities(
                network
            )

        val internetStatus =
            capability?.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET
            ) ?: false

        val audioManager =
            context.getSystemService(
                Context.AUDIO_SERVICE
            ) as AudioManager

        val doNotDisturb =
            audioManager.ringerMode ==
                    AudioManager.RINGER_MODE_SILENT

        return DeviceInfo(

            batteryLevel = batteryLevel,

            charging = charging,

            screenOn = screenOn,

            phoneLocked = phoneLocked,

            internetStatus = internetStatus,

            doNotDisturb = doNotDisturb

        )
    }
}