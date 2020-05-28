package com.knight.oneday.views.hsv

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView

/**
 * Create by FLJ in 2020/5/26 16:07
 * 水平方向可滑动的步骤View
 */
class HorizontalStepView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {

    /* 这里将attrs 传递下去 */
    private val stepIndicator =
        StepIndicator(context, attrs)

    init {
        addView(stepIndicator)
    }

}
