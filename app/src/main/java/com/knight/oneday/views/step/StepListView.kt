package com.knight.oneday.views.step

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.data.Step
import com.knight.oneday.data.StepDiffUtil
import com.knight.oneday.databinding.StepListViewItemBinding
import com.knight.oneday.utilities.singleClick

/**
 * Create by FLJ in 2020/4/26 10:02
 * 步骤View
 */
class StepListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private lateinit var stepListAdapter: StepListAdapter

    private var selectedStepView: StepView? = null

    var selectedStepChangeListener: SelectedStepChangeListener? = null

    init {
        initAdapter()
    }

    fun setStepList(list: List<Step>) {
        Log.d("Empty", "setStepList")
        stepListAdapter.submitList(list)
    }

    private fun initAdapter() {
        Log.d("Empty", "initAdapter")
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        stepListAdapter = StepListAdapter()
        adapter = stepListAdapter
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

    inner class StepListViewHolder(val binding: StepListViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(step: Step) {
            binding.stepListItem.stepViewListener = object : StepView.StepViewListener {
                override fun onStatusChangeListener(fromStatus: Int, toStatus: Int) {

                }

                override fun onSelectedStateChangeListener(isSelected: Boolean) {
                    if (isSelected) {
                        if (selectedStepView != binding.stepListItem) {
                            selectedStepView?.cancelSelected()
                            selectedStepChangeListener?.onSelectedStepChanged(step)
                            selectedStepView = binding.stepListItem
                        }
                    }
                }
            }
        }
    }

    interface SelectedStepChangeListener {
        fun onSelectedStepChanged(step: Step)
    }

}