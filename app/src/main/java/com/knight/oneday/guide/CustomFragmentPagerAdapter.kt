package com.knight.oneday.guide

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

@SuppressLint("WrongConstant")
class CustomFragmentPagerAdapter(
    fm: FragmentManager
) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        val showIndex = (position % titleArray.count())
        val color =
            if (showIndex % 2 == 0) Color.parseColor("#ffffff") else Color.parseColor("#1EB980")
        return GuideFragment.newInstance(
            color,
            titleArray[showIndex],
            contentArray[showIndex],
            resourceArray[showIndex]
        )
    }

    override fun getCount(): Int {
        return titleArray.count()
    }
}