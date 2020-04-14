package com.knight.oneday.views.step

import androidx.recyclerview.widget.DiffUtil

/**
 * Create by FLJ in 2020/4/14 14:41
 * 创建步骤项
 */
sealed class CreateStepItem {

    data class AddStepIconItem(val id: Int) : CreateStepItem()

    data class AddStepContentItem(var content: String, var serialNumber: Int) : CreateStepItem()

    object StepItemDiff : DiffUtil.ItemCallback<CreateStepItem>() {
        override fun areItemsTheSame(
            oldItem: CreateStepItem,
            newItem: CreateStepItem
        ): Boolean {
            return when {
                oldItem is AddStepIconItem && newItem is AddStepIconItem -> newItem.id == oldItem.id
                newItem is AddStepContentItem && oldItem is AddStepContentItem -> newItem.content == oldItem.content
                else -> oldItem == newItem
            }
        }

        override fun areContentsTheSame(oldItem: CreateStepItem, newItem: CreateStepItem): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }

}