package com.knight.oneday.utilities

import com.knight.oneday.OneDayApp
import com.knight.oneday.R
import com.knight.oneday.views.themeColor
import java.util.*


fun getString(id: Int): String = OneDayApp.instance().getString(id)

fun themeColor(attr: Int): Int = OneDayApp.instance().themeColor(attr)
