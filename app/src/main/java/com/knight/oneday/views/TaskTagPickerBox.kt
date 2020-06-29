package com.knight.oneday.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.knight.oneday.R
import com.knight.oneday.nav.NavigationModelItem
import com.ramotion.directselect.DSAbstractPickerBox

/**
 * Create by FLJ in 2020/4/9 10:37
 * 事件分类 Tag 选择器
 */
class TaskTagPickerBox : DSAbstractPickerBox<NavigationModelItem.NavTaskTag> {

    private lateinit var cellTv: TextView
    private lateinit var cellIv: ImageView
    private lateinit var cellRoot: View

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        inflaterBoxView(context)
    }

    private fun inflaterBoxView(context: Context) {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.event_tag_picker_box_layout, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        cellIv = findViewById(R.id.cell_icon_iv)
        cellTv = findViewById(R.id.cell_title_tv)
        cellRoot = findViewById(R.id.cell_container)
    }

    override fun onSelect(tagItem: NavigationModelItem.NavTaskTag?, tagIndex: Int) {
        tagItem?.run {
            cellIv.setImageResource(icon)
            cellTv.text = taskTag
        }
    }

    override fun getCellRoot(): View = cellRoot
}
