package com.knight.oneday.views.choice

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.knight.oneday.R

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
        LayoutInflater.from(context).inflate(R.layout.am_pm_choice_view, this, true)
    }

    interface OnChoiceFinish {
        fun onChoiceFinish(isAm: Boolean)
    }

}