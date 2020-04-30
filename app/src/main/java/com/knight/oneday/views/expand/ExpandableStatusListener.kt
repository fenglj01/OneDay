package com.knight.oneday.views.expand

import android.animation.Animator
import android.animation.ObjectAnimator

/**
 * Create by FLJ in 2020/4/28 10:27
 * 展开View的相关监听
 */
interface ExpandableStatusListener {

    fun onPreExpand()

    fun onExpanded()

    fun onPreCollapse()

    fun onCollapsed()

    fun onFraction(fraction: Float, isExpanding: Boolean)

}

/**
 * Create by FLJ in 2020/4/28 10:35
 * 适配 方便只需要监听某一部份的时候
 */
open class ExpandableStatusListenerAdapter(
    private val onPreExpand: (() -> Unit)? = null,
    private val onExpanded: (() -> Unit)? = null,
    private val onFraction: ((Float, Boolean) -> Unit)? = null,
    private val onPreCollapse: (() -> Unit)? = null,
    private val onCollapsed: (() -> Unit)? = null
) : ExpandableStatusListener {

    override fun onPreExpand() {
        onPreExpand?.invoke()
    }

    override fun onExpanded() {
        onExpanded?.invoke()
    }

    override fun onPreCollapse() {
        onPreCollapse?.invoke()
    }

    override fun onCollapsed() {
        onCollapsed?.invoke()
    }

    override fun onFraction(fraction: Float, isExpanding: Boolean) {
        onFraction?.invoke(fraction, isExpanding)
    }
}