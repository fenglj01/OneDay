package com.knight.oneday

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.facebook.stetho.Stetho
import com.google.android.gms.ads.MobileAds
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
            ThemePreference(PreferenceManager.getDefaultSharedPreferences(this))
        Stetho.initializeWithDefaults(this)
        MobileAds.initialize(this) {}
    }


}