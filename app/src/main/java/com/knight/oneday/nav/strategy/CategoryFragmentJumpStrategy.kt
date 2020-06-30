package com.knight.oneday.nav.strategy

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.knight.oneday.R
import com.knight.oneday.nav.BottomNavDrawerFragment
import com.knight.oneday.utilities.TaskType

class CategoryFragmentJumpStrategy(
    private val nav: BottomNavDrawerFragment
) : JumpStrategy {

    override fun jumpHome() {
        nav.findNavController().navigateUp()
    }

    override fun jumpAbout() {
    }

    override fun jumpSetting() {
        nav.findNavController().navigate(R.id.action_categoryFragment_to_settingFragment)
    }

    override fun jumpChart() {
    }

    override fun jumpCategory(taskType: TaskType) {
        BottomNavDrawerFragment.navTag.postValue(taskType)
    }

    override fun jumpByFloatingBar() {
        nav.findNavController().navigate(R.id.action_categoryFragment_to_addTaskFragment)
    }

    override fun jumpSearch() {
        nav.findNavController().navigate(R.id.action_categoryFragment_to_searchFragment)
    }
}