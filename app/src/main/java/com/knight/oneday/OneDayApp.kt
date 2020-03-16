package com.knight.oneday

import android.app.Application

class OneDayApp : Application() {

    companion object {
        private var instance: Application? = null
        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}