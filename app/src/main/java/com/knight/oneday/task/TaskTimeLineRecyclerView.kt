package com.knight.oneday.task

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.R
import com.knight.oneday.utilities.TaskType
import com.knight.oneday.utilities.dp
import com.knight.oneday.utilities.sp

/**
 * Create by FLJ in 2020/6/17 13:30
 */
class TaskTimeLineRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), TaskTimeLineItemDecoration.TimeLineContentCallback {

    private val itemDecoration: TaskTimeLineItemDecoration

    init {
        val typeArray =
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.TaskTimeLineRecyclerView,
                defStyleAttr,
                R.style.TaskTimeLineRecyclerView
            )
        typeArray.run {
            itemDecoration = TaskTimeLineItemDecoration.setTimeLineColor(
                getColor(
                    R.styleable.TaskTimeLineRecyclerView_timeLineColor,
                    Color.BLACK
                )
            ).setTimeLineCircleRadius(
                getDimension(
                    R.styleable.TaskTimeLineRecyclerView_timeLineCircleRadius,
                    32F.dp
                )
            ).setTimeLineExpiredFillColor(
                getColor(
                    R.styleable.TaskTimeLineRecyclerView_timeLineExpiredFillColor,
                    Color.BLACK
                )
            ).setTimeLineExpiredStrokeColor(
                getColor(
                    R.styleable.TaskTimeLineRecyclerView_timeLineExpiredStrokeColor,
                    Color.BLACK
                )
            ).setTimeLineExpiredTextColor(
                getColor(
                    R.styleable.TaskTimeLineRecyclerView_timeLineExpiredTextColor,
                    Color.BLACK
                )
            ).setTimeLineExpiredIcon(
                getDrawable(R.styleable.TaskTimeLineRecyclerView_timeLineExpiredIcon)
            ).setTimeLineFinishedFillColor(
                getColor(
                    R.styleable.TaskTimeLineRecyclerView_timeLineFinishedFillColor,
                    Color.BLACK
                )
            ).setTimeLineFinishedStrokeColor(
                getColor(
                    R.styleable.TaskTimeLineRecyclerView_timeLineFinishedStrokeColor,
                    Color.BLACK
                )
            ).setTimeLineFinishedTextColor(
                getColor(
                    R.styleable.TaskTimeLineRecyclerView_timeLineFinishedTextColor,
                    Color.BLACK
                )
            ).setTimeLineFinishedIcon(
                getDrawable(R.styleable.TaskTimeLineRecyclerView_timeLineFinishedIcon)
            ).setTimeLineUnFinishedFillColor(
                getColor(
                    R.styleable.TaskTimeLineRecyclerView_timeLineUnfinishedFillColor,
                    Color.BLACK
                )
            ).setTimeLineUnFinishedStrokeColor(
                getColor(
                    R.styleable.TaskTimeLineRecyclerView_timeLineUnfinishedStrokeColor,
                    Color.BLACK
                )
            ).setTimeLineUnFinishedTextColor(
                getColor(
                    R.styleable.TaskTimeLineRecyclerView_timeLineUnfinishedTextColor,
                    Color.BLACK
                )
            ).setTimeLineUnfinishedIcon(
                getDrawable(R.styleable.TaskTimeLineRecyclerView_timeLineUnfinishedIcon)
            ).setTimeLineStyle(
                getInt(R.styleable.TaskTimeLineRecyclerView_timeLineStyle, 0)
            ).setTimeLineTextSize(
                getDimension(R.styleable.TaskTimeLineRecyclerView_timeLineTextSize, 12F.sp)
            ).setTimeLineCircleRadius(
                getDimension(R.styleable.TaskTimeLineRecyclerView_timeLineCircleRadius, 32F.dp)
            ).setTimeLineWidth(
                getDimension(R.styleable.TaskTimeLineRecyclerView_timeLineWidth, 2F.dp)
            ).setTimeLineStartOffset(
                getDimension(R.styleable.TaskTimeLineRecyclerView_timeLineStartOffset, 100F.dp)
            ).setTimeLineTopOffset(
                getDimension(R.styleable.TaskTimeLineRecyclerView_timeLineTopOffset, 48F.dp)
            ).setTimeLineContentCallback(this@TaskTimeLineRecyclerView)
                .create()
            recycle()
        }

    }

    override fun getTime(position: Int): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTaskType(position: Int): TaskType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTaskStatus(position: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}