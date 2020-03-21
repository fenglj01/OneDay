package com.knight.oneday

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.knight.oneday.utilities.ThemePreference

class OneDayApp : Application() {

    lateinit var themePreference: ThemePreference

    companion object {
        private var instance: Application? = null
        fun instance() = instance!!
        const val ONE_DAY_PREFERENCE = "one_day_preference"
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        themePreference =
            ThemePreference(getSharedPreferences(ONE_DAY_PREFERENCE, Context.MODE_PRIVATE))
    }


}