package com.niraj.notificationdatacollector.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.PowerManager
import java.util.concurrent.atomic.AtomicInteger

object BatteryReceiver {

    private val batteryLevel = AtomicInteger(-1)

    private var charging = false

    private var chargingType = "Unknown"

    private var batteryHealth = "Unknown"

    private var batteryTemperature = 0f

    private var batteryVoltage = 0

    private var batteryTechnology = ""

    private var batteryStatus = ""

    private var powerSaveMode = false

    fun update(context: Context) {

        val batteryIntent = context.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        ) ?: return

        batteryLevel.set(
            batteryIntent.getIntExtra(
                BatteryManager.EXTRA_LEVEL,
                -1
            )
        )

        val status =
            batteryIntent.getIntExtra(
                BatteryManager.EXTRA_STATUS,
                -1
            )

        charging =
            status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL

        chargingType = when (
            batteryIntent.getIntExtra(
                BatteryManager.EXTRA_PLUGGED,
                -1
            )
        ) {

            BatteryManager.BATTERY_PLUGGED_USB ->
                "USB"

            BatteryManager.BATTERY_PLUGGED_AC ->
                "AC"

            BatteryManager.BATTERY_PLUGGED_WIRELESS ->
                "Wireless"

            else ->
                "None"
        }

        batteryHealth = when (
            batteryIntent.getIntExtra(
                BatteryManager.EXTRA_HEALTH,
                -1
            )
        ) {

            BatteryManager.BATTERY_HEALTH_GOOD ->
                "Good"

            BatteryManager.BATTERY_HEALTH_OVERHEAT ->
                "Overheat"

            BatteryManager.BATTERY_HEALTH_DEAD ->
                "Dead"

            BatteryManager.BATTERY_HEALTH_COLD ->
                "Cold"

            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE ->
                "Over Voltage"

            BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE ->
                "Failure"

            else ->
                "Unknown"
        }

        batteryTemperature =
            batteryIntent.getIntExtra(
                BatteryManager.EXTRA_TEMPERATURE,
                0
            ) / 10f

        batteryVoltage =
            batteryIntent.getIntExtra(
                BatteryManager.EXTRA_VOLTAGE,
                0
            )

        batteryTechnology =
            batteryIntent.getStringExtra(
                BatteryManager.EXTRA_TECHNOLOGY
            ) ?: ""

        batteryStatus = when (status) {

            BatteryManager.BATTERY_STATUS_CHARGING ->
                "Charging"

            BatteryManager.BATTERY_STATUS_FULL ->
                "Full"

            BatteryManager.BATTERY_STATUS_DISCHARGING ->
                "Discharging"

            BatteryManager.BATTERY_STATUS_NOT_CHARGING ->
                "Not Charging"

            else ->
                "Unknown"
        }

        val powerManager =
            context.getSystemService(Context.POWER_SERVICE)
                    as PowerManager

        powerSaveMode =
            powerManager.isPowerSaveMode
    }

    fun batteryLevel() = batteryLevel.get()

    fun isCharging() = charging

    fun chargingType() = chargingType

    fun batteryHealth() = batteryHealth

    fun batteryTemperature() = batteryTemperature

    fun batteryVoltage() = batteryVoltage

    fun batteryTechnology() = batteryTechnology

    fun batteryStatus() = batteryStatus

    fun powerSaveMode() = powerSaveMode
}