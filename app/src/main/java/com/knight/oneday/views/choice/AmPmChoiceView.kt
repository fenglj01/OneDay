package com.knight.oneday.views.choice

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * Create by FLJ in 2020/5/13 16:02
 * 上午下午选择器
 */
class AmPmChoiceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        initAttr()
    }

    private fun initAttr() {

    }

    interface OnChoiceFinish {
        fun onChoiceFinish(isAm: Boolean)
    }

}