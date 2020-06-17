package com.knight.oneday.task

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.utilities.TaskType
import com.knight.oneday.utilities.sp

/**
 * Create by FLJ in 2020/6/17 11:02
 * 任务时间轴
 */
class TaskTimeLineItemDecoration private constructor(private val params: TaskTimeLineParams) :
    RecyclerView.ItemDecoration() {


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
    }

    interface TimeLineContentCallback {
        fun getTime(position: Int): String
        fun getTaskType(position: Int): TaskType
    }

    class TaskTimeLineParams {
        @ColorInt
        var timeLineUnFinishedColor: Int = Color.BLACK
        @ColorInt
        var timeLineFinishedColor: Int = Color.GRAY
        @ColorInt
        var timeLineExpiredColor: Int = Color.RED

        var timeLineTextSize: Float = 12F.sp
        @ColorInt
        var timeLineUnFinishedTextColor = Color.BLACK
        @ColorInt
        var timeLineFinishedTextColor = Color.GRAY
        @ColorInt
        var timeLineExpiredTextColor = Color.RED

        var timeLineCallback: TimeLineContentCallback? = null

    }

    companion object BUILD {

        private val timeLineParams = TaskTimeLineParams()

        fun setTimeLineUnFinishedColor(@ColorInt color: Int): BUILD {
            timeLineParams.timeLineUnFinishedColor = color
            return this
        }

        fun setTimeLineFinishedColor(@ColorInt color: Int): BUILD {
            timeLineParams.timeLineFinishedColor = color
            return this
        }

        fun setTimeLineExpiredColor(@ColorInt color: Int): BUILD {
            timeLineParams.timeLineExpiredColor = color
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

        fun create() = TaskTimeLineItemDecoration(timeLineParams)
    }

}