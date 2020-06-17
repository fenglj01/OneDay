package com.knight.oneday.task

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
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

    init {
        textPaint.apply {
            textSize = params.timeLineTextSize
            color = params.timeLineUnFinishedTextColor
        }
        timeLinePaint.apply {
            color = params.timeLineUnFinishedFillColor
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
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

        var timeLineStyle = 0

        var timeLineUnfinishedIcon: Drawable? = null
        var timeLineFinishedIcon: Drawable? = null
        var timeLineExpiredIcon: Drawable? = null

        var timeLineCallback: TimeLineContentCallback? = null

    }

    companion object BUILD {

        const val STYLE_FILL = 0
        const val STYLE_FILL_STROKE = 1

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

        fun create() = TaskTimeLineItemDecoration(timeLineParams)
    }

}