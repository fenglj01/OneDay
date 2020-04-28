package com.knight.oneday.views.expand

/**
 * Create by FLJ in 2020/4/28 10:39
 * 可展开接口封装
 */
interface Expandable {

    fun isExpanded(): Boolean

    fun expand(withAnimator: Boolean)

    fun collapse(withAnimator: Boolean)

    fun toggle(withAnimator: Boolean)

    fun addExpandableStatusListener(expandableStatusListener: ExpandableStatusListener)

}