package com.niraj.notificationdatacollector.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import java.util.concurrent.atomic.AtomicBoolean

class ScreenStateReceiver : BroadcastReceiver() {

    companion object {

        private val screenOn = AtomicBoolean(false)

        private val deviceUnlocked = AtomicBoolean(false)

        private var lastScreenOnTime = 0L

        private var lastScreenOffTime = 0L

        private var unlockCount = 0

        fun isScreenOn(): Boolean {
            return screenOn.get()
        }

        fun isDeviceUnlocked(): Boolean {
            return deviceUnlocked.get()
        }

        fun getUnlockCount(): Int {
            return unlockCount
        }

        fun getLastScreenOnTime(): Long {
            return lastScreenOnTime
        }

        fun getLastScreenOffTime(): Long {
            return lastScreenOffTime
        }

        fun screenOnDuration(): Long {

            if (!screenOn.get()) return 0L

            return System.currentTimeMillis() - lastScreenOnTime
        }

        fun isInteractive(
            context: Context
        ): Boolean {

            val pm =
                context.getSystemService(Context.POWER_SERVICE)
                        as PowerManager

            return pm.isInteractive
        }
    }

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        when (intent.action) {

            Intent.ACTION_SCREEN_ON -> {

                screenOn.set(true)

                lastScreenOnTime =
                    System.currentTimeMillis()
            }

            Intent.ACTION_SCREEN_OFF -> {

                screenOn.set(false)

                deviceUnlocked.set(false)

                lastScreenOffTime =
                    System.currentTimeMillis()
            }

            Intent.ACTION_USER_PRESENT -> {

                deviceUnlocked.set(true)

                unlockCount++
            }
        }
    }
}