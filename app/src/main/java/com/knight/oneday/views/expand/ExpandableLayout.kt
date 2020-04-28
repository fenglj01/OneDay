package com.knight.oneday.views.expand

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IntDef
import com.knight.oneday.R
import kotlin.math.roundToInt

/**
 * Create by FLJ in 2020/4/28 13:49
 * 仅仅是作为下面可展开部分
 */
class ExpandableLayout : FrameLayout, Expandable {

    private var duration: Long = 1000L
    // 展开进度
    private var expansionFraction: Float = 0F
    @ExpandState
    private var state: Int = EXPANDED
    private var parallax: Float = 0F

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(EXPANDED, EXPANDING, COLLAPSED, COLLAPSING)
    annotation class ExpandState

    companion object {
        const val EXPANDED = 0
        const val EXPANDING = 1
        const val COLLAPSED = 3
        const val COLLAPSING = 4
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
        // 保证时差在0 -1 之间
        parallax = 1f.coerceAtMost(
            0f.coerceAtLeast(
                typeArray.getInt(
                    R.styleable.ExpandableLayout_el_parallax,
                    0
                ).toFloat()
            )
        )
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
            for (index in 0..childCount) {
                val child = getChildAt(index)
                child.translationY = -parallaxDelta
            }
        }
        setMeasuredDimension(measuredWidth, measuredHeight - expansionDelta)
    }

    override fun isExpanded(): Boolean {
        return state == EXPANDED
    }

    override fun expand(withAnimator: Boolean) {
        setExpansionFraction(expansion = 1F)
    }

    override fun collapse(withAnimator: Boolean) {
        setExpansionFraction(0F)
    }

    override fun toggle(withAnimator: Boolean) {
        if (isExpanded()) collapse(withAnimator) else expand(withAnimator)
    }

    override fun addExpandableStatusListener(expandableStatusListener: ExpandableStatusListener) {
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

    private fun changeVisibility() {
        visibility = if (state == COLLAPSED) View.GONE else View.VISIBLE
    }
}