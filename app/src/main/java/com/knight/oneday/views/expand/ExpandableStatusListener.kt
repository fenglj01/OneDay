package com.knight.oneday.views.expand

import android.animation.Animator
import android.animation.ObjectAnimator

/**
 * Create by FLJ in 2020/4/28 10:27
 * 展开View的相关监听
 */
interface ExpandableStatusListener {

    fun onAnimatorStart()

    fun onAnimatorEnd()

    fun onAnimatorUpdate(animator: Animator)

    fun onPreExpand()

    fun onExpanded()

    fun onPreCollapse()

    fun onCollapsed()

}

/**
 * Create by FLJ in 2020/4/28 10:35
 * 适配 方便只需要监听某一部份的时候
 */
class ExpandableStatusListenerAdapter(
    private val onAnimatorStart: (() -> Unit)? = null,
    private val onAnimatorEnd: (() -> Unit)? = null,
    private val onAnimatorUpdate: ((Animator) -> Unit)? = null,
    private val onPreExpand: (() -> Unit)? = null,
    private val onExpanded: (() -> Unit)? = null,
    private val onPreCollapse: (() -> Unit)? = null,
    private val onCollapsed: (() -> Unit)? = null
) : ExpandableStatusListener {
    override fun onAnimatorStart() {
        onAnimatorStart?.invoke()
    }

    override fun onAnimatorEnd() {
        onAnimatorEnd?.invoke()
    }

    override fun onAnimatorUpdate(animator: Animator) {
        onAnimatorUpdate?.invoke(animator)
    }

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
}