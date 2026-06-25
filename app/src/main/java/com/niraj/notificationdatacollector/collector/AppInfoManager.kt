package com.niraj.notificationdatacollector.collector

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.os.Build

object AppInfoManager {

    fun getAppName(
        context: Context,
        packageName: String
    ): String {

        return try {

            val applicationInfo =
                context.packageManager.getApplicationInfo(packageName, 0)

            applicationInfo.loadLabel(
                context.packageManager
            ).toString()

        } catch (e: Exception) {

            packageName
        }
    }

    fun getVersionName(
        context: Context,
        packageName: String
    ): String {

        return try {

            val packageInfo: PackageInfo =
                context.packageManager.getPackageInfo(packageName, 0)

            packageInfo.versionName ?: "Unknown"

        } catch (e: Exception) {

            "Unknown"
        }
    }

    fun getVersionCode(
        context: Context,
        packageName: String
    ): Long {

        return try {

            val packageInfo =
                context.packageManager.getPackageInfo(packageName, 0)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode.toLong()
            }

        } catch (e: Exception) {

            -1L
        }
    }

    fun isSystemApp(
        context: Context,
        packageName: String
    ): Boolean {

        return try {

            val applicationInfo: ApplicationInfo =
                context.packageManager.getApplicationInfo(packageName, 0)

            (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0

        } catch (e: Exception) {

            false
        }
    }
}