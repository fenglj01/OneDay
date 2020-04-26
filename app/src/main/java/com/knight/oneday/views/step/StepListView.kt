package com.knight.oneday.views.step

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.data.Step
import com.knight.oneday.data.StepDiffUtil
import com.knight.oneday.databinding.StepListViewItemBinding

/**
 * Create by FLJ in 2020/4/26 10:02
 * 步骤View
 */
class StepListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val stepList: MutableList<Step> = mutableListOf()
    val stepListAdapter = StepListAdapter()

    var stepListListener: StepListListener? = null

    init {
        initAdapter()
    }

    fun setStepList(list: List<Step>) {
        stepList.clear()
        stepList.addAll(list)
        stepListAdapter.submitList(stepList)
    }

    private fun initAdapter() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = stepListAdapter
        stepListAdapter.submitList(stepList)
    }

    inner class StepListAdapter : ListAdapter<Step, StepListViewHolder>(StepDiffUtil) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepListViewHolder {
            var binding = StepListViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return StepListViewHolder(binding)
        }

        override fun onBindViewHolder(holder: StepListViewHolder, position: Int) {
            holder.binding.step = getItem(position)
            holder.bind(getItem(position))
        }
    }

    class StepListViewHolder(val binding: StepListViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(step: Step) {
            /*binding.stepListItem.stepViewListener = object : StepView.StepViewListener {
                override fun onStatusChangeListener(fromStatus: Int, toStatus: Int) {

                }

                override fun onSelectedStateChangeListener(isSelected: Boolean) {

                }
            }*/
        }
    }

    interface StepListListener {

        fun onStepSelected(step: Step)

        fun onStepFinished(step: Step, position: Int)

        fun onStepCancelFinished(step: Step, position: Int)

    }

}