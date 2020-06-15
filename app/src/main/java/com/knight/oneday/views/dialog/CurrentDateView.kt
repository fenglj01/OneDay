package com.knight.oneday.views.dialog

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.knight.oneday.R
import com.knight.oneday.utilities.currentDayOfMonth
import com.knight.oneday.utilities.singleClick

/**
 * Create by FLJ in 2020/6/15 10:06
 * 当前日期
 */
class CurrentDateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val contentView: View
    var onClickListener: (() -> Unit)? = null

    init {
        contentView =
            LayoutInflater.from(context).inflate(R.layout.view_current_date_layout, this, true)
        contentView.findViewById<TextView>(R.id.current_date_tv).text =
            currentDayOfMonth().toString()
        contentView.singleClick {
            onClickListener?.invoke()
        }
    }
}