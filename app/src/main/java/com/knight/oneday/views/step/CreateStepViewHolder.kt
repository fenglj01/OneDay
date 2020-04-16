package com.knight.oneday.views.step

import android.graphics.Color
import android.text.Editable
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.databinding.CreateStepAddContentItemBinding
import com.knight.oneday.databinding.CreateStepAddIconItemBinding
import com.knight.oneday.utilities.singleClick

/**
 * Create by FLJ in 2020/4/14 15:08
 * 创建步骤ViewHolder 分为预览项 和 添加项
 */
sealed class CreateStepViewHolder<T : CreateStepItem>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(stepItem: T, position: Int)

    class AddStepViewHolder(
        private val binding: CreateStepAddIconItemBinding,
        private val createStepAdapterListener: CreateStepAdapter.CreateStepAdapterListener
    ) : CreateStepViewHolder<CreateStepItem.AddStepIconItem>(binding.root) {
        override fun bind(stepItem: CreateStepItem.AddStepIconItem, position: Int) {
            binding.run {
                addClick = createStepAdapterListener
                executePendingBindings()
            }
        }
    }

    class AddStepContentViewHolder(
        private val binding: CreateStepAddContentItemBinding,
        private val createStepAdapterListener: CreateStepAdapter.CreateStepAdapterListener
    ) : CreateStepViewHolder<CreateStepItem.AddStepContentItem>(binding.root) {
        override fun bind(stepItem: CreateStepItem.AddStepContentItem, position: Int) {
            binding.run {
                addContent = stepItem.createStepContent
                // 主要目的是修改其编辑状态 来达到创建下一步的判断 当删减为0时 触发弹出删除框
                stepContentEdt.addTextChangedListener { text: Editable? ->
                    text?.run {
                        stepItem.createStepContent.status =
                            if (length > 0) ADD_STEP_CONTENT_STATUS_EDIT
                            else ADD_STEP_CONTENT_STATUS_ADD
                        // 回调告知可以删除这个步骤了
                        if (length == 0) createStepAdapterListener.onAddStepContentRemove(position)
                    }
                }
                stepRemove.singleClick {
                    createStepAdapterListener.onAddStepContentRemoveClick(position)
                }
                executePendingBindings()
            }
        }
    }


}