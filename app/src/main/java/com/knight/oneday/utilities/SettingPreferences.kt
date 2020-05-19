package com.knight.oneday.utilities

import com.knight.oneday.R
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_CREATE_ALLOW_EXPIRED
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_CREATE_DAY_MODE
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_CREATE_TIME_SYSTEM
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_ALLOW_EXPIRED
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_ALLOW_FINISHED

/**
 * Create by FLJ in 2020/5/19 15:17
 * 设置
 */
object SettingPreferences {

    val createAllowExpired: Boolean by PreferenceAttrProxy(KEY_CREATE_ALLOW_EXPIRED, true)
    val createTimeSystem: String by PreferenceAttrProxy(
        KEY_CREATE_TIME_SYSTEM,
        getString(R.string.time_system_12h)
    )
    val createDayMode: String by PreferenceAttrProxy(
        KEY_CREATE_DAY_MODE,
        getString(R.string.value_today)
    )
    val showAllowExpired: Boolean by PreferenceAttrProxy(KEY_SHOW_ALLOW_EXPIRED, false)
    val showAllowFinished: Boolean by PreferenceAttrProxy(KEY_SHOW_ALLOW_FINISHED, false)

    fun is12HMode() = createTimeSystem == getString(R.string.time_system_12h)

}