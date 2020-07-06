package com.knight.oneday.nav.strategy

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.knight.oneday.R
import com.knight.oneday.nav.BottomNavDrawerFragment
import com.knight.oneday.task.TaskFragment
import com.knight.oneday.utilities.TaskType
import java.lang.RuntimeException

class AppJumpStrategy(
    private val nav: BottomNavDrawerFragment
) :
    NavController.OnDestinationChangedListener, JumpStrategy {

    init {
        nav.findNavController().addOnDestinationChangedListener(this)
    }

    private val taskJumpStrategy = TaskFragmentJumpStrategy(nav)
    private val categoryJumpStrategy = CategoryFragmentJumpStrategy(nav)

    private var currentJumpStrategy: JumpStrategy = taskJumpStrategy

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        currentJumpStrategy = when (destination.id) {
            R.id.taskFragment -> taskJumpStrategy
            R.id.categoryFragment -> categoryJumpStrategy
            else -> taskJumpStrategy
        }
    }

    override fun jumpHome() {
        currentJumpStrategy.jumpHome()
    }

    override fun jumpAbout() {
        currentJumpStrategy.jumpAbout()
    }

    override fun jumpSetting() {
        currentJumpStrategy.jumpSetting()
    }

    override fun jumpChart() {
        currentJumpStrategy.jumpChart()
    }

    override fun jumpCategory(taskType: TaskType) {
        currentJumpStrategy.jumpCategory(taskType)
    }

    override fun jumpByFloatingBar() {
        currentJumpStrategy.jumpByFloatingBar()
    }

    override fun jumpSearch() {
        currentJumpStrategy.jumpSearch()
    }
}