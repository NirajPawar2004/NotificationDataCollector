package com.niraj.notificationdatacollector.collector

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.PowerManager

object BatteryCollector {

    fun getBatteryLevel(context: Context): Int {

        val batteryManager =
            context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

        return batteryManager.getIntProperty(
            BatteryManager.BATTERY_PROPERTY_CAPACITY
        )
    }

    fun isCharging(context: Context): Boolean {

        val batteryIntent = context.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        ) ?: return false

        val status = batteryIntent.getIntExtra(
            BatteryManager.EXTRA_STATUS,
            BatteryManager.BATTERY_STATUS_UNKNOWN
        )

        return status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL
    }

    fun isBatterySaverEnabled(context: Context): Boolean {

        val powerManager =
            context.getSystemService(Context.POWER_SERVICE) as PowerManager

        return powerManager.isPowerSaveMode
    }
}