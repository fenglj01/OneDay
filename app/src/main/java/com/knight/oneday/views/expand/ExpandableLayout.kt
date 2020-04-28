package com.knight.oneday.views.expand

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener
import com.knight.oneday.R
import java.lang.RuntimeException
import kotlin.math.roundToInt

/**
 * Create by FLJ in 2020/4/28 13:49
 * 仅仅是作为下面可展开部分
 */
class ExpandableLayout : ConstraintLayout, Expandable {

    private var animDuration: Long = 1000L
    // 展开进度
    private var expansionFraction: Float = 0F
    @ExpandState
    private var state: Int = EXPANDED
    private var parallax: Float = 0F
    private var withAnimator: Boolean = false

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(EXPANDED, EXPANDING, COLLAPSED, COLLAPSING)
    annotation class ExpandState

    private var listeners: MutableList<ExpandableStatusListener> = mutableListOf()

    private lateinit var animator: ValueAnimator

    companion object {
        /* View状态 */
        const val EXPANDED = 0
        const val EXPANDING = 1
        const val COLLAPSED = 3
        const val COLLAPSING = 4
        /* 分发类型 */
        const val DISPATCH_EXPANDED = 0
        const val DISPATCH_PRE_EXPAND = 1
        const val DISPATCH_COLLAPSED = 2
        const val DISPATCH_PRE_COLLAPSE = 3
        const val DISPATCH_ANIM_START = 4
        const val DISPATCH_ANIM_END = 5
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, def: Int) : super(
        context,
        attributeSet,
        def
    ) {
        initAttrs(attributeSet)
    }

    private fun initAttrs(attr: AttributeSet?) {
        val typeArray = context!!.obtainStyledAttributes(
            attr,
            R.styleable.ExpandableLayout
        )
        state = if (typeArray.getBoolean(
                R.styleable.ExpandableLayout_el_expanded,
                false
            )
        ) EXPANDED else COLLAPSED
        // 保证视差在0 -1 之间
        parallax = 1f.coerceAtMost(
            0f.coerceAtLeast(
                typeArray.getInt(
                    R.styleable.ExpandableLayout_el_parallax,
                    0
                ).toFloat()
            )
        )
        withAnimator = typeArray.getBoolean(R.styleable.ExpandableLayout_el_withAnimator, false)
        expansionFraction = if (state == EXPANDED) 1F else 0F
        typeArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        visibility = if (expansionFraction == 0F && measuredHeight == 0) View.GONE else View.VISIBLE
        // 计算展开的高度
        val expansionDelta = measuredHeight - (measuredHeight * expansionFraction).roundToInt()
        // 计算子控件的移动位置 保证能够随着展开而相对平滑的出现
        if (parallax > 0) {
            val parallaxDelta = expansionDelta * parallax
            for (index in 0 until childCount) {
                val child = getChildAt(index)
                child.translationY = -parallaxDelta
            }
        }
        setMeasuredDimension(measuredWidth, measuredHeight - expansionDelta)
    }

    override fun isExpanded(): Boolean {
        return state == EXPANDED
    }

    override fun expand() {
        dispatchStatus(DISPATCH_PRE_EXPAND)
        if (withAnimator) {
            setExpansionFractionByAnimate(true)
        } else {
            setExpansionFraction(expansion = 1F)
        }
    }

    override fun collapse() {
        dispatchStatus(DISPATCH_PRE_COLLAPSE)
        if (withAnimator) {
            setExpansionFractionByAnimate(false)
        } else {
            setExpansionFraction(expansion = 0F)
        }
    }

    override fun toggle() {
        if (isExpanded()) collapse() else expand()
    }

    override fun addExpandableStatusListener(expandableStatusListener: ExpandableStatusListener) {
        listeners.add(expandableStatusListener)
    }

    private fun setExpansionFractionByAnimate(isExpand: Boolean) {
        val targetExpansionFraction = if (isExpand) 1F else 0F
        animateHeight(targetExpansionFraction)
    }

    private fun animateHeight(targetExpansion: Float) {
        if (!::animator.isInitialized) {
            initAnimator(targetExpansion)
        }
        animator.setFloatValues(expansionFraction, targetExpansion)
        animator.start()
    }

    private fun initAnimator(targetExpansion: Float) {
        animator = ValueAnimator.ofFloat(expansionFraction, targetExpansion).apply {
            duration = animDuration
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener {
                setExpansionFraction(it.animatedValue as Float)
            }
            this.addListener(
                onStart = {
                    dispatchStatus(DISPATCH_ANIM_START)
                },
                onEnd = {
                    dispatchStatus(DISPATCH_ANIM_END)
                }
            )
        }
    }


    private fun setExpansionFraction(expansion: Float) {
        if (expansionFraction == expansion) return
        val delta = expansion - expansionFraction
        state = when {
            expansion == 0F -> COLLAPSED
            expansion == 1F -> EXPANDED
            delta > 0 -> EXPANDING
            else -> COLLAPSING
        }
        visibility = if (state == COLLAPSED) View.GONE else View.VISIBLE
        expansionFraction = expansion
        requestLayout()
    }

    override fun dispatchStatus(status: Int) {
        listeners.forEach { listener ->
            when (status) {
                DISPATCH_COLLAPSED -> listener.onCollapsed()
                DISPATCH_PRE_COLLAPSE -> listener.onPreCollapse()
                DISPATCH_PRE_EXPAND -> listener.onPreExpand()
                DISPATCH_EXPANDED -> listener.onExpanded()
                DISPATCH_ANIM_START -> listener.onAnimatorStart()
                DISPATCH_ANIM_END -> listener.onAnimatorEnd()
                else -> throw  RuntimeException("un know dispatch status: $status")
            }
        }
    }
}