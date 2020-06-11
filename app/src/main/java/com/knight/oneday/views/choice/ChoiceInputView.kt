package com.knight.oneday.views.choice

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import com.knight.oneday.R
import com.knight.oneday.utilities.singleClick

class ChoiceInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val contentView: View
    private val iconIv: AppCompatImageButton
    private val titleTv: AppCompatTextView
    private val contentTv: AppCompatTextView
    private val gpClick: Group
    var onChoiceInputClicked: OnChoiceInputClicked? = null

    init {
        contentView = LayoutInflater.from(context).inflate(R.layout.view_choice_input, this, true)
        iconIv = contentView.findViewById(R.id.choice_input_icon)
        titleTv = contentView.findViewById(R.id.choice_input_title)
        contentTv = contentView.findViewById(R.id.choice_input_content)
        /* 点击事件缩小了一下范围 增加体验 */
        gpClick = contentView.findViewById(R.id.gp_click)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ChoiceInputView)
        with(ta) {
            val iconRes = ta.getResourceId(R.styleable.ChoiceInputView_civIcon, 0)
            iconIv.setImageResource(iconRes)
            val title = ta.getString(R.styleable.ChoiceInputView_civTitle)
            titleTv.text = title
            recycle()
        }
        initEvent()
    }

    private fun initEvent() {
        gpClick.singleClick {
            onChoiceInputClicked?.onChoiceInputClicked()
        }
    }


    interface OnChoiceInputClicked {
        fun onChoiceInputClicked()
    }

}