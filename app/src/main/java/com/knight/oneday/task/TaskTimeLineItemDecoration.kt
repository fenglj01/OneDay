package com.knight.oneday.task

import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.utilities.TaskType
import com.knight.oneday.utilities.dp
import com.knight.oneday.utilities.sp

/**
 * Create by FLJ in 2020/6/17 11:02
 * 任务时间轴
 */
class TaskTimeLineItemDecoration private constructor(private val params: TaskTimeLineParams) :
    RecyclerView.ItemDecoration() {

    private val textPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val timeLinePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val timeLineIconPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val timeLineCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val timeLineCircleStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val timeLineTextRect: Rect = Rect()

    init {
        textPaint.apply {
            textSize = params.timeLineTextSize
            color = params.timeLineUnFinishedTextColor
            typeface = Typeface.MONOSPACE
            isFakeBoldText = true
        }
        timeLinePaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = params.timeLineWidth
            color = params.timeLineColor
        }
        timeLineCircleStrokePaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = params.timeLineStrokeWidth
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val visibleCount = parent.childCount

        for (index in 0..visibleCount) {
            val visibleItemView = parent.getChildAt(index)
            val visibleItemViewPosition = parent.getChildAdapterPosition(visibleItemView)
            if (visibleItemView == null) return
            /* 绘制时间轴圆 */
            drawTimeLine(visibleItemView, c, visibleItemViewPosition)

        }

    }


    private fun drawTimeLine(visibleItemView: View, canvas: Canvas, visibleItemViewPosition: Int) {

        val centerX = visibleItemView.left - params.timeLineStartOffset * 0.25F
        val centerY = visibleItemView.top + visibleItemView.height / 2F

        val topLineStartY = visibleItemView.top - params.timeLineTopOffset
        val topLineEndY = centerY - params.timeLineCircleRadius - 4F.dp
        val bottomLineStatY = centerY + params.timeLineCircleRadius + 4F.dp
        val bottomLineEndY = visibleItemView.bottom.toFloat()
        /* 绘制时间线 */
        canvas.drawLine(centerX, topLineStartY, centerX, topLineEndY, timeLinePaint)
        canvas.drawLine(centerX, bottomLineStatY, centerX, bottomLineEndY, timeLinePaint)

        val taskStatus =
            params.timeLineCallback?.getTaskStatus(visibleItemViewPosition) ?: STATUS_UNFINISHED

        prepareCircleFillColor(taskStatus)

        /* 绘制圆 */
        canvas.drawCircle(centerX, centerY, params.timeLineCircleRadius, timeLineCirclePaint)
        /* 绘制描边 */
        if (params.timeLineStyle == STYLE_FILL_STROKE) {
            prepareCircleStrokeColor(taskStatus)
            canvas.drawCircle(
                centerX,
                centerY,
                params.timeLineCircleRadius,
                timeLineCircleStrokePaint
            )
        }
        /* 绘制标志icon */
        val drawableCircle = prepareDrawable(taskStatus)
        drawableCircle?.run {
            val drawableWidth = intrinsicWidth
            val drawableHeight = intrinsicHeight
            val rect = Rect(
                (centerX - drawableWidth / 2).toInt(),
                (centerY - drawableHeight / 2).toInt(),
                (centerX + drawableWidth / 2).toInt(),
                (centerY + drawableHeight / 2).toInt()
            )
            bounds = rect
            draw(canvas)
        }
        /* 绘制时间 */
        val timeText = params.timeLineCallback?.getTime(visibleItemViewPosition)
        timeText?.run {
            prepareTextColor(taskStatus)
            textPaint.getTextBounds(this, 0, length, timeLineTextRect)
            val mw = textPaint.measureText(this)
            val textX =
                visibleItemView.left - params.timeLineStartOffset * 0.65F - mw / 2
            val textY = centerY + timeLineTextRect.height() / 2
            canvas.drawText(this, textX, textY, textPaint)
        }

    }

    private fun prepareTextColor(taskStatus: Int) {
        textPaint.color = when (taskStatus) {
            STATUS_EXPIRED -> params.timeLineExpiredTextColor
            STATUS_FINISHED -> params.timeLineFinishedTextColor
            else -> params.timeLineUnFinishedTextColor
        }
    }

    private fun prepareDrawable(taskStatus: Int): Drawable? = when (taskStatus) {
        STATUS_EXPIRED -> params.timeLineExpiredIcon
        STATUS_FINISHED -> params.timeLineFinishedIcon
        else -> params.timeLineUnfinishedIcon
    }

    private fun prepareCircleFillColor(taskStatus: Int) {
        timeLineCirclePaint.color = when (taskStatus) {
            STATUS_EXPIRED -> params.timeLineExpiredFillColor
            STATUS_FINISHED -> params.timeLineFinishedFillColor
            else -> params.timeLineUnFinishedFillColor
        }
    }

    private fun prepareCircleStrokeColor(taskStatus: Int) {
        timeLineCircleStrokePaint.color = when (taskStatus) {
            STATUS_EXPIRED -> params.timeLineExpiredStrokeColor
            STATUS_FINISHED -> params.timeLineFinishedStrokeColor
            else -> params.timeLineUnFinishedStrokeColor
        }
    }


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(params.timeLineStartOffset.toInt(), params.timeLineTopOffset.toInt(), 0, 0)
    }

    interface TimeLineContentCallback {
        fun getTime(position: Int): String
        fun getTaskType(position: Int): TaskType
        fun getTaskStatus(position: Int): Int
    }

    class TaskTimeLineParams {

        @ColorInt
        var timeLineColor: Int = Color.BLACK
        @ColorInt
        var timeLineUnFinishedFillColor: Int = Color.BLACK
        @ColorInt
        var timeLineUnFinishedStrokeColor: Int = Color.GRAY
        @ColorInt
        var timeLineFinishedFillColor: Int = Color.GRAY
        @ColorInt
        var timeLineFinishedStrokeColor: Int = Color.GRAY
        @ColorInt
        var timeLineExpiredFillColor: Int = Color.RED
        @ColorInt
        var timeLineExpiredStrokeColor: Int = Color.RED

        var timeLineTextSize: Float = 12F.sp
        @ColorInt
        var timeLineUnFinishedTextColor = Color.BLACK
        @ColorInt
        var timeLineFinishedTextColor = Color.GRAY
        @ColorInt
        var timeLineExpiredTextColor = Color.RED

        @Dimension
        var timeLineCircleRadius = 24f.dp
        @Dimension
        var timeLineWidth = 2f.dp
        @Dimension
        var timeLineStartOffset = 100f.dp
        @Dimension
        var timeLineTopOffset = 48F.dp
        @Dimension
        var timeLineStrokeWidth = 2f.dp

        var timeLineStyle = 0

        var timeLineUnfinishedIcon: Drawable? = null
        var timeLineFinishedIcon: Drawable? = null
        var timeLineExpiredIcon: Drawable? = null

        var timeLineCallback: TimeLineContentCallback? = null

    }

    companion object BUILD {

        const val STYLE_FILL = 0
        const val STYLE_FILL_STROKE = 1

        const val STATUS_UNFINISHED = 0
        const val STATUS_FINISHED = 1
        const val STATUS_EXPIRED = 2

        private val timeLineParams = TaskTimeLineParams()

        fun setTimeLineColor(@ColorInt color: Int): BUILD {
            timeLineParams.timeLineColor = color
            return this
        }

        fun setTimeLineUnFinishedFillColor(@ColorInt color: Int): BUILD {
            timeLineParams.timeLineUnFinishedFillColor = color
            return this
        }

        fun setTimeLineUnFinishedStrokeColor(@ColorInt color: Int): BUILD {
            timeLineParams.timeLineUnFinishedStrokeColor = color
            return this
        }

        fun setTimeLineFinishedFillColor(@ColorInt color: Int): BUILD {
            timeLineParams.timeLineFinishedFillColor = color
            return this
        }

        fun setTimeLineFinishedStrokeColor(@ColorInt color: Int): BUILD {
            timeLineParams.timeLineFinishedStrokeColor = color
            return this
        }

        fun setTimeLineExpiredFillColor(@ColorInt color: Int): BUILD {
            timeLineParams.timeLineExpiredFillColor = color
            return this
        }

        fun setTimeLineExpiredStrokeColor(@ColorInt color: Int): BUILD {
            timeLineParams.timeLineExpiredStrokeColor = color
            return this
        }

        fun setTimeLineUnFinishedTextColor(@ColorInt color: Int): BUILD {
            timeLineParams.timeLineUnFinishedTextColor = color
            return this
        }

        fun setTimeLineFinishedTextColor(@ColorInt color: Int): BUILD {
            timeLineParams.timeLineFinishedTextColor = color
            return this
        }

        fun setTimeLineExpiredTextColor(@ColorInt color: Int): BUILD {
            timeLineParams.timeLineExpiredTextColor = color
            return this
        }

        fun setTimeLineTextSize(@Dimension textSize: Float): BUILD {
            timeLineParams.timeLineTextSize = textSize
            return this
        }

        fun setTimeLineContentCallback(callback: TimeLineContentCallback): BUILD {
            timeLineParams.timeLineCallback = callback
            return this
        }

        fun setTimeLineWidth(@Dimension timeLineWidth: Float): BUILD {
            timeLineParams.timeLineWidth = timeLineWidth
            return this
        }

        fun setTimeLineCircleRadius(@Dimension timeLineCircleRadius: Float): BUILD {
            timeLineParams.timeLineCircleRadius = timeLineCircleRadius
            return this
        }

        fun setTimeLineFinishedIcon(timeLineFinishedIcon: Drawable?): BUILD {
            timeLineParams.timeLineFinishedIcon = timeLineFinishedIcon
            return this
        }

        fun setTimeLineUnfinishedIcon(timeLineUnfinishedIcon: Drawable?): BUILD {
            timeLineParams.timeLineUnfinishedIcon = timeLineUnfinishedIcon
            return this
        }

        fun setTimeLineExpiredIcon(timeLineExpiredIcon: Drawable?): BUILD {
            timeLineParams.timeLineExpiredIcon = timeLineExpiredIcon
            return this
        }

        fun setTimeLineStyle(timeLineStyle: Int): BUILD {
            timeLineParams.timeLineStyle = timeLineStyle
            return this
        }

        fun setTimeLineStartOffset(offset: Float): BUILD {
            timeLineParams.timeLineStartOffset = offset
            return this
        }

        fun setTimeLineTopOffset(offset: Float): BUILD {
            timeLineParams.timeLineTopOffset = offset
            return this
        }

        fun setTimeLineStrokeWidth(width: Float): BUILD {
            timeLineParams.timeLineStrokeWidth = width
            return this
        }

        fun create() = TaskTimeLineItemDecoration(timeLineParams)
    }

}