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
    private val context: Context
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
        // 设置顶部偏移
        outRect.set(0, sectionHeight, 0, 0)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        for (index in 0 until parent.childCount) {
            val view = /*parent[index]*/ parent.getChildAt(index)
            val left = view.left.toFloat()
            val right = view.right.toFloat()
            val bottom = view.top.toFloat()
            val top = 0F
            c.drawRect(left, top, right, bottom, decorationPaint)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val textRect = Rect()

        for (index in 0 until parent.childCount) {
            val view = parent[index]
            val position = parent.getChildAdapterPosition(view)
            val left = view.left.toFloat()
            val right = view.right.toFloat()
            val bottom = view.top.toFloat()
            val top = 0F

            c.drawText(
                sectionList[position].content,
                parent.paddingLeft.toFloat(),
                view.top - (sectionHeight / 2 - sectionTextHeight / 2),
                sectionPaint
            )
        }
    }


    /* fun addSection(sectionBean: SectionBean) {
         sectionList.add(sectionBean)
     }*/


}

/* 分段标题 */
data class SectionBean(val content: String)
