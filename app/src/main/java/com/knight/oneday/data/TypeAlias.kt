package com.knight.oneday.data

import com.knight.oneday.utilities.SettingPreferences

typealias HourString = String

fun HourString.toHourOfDay(isAm: Boolean = false): Int {
    if (SettingPreferences.is12HMode()) {

    } else {

    }
    return 0
}