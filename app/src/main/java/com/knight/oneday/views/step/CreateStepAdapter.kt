package com.knight.oneday.views.step

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.databinding.CreateStepAddContentItemBinding
import com.knight.oneday.databinding.CreateStepAddIconItemBinding

/**
 * Create by FLJ in 2020/4/14 15:27
 * 创建Adapter
 */
@Suppress("UNCHECKED_CAST")
class CreateStepAdapter(private val listener: CreateStepAdapterListener) :
    ListAdapter<CreateStepItem, CreateStepViewHolder<CreateStepItem>>(CreateStepItem.StepItemDiff) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CreateStepViewHolder<CreateStepItem> {
        return when (viewType) {
            VIEW_TYPE_CREATE_STEP_ADD_ICON -> CreateStepViewHolder.AddStepViewHolder(
                CreateStepAddIconItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                listener
            )
            VIEW_TYPE_CREATE_STEP_ADD_CONTENT_ITEM -> CreateStepViewHolder.AddStepContentViewHolder(
                CreateStepAddContentItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                listener
            )
            else -> throw RuntimeException("Unsupported view holder type")
        } as CreateStepViewHolder<CreateStepItem>
    }

    override fun onBindViewHolder(holder: CreateStepViewHolder<CreateStepItem>, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CreateStepItem.AddStepIconItem -> VIEW_TYPE_CREATE_STEP_ADD_ICON
            is CreateStepItem.AddStepContentItem -> VIEW_TYPE_CREATE_STEP_ADD_CONTENT_ITEM
            else -> throw RuntimeException("Unsupported ItemViewType for obj ${getItem(position)}")
        }
    }

    interface CreateStepAdapterListener {
        fun onAddStepClick()
        fun onAddStepContentRemoveClick(position: Int)
        fun onAddStepContentRemove(position: Int)
    }
}

private const val VIEW_TYPE_CREATE_STEP_ADD_ICON = 1
private const val VIEW_TYPE_CREATE_STEP_ADD_CONTENT_ITEM = 2