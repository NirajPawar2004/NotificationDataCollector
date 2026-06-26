package com.niraj.notificationdatacollector.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build

object AppInfoUtil {

    data class AppInfo(

        val appName: String,

        val packageName: String,

        val versionName: String,

        val versionCode: Long,

        val isSystemApp: Boolean

    )

    fun collect(
        context: Context,
        packageName: String
    ): AppInfo {

        val pm = context.packageManager

        return try {

            val applicationInfo =
                pm.getApplicationInfo(packageName, 0)

            val packageInfo =
                pm.getPackageInfo(packageName, 0)

            val versionCode =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                    packageInfo.longVersionCode
                else
                    packageInfo.versionCode.toLong()

            AppInfo(

                appName =
                    pm.getApplicationLabel(applicationInfo)
                        .toString(),

                packageName = packageName,

                versionName =
                    packageInfo.versionName ?: "",

                versionCode = versionCode,

                isSystemApp =
                    (applicationInfo.flags and
                            ApplicationInfo.FLAG_SYSTEM) != 0

            )

        } catch (_: PackageManager.NameNotFoundException) {

            AppInfo(

                appName = packageName,

                packageName = packageName,

                versionName = "",

                versionCode = 0,

                isSystemApp = false

            )
        }
    }
}