package com.knight.oneday.views.choice

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.R

/**
 * Create by FLJ in 2020/5/12 9:44
 * 水平小时选择器
 * am pm 24Hours
 */
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

    }

}

data class TimeBean(val am: String, val pm: String)

