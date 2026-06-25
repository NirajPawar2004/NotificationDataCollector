package com.niraj.notificationdatacollector.receiver

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkReceiver {

    data class NetworkInfo(
        val connected: Boolean,
        val wifi: Boolean,
        val mobile: Boolean,
        val ethernet: Boolean,
        val vpn: Boolean,
        val bluetooth: Boolean,
        val connectionType: String,
        val validated: Boolean,
        val metered: Boolean
    )

    fun getNetworkInfo(
        context: Context
    ): NetworkInfo {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network =
                connectivityManager.activeNetwork

            val capabilities =
                connectivityManager.getNetworkCapabilities(network)

            if (capabilities == null) {

                return NetworkInfo(
                    connected = false,
                    wifi = false,
                    mobile = false,
                    ethernet = false,
                    vpn = false,
                    bluetooth = false,
                    connectionType = "NONE",
                    validated = false,
                    metered = false
                )
            }

            val wifi =
                capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )

            val mobile =
                capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                )

            val ethernet =
                capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_ETHERNET
                )

            val vpn =
                capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_VPN
                )

            val bluetooth =
                capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_BLUETOOTH
                )

            val validated =
                capabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_VALIDATED
                )

            val metered =
                connectivityManager.isActiveNetworkMetered

            val type = when {

                wifi -> "WiFi"

                mobile -> "Mobile"

                ethernet -> "Ethernet"

                vpn -> "VPN"

                bluetooth -> "Bluetooth"

                else -> "Unknown"
            }

            return NetworkInfo(
                connected = true,
                wifi = wifi,
                mobile = mobile,
                ethernet = ethernet,
                vpn = vpn,
                bluetooth = bluetooth,
                connectionType = type,
                validated = validated,
                metered = metered
            )
        }

        @Suppress("DEPRECATION")
        val networkInfo =
            connectivityManager.activeNetworkInfo

        val connected =
            networkInfo?.isConnected == true

        return NetworkInfo(
            connected = connected,
            wifi = networkInfo?.type == ConnectivityManager.TYPE_WIFI,
            mobile = networkInfo?.type == ConnectivityManager.TYPE_MOBILE,
            ethernet = networkInfo?.type == ConnectivityManager.TYPE_ETHERNET,
            vpn = false,
            bluetooth = false,
            connectionType = networkInfo?.typeName ?: "Unknown",
            validated = connected,
            metered = connectivityManager.isActiveNetworkMetered
        )
    }
}