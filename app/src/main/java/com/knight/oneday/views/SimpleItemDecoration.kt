package com.knight.oneday.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt


/**
 * Author:      HW
 * Date:        2019/9/25 16:07
 * Description: 简单的RecyclerView分割线，只支持颜色
 */
class SimpleItemDecoration(
    context: Context,
    private val orientation: Int,
    private val dividerHeight: Int,
    @ColorInt private val dividerColor: Int
) : RecyclerView.ItemDecoration() {

    private val ATTRS = intArrayOf(android.R.attr.listDivider)

    private var paint: Paint? = null

    init {
        require(!(RecyclerView.VERTICAL != orientation && RecyclerView.HORIZONTAL != orientation))
        val a = context.obtainStyledAttributes(ATTRS)
        a.recycle()
        this.paint = Paint().apply {
            color = dividerColor
            style = Paint.Style.FILL
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0, 0, 0, this.dividerHeight)
    }

    override fun onDraw(
        c: Canvas, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.onDraw(c, parent, state)
        if (this.orientation == RecyclerView.VERTICAL) {
            this.drawVertical(c, parent)
        } else {
            this.drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int
        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right, parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }

        val childSize = parent.childCount
        for (i in 0 until childSize) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val top = (child.bottom
                    + layoutParams.bottomMargin
                    + ViewCompat.getTranslationY(child).roundToInt())
            val bottom = top + this.dividerHeight
            if (this.paint != null) {
                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), this.paint!!)
            }
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int
        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(
                parent.paddingLeft, top, parent.width - parent.paddingRight, bottom
            )
        } else {
            top = 0
            bottom = parent.height
        }

        val childSize = parent.childCount

        for (i in 0 until childSize) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val left = (child.right + layoutParams.rightMargin + ViewCompat.getTranslationX(child).roundToInt())
            val right = left + this.dividerHeight
            if (this.paint != null) {
                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), this.paint!!)
            }
        }
        canvas.restore()
    }
}
