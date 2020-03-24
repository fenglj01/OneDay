package com.knight.oneday.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.view.View
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.R
import com.knight.oneday.utilities.dp2px
import com.knight.oneday.utilities.sp

/**
 * Create by FLJ in 2020/3/12 10:40
 * 分段分节分割线，支持滑动吸顶效果
 * 第一步 每个上面都画一个title
 * 第二步 相同的第一个title就不画了
 * 第三步 吸顶效果
 * https://www.jianshu.com/p/41ae13016243
 */
class SectionDecoration(
    private val context: Context,
    private val sectionCallback: SectionCallback
) :
    RecyclerView.ItemDecoration() {

    private val sectionPaint: TextPaint
    private val decorationPaint: Paint
    private val sectionHeight: Int
    private val sectionResources: Resources
    private val sectionTextRect: Rect
    private val sectionTextHeight: Float
    private val sectionTextPadding: Float

    init {
        sectionResources = context.resources
        sectionPaint = TextPaint().apply {
            isAntiAlias = true
            textSize = 16F.sp
            color = sectionResources.getColor(R.color.one_day_white)
            textAlign = Paint.Align.LEFT
        }
        decorationPaint = Paint().apply {
            isAntiAlias = true
            color = sectionResources.getColor(R.color.one_day_black_900_alpha_020)
        }
        sectionHeight = sectionResources.getDimensionPixelSize(R.dimen.dp_32)
        sectionTextRect = Rect()
        sectionPaint.getTextBounds("1", 0, 1, sectionTextRect)
        sectionTextHeight = sectionTextRect.height().toFloat()
        sectionTextPadding = dp2px(context, 16F)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val sectionContent = sectionCallback.getSectionContent(position)
        if (sectionContent.isNullOrEmpty()) return
        outRect.top =
            if (position == 0 || isFirstSectionInGroup(position)) sectionHeight else 1
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        // getChildAt 当前可见得
        for (index in 0 until parent.childCount) {
            val view = /*parent[index]*/ parent.getChildAt(index)
            val position = parent.getChildAdapterPosition(view)
            val sectionBottom = view.top
            val sectionLeft = view.left
            val sectionRight = view.right
            if (isFirstSectionInGroup(position)) {  // 绘制分割组
                val sectionTop = sectionBottom - sectionHeight
                c.drawRect(
                    sectionLeft.toFloat(),
                    sectionTop.toFloat(),
                    sectionRight.toFloat(),
                    sectionBottom.toFloat(),
                    decorationPaint
                )
                c.drawText(
                    sectionCallback.getSectionContent(position),
                    parent.paddingLeft.toFloat() + sectionTextPadding,
                    view.top - (sectionHeight / 2 - sectionTextHeight / 2),
                    sectionPaint
                )
            } else {    // 绘制分割线
                val lineTop = sectionBottom - 1
                c.drawLine(
                    sectionLeft.toFloat(),
                    lineTop.toFloat(),
                    sectionRight.toFloat(),
                    lineTop.toFloat(),
                    decorationPaint
                )
            }

        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (parent.isEmpty()) return
        // childCount 是屏幕能展示数目的个数
        val firstVisibleView = parent[0]
        val firstVisiblePosition = parent.getChildAdapterPosition(firstVisibleView)
        val firstSectionContent = sectionCallback.getSectionContent(firstVisiblePosition)
        val left = parent.paddingLeft
        val right = firstVisibleView.width - parent.paddingRight
        if (firstVisibleView.bottom <= sectionHeight && isFirstSectionInGroup(firstVisiblePosition + 1)) {
            c.drawRect(
                left.toFloat(),
                0F,
                right.toFloat(),
                firstVisibleView.bottom.toFloat(),
                decorationPaint
            )
            c.drawText(
                firstSectionContent,
                left.toFloat() + sectionTextPadding,
                firstVisibleView.top - (sectionHeight / 2 - sectionTextHeight / 2),
                sectionPaint
            )
        } else {
            c.drawRect(
                left.toFloat(),
                0F,
                right.toFloat(),
                sectionHeight.toFloat(),
                decorationPaint
            )
            c.drawText(
                firstSectionContent,
                left.toFloat() + sectionTextPadding,
                (sectionHeight / 2 + sectionTextHeight / 2),
                sectionPaint
            )
        }

    }

    private fun isFirstSectionInGroup(dataPosition: Int): Boolean {
        return if (dataPosition == 0) true else sectionCallback.getSectionContent(dataPosition) != sectionCallback.getSectionContent(
            dataPosition - 1
        )
    }

    interface SectionCallback {
        fun getSectionContent(dataPosition: Int): String
    }

}

/* 分段标题 */
data class SectionBean(val content: String)

