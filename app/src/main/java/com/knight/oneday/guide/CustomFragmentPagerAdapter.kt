package com.knight.oneday.guide

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

@SuppressLint("WrongConstant")
class CustomFragmentPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        val showIndex = (position % titleArray.count())
        return GuideFragment.newInstance(
            backgroundColors[showIndex],
            titleArray[showIndex],
            contentArray[showIndex],
            resourceArray[showIndex]
        )
    }

    override fun getCount(): Int {
        return titleArray.count()
    }
}