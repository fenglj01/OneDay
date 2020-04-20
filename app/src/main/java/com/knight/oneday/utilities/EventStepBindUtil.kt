package com.knight.oneday.utilities

import android.view.View
import com.knight.oneday.data.Step
import java.util.*

fun getStepOverviewText(steps: List<Step>): String {
    val size = steps.size
    return if (size == 0) {
        ""
    } else {
        val nowStep = steps.first { it.state == EventState.UNFINISHED }
        "一共 $size 步 当前第 ${nowStep.serialNumber} 步: ${nowStep.content}"
    }
}