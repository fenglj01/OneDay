package com.knight.oneday.nav.strategy

import com.knight.oneday.utilities.TaskType

interface JumpStrategy {

    fun jumpHome()

    fun jumpAbout()

    fun jumpSetting()

    fun jumpChart()

    fun jumpCategory(taskType: TaskType)

    fun jumpByFloatingBar()

    fun jumpSearch()

}