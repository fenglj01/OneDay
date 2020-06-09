package com.knight.oneday.views.hsv

import android.content.Context
import android.util.AttributeSet
import android.util.Log
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

    var onStepIndicatorClickListener: OnStepIndicatorClickListener? = null
        set(value) {
            field = value
            stepIndicator.onStepIndicatorClickListener = field
        }

    init {
        addView(stepIndicator)
    }

    fun setUpStepIndicator(
        stepCount: Int,
        currentCount: Int,
        finishedCount: MutableList<Int>
    ) {
        stepIndicator.stepCount = stepCount
        stepIndicator.currentCount = currentCount
        stepIndicator.finishedCount = finishedCount
    }

    fun setSelectedIndex(selectedIndex: Int) {
        stepIndicator.selectedIndex = selectedIndex + 1
    }

    fun scrollToStep(stepCount: Int) {
        scrollTo(stepIndicator.getXPositionByStep(stepCount), scrollY)

    }

    fun smoothScrollToStep(stepCount: Int) {
        smoothScrollTo(stepIndicator.getXPositionByStep(stepCount), scrollY)
    }

    fun selectStepIndex(): Int {
        return stepIndicator.selectedIndex - 1
    }

}

