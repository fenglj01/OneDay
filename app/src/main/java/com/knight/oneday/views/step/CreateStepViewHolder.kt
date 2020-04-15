package com.knight.oneday.views.step

import android.view.View
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

        }
    }

    class AddStepContentViewHolder(
        private val binding: CreateStepAddContentItemBinding,
        private val createStepAdapterListener: CreateStepAdapter.CreateStepAdapterListener
    ) : CreateStepViewHolder<CreateStepItem.AddStepContentItem>(binding.root) {
        override fun bind(stepItem: CreateStepItem.AddStepContentItem) {

        }
    }




}