package com.knight.oneday.views.step

import android.graphics.Color
import android.text.Editable
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.databinding.CreateStepAddContentItemBinding
import com.knight.oneday.databinding.CreateStepAddIconItemBinding

/**
 * Create by FLJ in 2020/4/14 15:08
 * 创建步骤ViewHolder 分为预览项 和 添加项
 */
sealed class CreateStepViewHolder<T : CreateStepItem>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(stepItem: T)

    class AddStepViewHolder(
        private val binding: CreateStepAddIconItemBinding,
        private val createStepAdapterListener: CreateStepAdapter.CreateStepAdapterListener
    ) : CreateStepViewHolder<CreateStepItem.AddStepIconItem>(binding.root) {
        override fun bind(stepItem: CreateStepItem.AddStepIconItem) {
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
        override fun bind(stepItem: CreateStepItem.AddStepContentItem) {
            binding.run {
                addContent = stepItem.createStepContent
                // 主要目的是修改其编辑状态 来达到创建下一步的判断
                stepContentEdt.addTextChangedListener { text: Editable? ->
                    text?.run {
                        stepItem.createStepContent.status =
                            if (length > 0) ADD_STEP_CONTENT_STATUS_EDIT
                            else ADD_STEP_CONTENT_STATUS_ADD
                    }
                }
                executePendingBindings()
            }
        }
    }


}