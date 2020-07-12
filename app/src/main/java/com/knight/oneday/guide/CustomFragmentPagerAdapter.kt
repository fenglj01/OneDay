package com.knight.oneday.guide

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.knight.oneday.R
import com.knight.oneday.utilities.themeColor

@SuppressLint("WrongConstant")
class CustomFragmentPagerAdapter(
    fm: FragmentManager,
    val surfaceColor: Int,
    val secondaryColor: Int
) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        val showIndex = (position % titleArray.count())
        val color =
            if (showIndex % 2 != 0) surfaceColor else secondaryColor
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