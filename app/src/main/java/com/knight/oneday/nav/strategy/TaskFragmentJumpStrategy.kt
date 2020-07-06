package com.knight.oneday.nav.strategy

import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.knight.oneday.R
import com.knight.oneday.nav.BottomNavDrawerFragment
import com.knight.oneday.task.TaskFragment
import com.knight.oneday.task.TaskFragmentDirections
import com.knight.oneday.utilities.TaskType
import java.util.*

class TaskFragmentJumpStrategy(
    private val nav: BottomNavDrawerFragment
) : JumpStrategy {

    private var selectedCalendar: Calendar? = null

    init {
        /* 对首页日期的选择监听 保证在跳转到 */
        TaskFragment.selectDate.observe(nav, Observer {
            selectedCalendar = it
        })
    }

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
        //nav.findNavController().navigate(R.id.action_taskFragment_to_addEventFragment)
        nav.findNavController().navigate(
            TaskFragmentDirections.actionTaskFragmentToAddEventFragment(
                task = null,
                date = selectedCalendar
            )
        )
    }

    override fun jumpSearch() {
        nav.findNavController().navigate(R.id.action_taskFragment_to_searchFragment)
    }
}