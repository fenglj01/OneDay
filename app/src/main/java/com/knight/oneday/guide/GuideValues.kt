package com.knight.oneday.guide

import android.graphics.Color
import com.knight.oneday.OneDayApp
import com.knight.oneday.R
import com.knight.oneday.utilities.getString
import com.knight.oneday.utilities.themeColor
import com.knight.oneday.views.themeColor


val backgroundColors = arrayListOf(
    themeColor(R.attr.colorSurface),
    Color.parseColor("#1EB980"),
    themeColor(R.attr.colorSurface),
    Color.parseColor("#1EB980")
)

val resourceArray = arrayListOf(
    R.mipmap.ill_welcome,
    R.mipmap.ill_finished_task,
    R.mipmap.ill_delete_task,
    R.mipmap.ill_edit_task
)

val titleArray = arrayListOf(
    "welcome",
    "finished",
    "delete",
    "edit"
)

val contentArray = arrayListOf(
    getString(R.string.guide_step_one),
    getString(R.string.guide_step_two),
    getString(R.string.guide_step_three),
    getString(R.string.guide_step_four)
)