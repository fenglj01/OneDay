package com.knight.oneday.views.choice

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.knight.oneday.R
import com.knight.oneday.utilities.px2sp
import com.knight.oneday.utilities.singleClick

/**
 * Create by FLJ in 2020/5/13 16:02
 * 上午下午选择器
 */
class AmPmChoiceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var contentView: View
    private lateinit var am: AppCompatTextView
    private lateinit var pm: AppCompatTextView
    private lateinit var choiceAnimator: ValueAnimator
    private lateinit var unChoiceAnimator: ValueAnimator
    private var choiceColor: Int = 0
    private var unChoiceColor: Int = 0
    private var choiceTextSize: Float = 0F
    private var unChoiceTextSize: Float = 0F
    private var textSizeGap: Float = 0F
    private var isChoiceAm = true

    init {
        initAttr()
        initAnimator()
        initEvent()
    }

    private fun initAttr() {
        contentView = LayoutInflater.from(context).inflate(R.layout.am_pm_choice_view, this, true)
        am = contentView.findViewById(R.id.am_tv)
        pm = contentView.findViewById(R.id.pm_tv)
        choiceColor = am.currentTextColor
        unChoiceColor = pm.currentTextColor
        choiceTextSize = px2sp(px = am.textSize)
        unChoiceTextSize = px2sp(px = pm.textSize)
        textSizeGap = choiceTextSize - unChoiceTextSize
    }

    private fun initEvent() {
        am.singleClick(200L) {
            if (isChoiceAm) return@singleClick
            isChoiceAm = true
            pm.setTextColor(unChoiceColor)
            choiceAnimator.start()
            unChoiceAnimator.start()
        }
        pm.singleClick(200L) {
            if (!isChoiceAm) return@singleClick
            isChoiceAm = false
            choiceAnimator.start()
            unChoiceAnimator.start()
        }
    }

    private fun initAnimator() {
        choiceAnimator = ValueAnimator.ofInt(unChoiceColor, choiceColor).apply {
            duration = 200L
            setEvaluator(ArgbEvaluator())
            addUpdateListener { animator ->
                val value = animator.animatedValue as Int
                if (isChoiceAm) {
                    am.setTextColor(value)
                    am.textSize = unChoiceTextSize + textSizeGap * animator.animatedFraction
                    pm.textSize = choiceTextSize - textSizeGap * animator.animatedFraction
                } else {
                    pm.setTextColor(value)
                    pm.textSize = unChoiceTextSize + textSizeGap * animator.animatedFraction
                    am.textSize = choiceTextSize - textSizeGap * animator.animatedFraction
                }
            }
        }
        unChoiceAnimator = ValueAnimator.ofInt(choiceColor, unChoiceColor).apply {
            duration = 200L
            setEvaluator(ArgbEvaluator())
            addUpdateListener { animator ->
                val value = animator.animatedValue as Int
                if (isChoiceAm) {
                    pm.setTextColor(value)
                } else {
                    am.setTextColor(value)
                }
            }
        }
    }

    interface OnChoiceFinish {
        fun onChoiceFinish(isAm: Boolean)
    }

}