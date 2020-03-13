package com.knight.oneday.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.R
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
    private val sectionList: MutableList<SectionBean>,
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

    init {
        sectionResources = context.resources
        sectionPaint = TextPaint().apply {
            isAntiAlias = true
            textSize = 16F.sp
            color = sectionResources.getColor(R.color.colorWhite)
            textAlign = Paint.Align.LEFT
        }
        decorationPaint = Paint().apply {
            isAntiAlias = true
            color = sectionResources.getColor(R.color.color43434E)
        }
        sectionHeight = sectionResources.getDimensionPixelSize(R.dimen.dp_32)
        sectionTextRect = Rect()
        sectionPaint.getTextBounds("1", 0, 1, sectionTextRect)
        sectionTextHeight = sectionTextRect.height().toFloat()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        for (index in 0 until parent.childCount) {
            val position = parent.getChildAdapterPosition(view)
            if (sectionCallback.itemNeedSection(position)) {
                // 预留出分段标签的位置
                outRect.set(0, sectionHeight, 0, 1)
            } else {
                // 预留出分割线的位置
                outRect.set(0, 0, 0, 1)
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        // getChildAt 当前可见得
        for (index in 0 until parent.childCount) {

            val view = /*parent[index]*/ parent.getChildAt(index)
            val position = parent.getChildAdapterPosition(view)
            if (sectionCallback.itemNeedSection(position)) {
                val left = view.left.toFloat()
                val right = view.right.toFloat()
                val bottom = view.top.toFloat()
                val top = 0F
                // 画出分段标签的背景 并且画出分割线
                c.drawRect(left, top, right, bottom, decorationPaint)
                c.drawLine(
                    0F, view.bottom.toFloat(),
                    view.right.toFloat(), (view.bottom + 1).toFloat(), decorationPaint
                )
            } else {
                // 不需要分段标签的情况下 只需要画出分割线
                c.drawLine(
                    0F, view.bottom.toFloat(),
                    view.right.toFloat(), (view.bottom + 1).toFloat(), decorationPaint
                )
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val textRect = Rect()
        for (index in 0 until parent.childCount) {
            val view = parent[index]
            val position = parent.getChildAdapterPosition(view)
            if (sectionCallback.itemNeedSection(position)) {
                val left = view.left.toFloat()
                val right = view.right.toFloat()
                val bottom = view.top.toFloat()
                val top = 0F
                // 绘制标题分段
                c.drawText(
                    sectionList[position].content,
                    parent.paddingLeft.toFloat(),
                    view.top - (sectionHeight / 2 - sectionTextHeight / 2),
                    sectionPaint
                )
            } else {

            }

        }
    }


    /* fun addSection(sectionBean: SectionBean) {
         sectionList.add(sectionBean)
     }*/


}

/* 分段标题 */
data class SectionBean(val content: String)

interface SectionCallback {
    fun itemNeedSection(dataPosition: Int): Boolean
}