package com.niraj.notificationdatacollector.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build

object AppInfoUtil {

    data class AppInfoData(
        val appName: String,
        val packageName: String,
        val versionName: String,
        val versionCode: Long,
        val installTime: Long,
        val updateTime: Long,
        val isSystemApp: Boolean,
        val category: String
    )

    fun getAppInfo(
        context: Context,
        packageName: String
    ): AppInfoData {

        val pm = context.packageManager

        return try {

            val appInfo = pm.getApplicationInfo(packageName, 0)
            val packageInfo = pm.getPackageInfo(packageName, 0)

            val appName =
                pm.getApplicationLabel(appInfo).toString()

            val versionName =
                packageInfo.versionName ?: ""

            val versionCode =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                    packageInfo.longVersionCode
                else
                    packageInfo.versionCode.toLong()

            val installTime =
                packageInfo.firstInstallTime

            val updateTime =
                packageInfo.lastUpdateTime

            val isSystem =
                (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0

            val category =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    when (appInfo.category) {
                        ApplicationInfo.CATEGORY_AUDIO -> "Audio"
                        ApplicationInfo.CATEGORY_GAME -> "Game"
                        ApplicationInfo.CATEGORY_IMAGE -> "Image"
                        ApplicationInfo.CATEGORY_MAPS -> "Maps"
                        ApplicationInfo.CATEGORY_NEWS -> "News"
                        ApplicationInfo.CATEGORY_PRODUCTIVITY -> "Productivity"
                        ApplicationInfo.CATEGORY_SOCIAL -> "Social"
                        ApplicationInfo.CATEGORY_VIDEO -> "Video"
                        else -> "Other"
                    }
                } else {
                    "Unknown"
                }

            AppInfoData(
                appName = appName,
                packageName = packageName,
                versionName = versionName,
                versionCode = versionCode,
                installTime = installTime,
                updateTime = updateTime,
                isSystemApp = isSystem,
                category = category
            )

        } catch (e: PackageManager.NameNotFoundException) {

            AppInfoData(
                appName = packageName,
                packageName = packageName,
                versionName = "",
                versionCode = 0,
                installTime = 0L,
                updateTime = 0L,
                isSystemApp = false,
                category = "Unknown"
            )
        }
    }
}