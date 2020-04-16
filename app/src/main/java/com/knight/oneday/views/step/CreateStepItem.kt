package com.knight.oneday.views.step

import androidx.recyclerview.widget.DiffUtil

/**
 * Create by FLJ in 2020/4/14 14:41
 * 创建步骤项
 */
sealed class CreateStepItem {

    data class AddStepIconItem(val id: Int) : CreateStepItem()

    data class AddStepContentItem(
        val createStepContent: CreateStepContent
    ) : CreateStepItem()

    object StepItemDiff : DiffUtil.ItemCallback<CreateStepItem>() {
        override fun areItemsTheSame(
            oldItem: CreateStepItem,
            newItem: CreateStepItem
        ): Boolean {
            return when {
                oldItem is AddStepIconItem && newItem is AddStepIconItem -> newItem.id == oldItem.id
                newItem is AddStepContentItem && oldItem is AddStepContentItem -> newItem.createStepContent.content == oldItem.createStepContent.content
                else -> oldItem == newItem
            }
        }

        override fun areContentsTheSame(oldItem: CreateStepItem, newItem: CreateStepItem): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }

}

data class CreateStepContent(
    var content: String = "",
    var serialNumber: Int = 0,
    var status: Int = ADD_STEP_CONTENT_STATUS_ADD
)

/*
    定义三种状态: 添加/编辑/完成/添加下一个
*/
const val ADD_STEP_CONTENT_STATUS_ADD = 0
const val ADD_STEP_CONTENT_STATUS_EDIT = 1
const val ADD_STEP_CONTENT_STATUS_FINISH = 2