package com.knight.oneday.views.choice

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.R

/**
 * Create by FLJ in 2020/5/12 9:44
 * 水平小时选择器(目前的实现方案并不尽善尽美 最佳的实现方案应该是 Camare)
 * am pm 24Hours
 */
@Deprecated("没有使用这种方式完成 最佳应该是结合到recyclerView和camera")
class HorizontalHourView @JvmOverloads constructor(
    context: Context, private val attrs: AttributeSet? = null, private val defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @ColorInt
    private var selectedColor: Int = Color.WHITE
    @ColorInt
    private var unSelectedColor: Int = Color.BLUE
    @Dimension(unit = Dimension.SP)
    private var selectedTextSize: Float = 16F
    @Dimension(unit = Dimension.SP)
    private var unSelectedTextSize: Float = 12F

    @Retention(AnnotationRetention.RUNTIME)
    @IntDef(Companion.TIME_TYPE_12, Companion.TIME_TYPE_24)
    annotation class TimeType

    @TimeType
    private var timeType = TIME_TYPE_12
    private var needParallax: Boolean = true
    private lateinit var timeStringArray: Array<String>
    private lateinit var contentView: View
    private lateinit var arrowIv: AppCompatImageView
    private lateinit var rvHour: RecyclerView

    private var selectedRange = intArrayOf()

    companion object {
        const val TIME_TYPE_12 = 0
        const val TIME_TYPE_24 = 1
    }


    init {
        initAttr()
        initRecyclerView()
    }

    private fun initAttr() {
        contentView =
            LayoutInflater.from(context).inflate(R.layout.horizontal_time_view, this, true)
        arrowIv = contentView.findViewById(R.id.time_arrow)
        val locationArrow = intArrayOf(0, 0)
        arrowIv.getLocationOnScreen(locationArrow)
        rvHour = contentView.findViewById(R.id.rv_hours)
        val typeArray = context!!.obtainStyledAttributes(
            attrs,
            R.styleable.HorizontalHourView,
            defStyleAttr,
            R.style.HorizontalHourView
        )
        typeArray.run {
            selectedColor =
                getColor(R.styleable.HorizontalHourView_hhvSelectedTextColor, Color.BLUE)
            unSelectedColor =
                getColor(R.styleable.HorizontalHourView_hhvUnSelectedColor, Color.BLUE)
            selectedTextSize = getDimension(R.styleable.HorizontalHourView_hhvSelectedTextSize, 0F)
            unSelectedTextSize =
                getDimension(R.styleable.HorizontalHourView_hhvUnSelectedTextSize, 0F)
            timeType = getInteger(R.styleable.HorizontalHourView_hhvTimeType, TIME_TYPE_12)
            needParallax = getBoolean(R.styleable.HorizontalHourView_hhvNeedParallax, true)
            timeStringArray =
                resources.getStringArray(if (timeType == TIME_TYPE_12) R.array.time_type_12 else R.array.time_type_24)
        }
        typeArray.recycle()
    }

    private fun initRecyclerView() {
        rvHour.layoutManager = LoopLayoutManager(1, RecyclerView.HORIZONTAL)
        rvHour.adapter = HourAdapter()
        rvHour.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                /*if (newState == 0) {
                    timeStringArray.forEachIndexed { index, _ ->
                        val vh =
                            recyclerView.findViewHolderForAdapterPosition(index) as? HourViewHolder
                        val location = intArrayOf(0, 0)
                        vh?.hourText?.getLocationOnScreen(location)
                       *//* Log.d(
                            "TAG_HOUR",
                            "${location[0]}"
                        )*//*
                        // if(location[0])
                    }
                }*/
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        // 当滑动完成时 在这里判断选中了谁
                    }
                    RecyclerView.SCROLL_STATE_SETTLING -> {
                        // 当用户抬手后自行滑动时
                    }
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        // 当用户在拖动的时候 判断当前的位置让 时间显示根据当前的位置
                    }

                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("TAG_HOUR", "$dx")
                // 在这里当滑动距离越来越小的时候 回调让时间慢慢恢复
            }
        })
    }

    inner class HourAdapter : RecyclerView.Adapter<HourViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.hour_view, parent, false)
            return HourViewHolder(view)
        }

        override fun getItemCount(): Int = timeStringArray.size

        override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
            holder.hourText.text = timeStringArray[position]
            holder.hourText.textSize = unSelectedTextSize
            holder.hourText.setTextColor(unSelectedColor)
        }

    }

    inner class HourViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val hourText = view.findViewById<TextView>(R.id.hour_tv)

    }


}


