package com.knight.oneday.views.step

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.knight.oneday.R
import com.knight.oneday.data.Step

/**
 * Create by FLJ in 2020/4/27 10:15
 * 列表中对Step进行预览以及操作的整合View
 */
class StepListControlView : ConstraintLayout {

    private val contentView: View
    private val stepListView: StepListView
    private val overViewStepTextView: TextView
    private val previewStepTextView: TextView
    private val stepList: MutableList<Step>

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context?, attributeSet: AttributeSet?, def: Int) : super(
        context,
        attributeSet,
        def
    ) {
        contentView =
            LayoutInflater.from(context).inflate(R.layout.step_list_control_view, this, true)
        stepList = mutableListOf()
        stepListView = contentView.findViewById(R.id.step_list_view)
        overViewStepTextView = contentView.findViewById(R.id.overview_step_tv)
        previewStepTextView = contentView.findViewById(R.id.preview_step_tv)
    }

    fun setUpStepList(list: List<Step>) {
        stepList.clear()
        stepList.addAll(list)
        stepListView.setStepList(stepList)
        stepListView.selectedStepChangeListener = object : StepListView.SelectedStepChangeListener {
            override fun onSelectedStepChanged(step: Step) {

            }
        }
    }


}