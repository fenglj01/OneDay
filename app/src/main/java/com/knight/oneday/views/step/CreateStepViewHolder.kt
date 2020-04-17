package com.knight.oneday.views.step

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.databinding.CreateStepAddContentItemBinding
import com.knight.oneday.databinding.CreateStepAddIconItemBinding
import com.knight.oneday.utilities.clearStrikeText
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.utilities.strikeText

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

                // 清理一下TextWatcher
                if (stepContentEdt.tag is TextWatcher) {
                    stepContentEdt.removeTextChangedListener(stepContentEdt.tag as TextWatcher)
                }
                // 清理一下stateChanged
                if (stepView.tag is StepNumberView.OnStepStateChangedListener) {
                    stepView.clearOnStepStateChangedListener()
                }

                // 主要目的是修改其编辑状态 来达到创建下一步的判断 当删减为0时 触发弹出删除框
                val textWatcher = object : TextChangeListener() {
                    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        text?.run {
                            stepItem.createStepContent.status =
                                if (length > 0) ADD_STEP_CONTENT_STATUS_EDIT
                                else ADD_STEP_CONTENT_STATUS_ADD
                            stepItem.createStepContent.content = toString()
                            // 回调告知可以删除这个步骤了
                            if (length == 0) createStepAdapterListener.onAddStepContentRemove(
                                adapterPosition
                            )
                        }
                    }
                }

                stepContentEdt.setText(stepItem.createStepContent.content)
                stepContentEdt.addTextChangedListener(textWatcher)
                stepContentEdt.tag = textWatcher

                val stepState = stepItem.createStepContent.state
                setUpEditTextByState(stepState)
                stepView.setState(stepState)

                val stepStateChange = object : StepNumberView.OnStepStateChangedListener {
                    override fun onStepStateChange(state: Int) {
                        stepItem.createStepContent.state = state
                        setUpEditTextByState(state)
                    }
                }
                stepView.onStepStateChangedListener = stepStateChange
                stepView.tag = stepStateChange

                stepRemove.singleClick {
                    createStepAdapterListener.onAddStepContentRemoveClick(adapterPosition)
                }
                executePendingBindings()
            }
        }

        private fun setUpEditTextByState(state: Int) {
            if (state == STEP_STATE_FINISHED) {
                binding.stepContentEdt.strikeText()
            } else {
                binding.stepContentEdt.clearStrikeText()
            }
        }
    }


    abstract class TextChangeListener : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

}