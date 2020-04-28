package com.knight.oneday.views.expand

import android.animation.Animator

/**
 * Create by FLJ in 2020/4/28 10:39
 * 可展开接口封装
 */
interface Expandable {

    fun isExpanded(): Boolean

    fun expand()

    fun collapse()

    fun toggle()

    fun addExpandableStatusListener(expandableStatusListener: ExpandableStatusListener)

    fun dispatchStatus(status: Int)

}