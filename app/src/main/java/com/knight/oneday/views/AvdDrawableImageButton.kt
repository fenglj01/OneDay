package com.knight.oneday.views

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.widget.addTextChangedListener
import com.knight.oneday.utilities.singleClick

/**
 * Create by FLJ in 2020/4/14 9:35
 * 动画icon
 */
class AvdDrawableImageButton : AppCompatImageButton {

    var onButtonClickListener: OnButtonClickListener? = null

    var nowState: State = false
        set(value) {
            field = value
            setImageState(
                if (nowState) intArrayOf(android.R.attr.state_activated)
                else intArrayOf(-android.R.attr.state_activated), true
            )
        }

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initListener()
    }


    fun setTriggerConditionByEditText(editText: EditText, condition: ((Int) -> Boolean)) {
        editText.addTextChangedListener { editable ->
            editable?.run {
                nowState = condition.invoke(length)

            }
        }
    }

    private fun initListener() {
        singleClick {
            onButtonClickListener?.run {
                if (nowState) onStateOnReserveClick() else onStateOnForwardClick()
            }
        }
    }

    fun revert() {
        nowState = !nowState
    }


}

typealias State = Boolean

interface OnButtonClickListener {
    /* 当按钮处于正向阶段时被点击 */
    fun onStateOnForwardClick()

    /* 当按钮处于逆向阶段时被点击 */
    fun onStateOnReserveClick()
}
