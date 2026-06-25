package com.niraj.notificationdatacollector.collector

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkCollector {

    fun isInternetAvailable(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false

        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        )
    }

    fun isWifiConnected(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false

        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_WIFI
        )
    }

    fun isMobileDataConnected(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false

        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR
        )
    }

    fun getConnectionType(context: Context): String {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return "No Connection"

        val capabilities =
            connectivityManager.getNetworkCapabilities(network)
                ?: return "No Connection"

        return when {

            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->
                "Wi-Fi"

            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->
                "Mobile Data"

            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->
                "Ethernet"

            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) ->
                "Bluetooth"

            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ->
                "VPN"

            else ->
                "Unknown"
        }
    }
}