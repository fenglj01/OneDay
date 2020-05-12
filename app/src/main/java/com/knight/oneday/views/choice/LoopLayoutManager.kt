package com.knight.oneday.views.choice

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by FLJ in 2020/5/12 13:55
 * 无限循环的LayoutManager
 */
class LoopLayoutManager(
    private val spanCount: Int,
    private val orientation: Int,
    private val offset: Int = 0
) : RecyclerView.LayoutManager() {
    private val viewsToRecycle = mutableListOf<View>()
    private val overallOffset = offset * (spanCount - 1)
    private var firstStart = true

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        if (firstStart) {
            detachAndScrapAttachedViews(recycler)
            if (orientation == RecyclerView.HORIZONTAL) {
                fillRight(recycler, state, 0, 0)
            } else {
                fillBottom(recycler, state, 0, 0)
            }
            firstStart = false
        }
    }

    override fun canScrollHorizontally(): Boolean = orientation == RecyclerView.HORIZONTAL

    override fun canScrollVertically(): Boolean = orientation == RecyclerView.VERTICAL

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        offsetChildrenHorizontal(-dx)
        if (dx > 0) {
            findBottomRightChild()
                ?.takeIf {
                    getDecoratedRightWithMargins(it) - overallOffset < width
                }
                ?.let {
                    val fromPosition = getPosition(it).let { position ->
                        (position + 1) % itemCount
                    }
                    fillRight(recycler, state, fromPosition, getDecoratedRightWithMargins(it))
                }
        } else if (dx < 0) {
            findTopLeftChild()
                ?.takeIf {
                    getDecoratedLeftWithMargins(it) + overallOffset > 0
                }
                ?.let {
                    val fromPosition = (getPosition(it) - 1).let { position ->
                        if (position < 0) {
                            state.itemCount - 1
                        } else {
                            position
                        }
                    }
                    fillLeft(
                        recycler,
                        state,
                        fromPosition,
                        getDecoratedLeftWithMargins(it),
                        (spanCount - 1) * getHeightWithMarginsAndDecorations(it)
                    )
                }
        }
        recycleViewsHorizontally(recycler)
        return dx
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        offsetChildrenVertical(-dy)
        if (dy > 0) {
            findBottomRightChild()
                ?.takeIf {
                    getDecoratedBottomWithMargins(it) - overallOffset < height
                }
                ?.let {
                    val fromPosition = getPosition(it).let { position ->
                        (position + 1) % itemCount
                    }
                    fillBottom(recycler, state, fromPosition, getDecoratedBottomWithMargins(it))
                }
        } else if (dy < 0) {
            findTopLeftChild()
                ?.takeIf {
                    getDecoratedTopWithMargins(it) + overallOffset > 0
                }
                ?.let {
                    val fromPosition = (getPosition(it) - 1).let { position ->
                        if (position < 0) {
                            state.itemCount - 1
                        } else {
                            position
                        }
                    }
                    fillTop(
                        recycler,
                        state,
                        fromPosition,
                        (spanCount - 1) * getWidthWithMarginsAndDecorations(it),
                        getDecoratedTopWithMargins(it)
                    )
                }
        }
        recycleViewsVertically(recycler)
        return dy
    }

    private fun fillRight(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        startPosition: Int,
        startX: Int
    ) {
        var currentX = startX - overallOffset
        var currentY = 0
        var counter = startPosition
        while (currentX < width) {
            val view = recycler.getViewForPosition(counter % state.itemCount)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val viewWidth = getWidthWithMarginsAndDecorations(view)
            val viewHeight = getHeightWithMarginsAndDecorations(view)
            layoutDecoratedWithMargins(
                view,
                currentX,
                currentY,
                currentX + viewWidth,
                currentY + viewHeight
            )
            if (currentY == (spanCount - 1) * viewHeight) {
                currentY = 0
                currentX += viewWidth
                currentX -= overallOffset
            } else {
                currentY += viewHeight
                currentX += offset
            }
            counter++
        }
    }

    private fun fillLeft(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        startPosition: Int,
        startX: Int,
        startY: Int
    ) {
        var currentX = startX + overallOffset
        var currentY = startY
        var counter = startPosition
        while (currentX > 0) {
            val view = recycler.getViewForPosition(counter)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val viewWidth = getWidthWithMarginsAndDecorations(view)
            val viewHeight = getHeightWithMarginsAndDecorations(view)
            layoutDecoratedWithMargins(
                view,
                currentX - viewWidth,
                currentY,
                currentX,
                currentY + viewHeight
            )
            if (currentY == 0) {
                currentY = startY
                currentX -= viewWidth
                currentX += overallOffset
            } else {
                currentY -= viewHeight
                currentX -= offset
            }
            counter--
            if (counter < 0) {
                counter = state.itemCount - 1
            }
        }
    }

    private fun fillBottom(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        startPosition: Int,
        startY: Int
    ) {
        var currentX = 0
        var currentY = startY - overallOffset
        var counter = startPosition
        while (currentY < height) {
            val view = recycler.getViewForPosition(counter % state.itemCount)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val viewWidth = getWidthWithMarginsAndDecorations(view)
            val viewHeight = getHeightWithMarginsAndDecorations(view)
            layoutDecoratedWithMargins(
                view,
                currentX,
                currentY,
                currentX + viewWidth,
                currentY + viewHeight
            )
            if (currentX == (spanCount - 1) * viewWidth) {
                currentX = 0
                currentY += viewHeight
                currentY -= overallOffset
            } else {
                currentX += viewWidth
                currentY += offset
            }
            counter++
        }
    }

    private fun fillTop(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        startPosition: Int,
        startX: Int,
        startY: Int
    ) {
        var currentX = startX
        var currentY = startY + overallOffset
        var counter = startPosition
        while (currentY > 0) {
            val view = recycler.getViewForPosition(counter)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val viewWidth = getWidthWithMarginsAndDecorations(view)
            val viewHeight = getHeightWithMarginsAndDecorations(view)
            layoutDecoratedWithMargins(
                view,
                currentX,
                currentY - viewHeight,
                currentX + viewWidth,
                currentY
            )
            if (currentX == 0) {
                currentY -= viewHeight
                currentY += overallOffset
                currentX = (spanCount - 1) * viewWidth
            } else {
                currentX -= viewWidth
                currentY -= offset
            }
            counter--
            if (counter < 0) {
                counter = state.itemCount - 1
            }
        }
    }

    private fun recycleViewsHorizontally(recycler: RecyclerView.Recycler) {
        val screenWidth = width
        for (i in 0 until childCount) {
            getChildAt(i)?.let { view ->
                val right = getDecoratedRight(view)
                val left = getDecoratedLeft(view)
                if (right < 0 || left > screenWidth) {
                    viewsToRecycle.add(view)
                }
            }
        }
        for (view in viewsToRecycle) {
            detachAndScrapView(view, recycler)
        }
        viewsToRecycle.clear()
    }

    private fun recycleViewsVertically(recycler: RecyclerView.Recycler) {
        val screenHeight = height
        for (i in 0 until childCount) {
            getChildAt(i)?.let { view ->
                val bottom = getDecoratedBottom(view)
                val top = getDecoratedTop(view)
                if (bottom < 0 || top > screenHeight) {
                    viewsToRecycle.add(view)
                }
            }
        }
        for (view in viewsToRecycle) {
            detachAndScrapView(view, recycler)
        }
        viewsToRecycle.clear()
    }

    private fun findTopLeftChild(): View? {
        var minTopLeftSum = width + height
        var result: View? = null
        for (i in 0 until childCount) {
            getChildAt(i)?.let { view ->
                val topLeft = getDecoratedTop(view) + getDecoratedLeft(view)
                if (topLeft < minTopLeftSum) {
                    minTopLeftSum = topLeft
                    result = view
                }
            }
        }
        return result
    }

    private fun findBottomRightChild(): View? {
        var maxBottomRight = 0
        var result: View? = null
        for (i in 0 until childCount) {
            getChildAt(i)?.let { view ->
                val bottomRight = getDecoratedRight(view) + getDecoratedBottom(view)
                if (bottomRight > maxBottomRight) {
                    maxBottomRight = bottomRight
                    result = view
                }
            }
        }
        return result
    }

    private fun getWidthWithMarginsAndDecorations(view: View): Int {
        val lp = view.layoutParams as RecyclerView.LayoutParams
        return getDecoratedMeasuredWidth(view) + lp.leftMargin + lp.rightMargin
    }

    private fun getHeightWithMarginsAndDecorations(view: View): Int {
        val lp = view.layoutParams as RecyclerView.LayoutParams
        return getDecoratedMeasuredHeight(view) + lp.topMargin + lp.bottomMargin
    }

    private fun getDecoratedLeftWithMargins(view: View): Int {
        val lp = view.layoutParams as RecyclerView.LayoutParams
        return getDecoratedLeft(view) - lp.leftMargin
    }


    private fun getDecoratedTopWithMargins(view: View): Int {
        val lp = view.layoutParams as RecyclerView.LayoutParams
        return getDecoratedTop(view) - lp.topMargin
    }

    private fun getDecoratedRightWithMargins(view: View): Int {
        val lp = view.layoutParams as RecyclerView.LayoutParams
        return getDecoratedRight(view) + lp.rightMargin
    }

    private fun getDecoratedBottomWithMargins(view: View): Int {
        val lp = view.layoutParams as RecyclerView.LayoutParams
        return getDecoratedBottom(view) + lp.bottomMargin
    }

    override fun isAutoMeasureEnabled(): Boolean = true

    override fun setMeasuredDimension(childrenBounds: Rect, wSpec: Int, hSpec: Int) {
        val width: Int
        val height: Int
        val horizontalPadding = paddingLeft + paddingRight
        val verticalPadding = paddingTop + paddingBottom
        if (orientation == LinearLayoutManager.VERTICAL) {
            val usedHeight = childrenBounds.height() + verticalPadding
            height = chooseSize(
                hSpec,
                usedHeight,
                minimumHeight
            )
            width = chooseSize(
                wSpec, childrenBounds.width() + horizontalPadding,
                minimumWidth
            )
        } else {
            val usedWidth = childrenBounds.width() + horizontalPadding
            width = chooseSize(
                wSpec,
                usedWidth,
                minimumWidth
            )
            height = chooseSize(
                hSpec, childrenBounds.height() + verticalPadding,
                minimumHeight
            )
        }
        setMeasuredDimension(width, height)
    }
}