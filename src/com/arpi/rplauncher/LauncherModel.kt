package com.arpi.rplauncher

import android.content.Context
import android.content.Intent
import java.util.ArrayList

class LauncherModel(private val mContext: Context) {
    private val mAppList: ArrayList<AppInfo> = ArrayList()

    private val allowedApps = listOf(
        // "com.example.android.camera2.basic",  // Camera
        "com.astro.timertest",
        "com.android.calendar", // Calendar
        "com.android.deskclock", // Clock
        // "com.android.gallery3d", // Gallery
        "com.perracolabs.pixtica"  // pixtica camera
    )

    suspend fun getAppList(): ArrayList<AppInfo> {
        return mAppList
    }

    suspend fun loadAppList() {
        mAppList.clear()
        val pm = mContext.packageManager

        // Retrieve all launcher apps
        val rInfos = pm.queryIntentActivities(
            Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER), 0
        )
        
        // Filter apps based on the whitelist
        for (ri in rInfos) {
            val ai = AppInfo(mContext, ri.activityInfo)
            if (ai.componentName.packageName in allowedApps) {
                addToAppList(ai)
            }
        }
    }

    private fun addToAppList(info: AppInfo) {
        if (info.componentName.packageName.equals(LEANBACK_SETTINGS_PACKAGE))
            return

        for (ai in mAppList) {
            if (info.componentName == ai.componentName) {
                return
            }
        }
        mAppList.add(info)
    }

    private val LEANBACK_SETTINGS_PACKAGE = "com.android.tv.settings"
    private val CATEGORY_LEANBACK_SETTINGS = "android.intent.category.LEANBACK_SETTINGS"
}
