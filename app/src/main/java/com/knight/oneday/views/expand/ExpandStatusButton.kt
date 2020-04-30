package com.knight.oneday.views.expand

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton

/**
 * Create by FLJ in 2020/4/30 14:06
 * 根据张开收起状态同步的选装按钮 直接成为其状态切换者 从而达到效果
 */
class ExpandStatusButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageButton(context, attrs, defStyleAttr), ExpandableStatusListener {

    override fun onPreExpand() {

    }

    override fun onExpanded() {
    }

    override fun onPreCollapse() {

    }

    override fun onCollapsed() {
    }

    override fun onFraction(fraction: Float, isExpanding: Boolean) {
        rotation = if (isExpanding) 180F * fraction else 180f * (1 - fraction)
    }
}