package com.knight.oneday.nav.strategy

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.knight.oneday.R
import com.knight.oneday.nav.BottomNavDrawerFragment
import com.knight.oneday.task.TaskFragmentDirections
import com.knight.oneday.utilities.TaskType

class TaskFragmentJumpStrategy(
    private val nav: BottomNavDrawerFragment
) : JumpStrategy {

    override fun jumpHome() {
    }

    override fun jumpAbout() {
    }

    override fun jumpSetting() {
        nav.findNavController().navigate(R.id.action_taskFragment_to_settingFragment)
    }

    override fun jumpChart() {
    }

    override fun jumpCategory(taskType: TaskType) {
        nav.findNavController().navigate(
            R.id.action_taskFragment_to_categoryFragment
        )
        BottomNavDrawerFragment.navTag.postValue(taskType)
    }

    override fun jumpByFloatingBar() {
        nav.findNavController().navigate(R.id.action_taskFragment_to_addEventFragment)
    }

    override fun jumpSearch() {
        nav.findNavController().navigate(R.id.action_taskFragment_to_searchFragment)
    }
}