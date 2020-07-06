package com.knight.oneday.views.step

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.R
import com.knight.oneday.utilities.dp2px
import com.knight.oneday.views.SimpleItemDecoration
import com.knight.oneday.views.showSnackBar

/**
 * Create by FLJ in 2020/4/14 14:36
 * 创建步骤View,将适配操作封装进来 只暴露给外部一个获取的列表
 */
class CreateStepView : RecyclerView, CreateStepAdapter.CreateStepAdapterListener {


    private val createStepItems = mutableListOf<CreateStepItem>()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, set: AttributeSet?) : this(context, set, 0)

    constructor(context: Context, set: AttributeSet?, def: Int) : super(context, set, def) {
        initAdapter()
    }

    private fun initAdapter() {
        createStepItems.add(CreateStepItem.AddStepIconItem(0))
        val listAdapter = CreateStepAdapter(this)
        adapter = listAdapter
        listAdapter.submitList(createStepItems)
    }

    fun getStepContentList() =
        createStepItems.asSequence()
            .filter { it is CreateStepItem.AddStepContentItem }
            .map { (it as CreateStepItem.AddStepContentItem).createStepContent }
            .toList()

    override fun onAddStepClick() {
        if (checkPreviousIsEditFinished()) {
            val insertIndex = createStepItems.size - 1
            createStepItems.add(
                insertIndex,
                CreateStepItem.AddStepContentItem(CreateStepContent(serialNumber = insertIndex + 1))
            )
            adapter?.notifyItemInserted(insertIndex)
        } else {
            context.showSnackBar(this, R.string.app_name)
        }
    }

    override fun onAddStepContentRemoveClick(position: Int) {
        checkHaveNextStep(position)
    }

    override fun onAddStepContentRemove(position: Int) {
        checkHaveNextStep(position)
    }

    private fun checkHaveNextStep(position: Int) {
        // 先刪除该项
        removeItemByPosition(position)
        // 如果下一向不是末尾项
        if (position + 1 != createStepItems.size) {
            for (nextIndex in position until createStepItems.size - 1) {
                (createStepItems[nextIndex] as CreateStepItem.AddStepContentItem).createStepContent.serialNumber =
                    nextIndex + 1
                adapter?.notifyItemChanged(nextIndex)
            }
        }
    }


    private fun removeItemByPosition(position: Int) {
        createStepItems.removeAt(position)
        adapter?.notifyItemRemoved(position)
    }

    private fun checkPreviousIsEditFinished(): Boolean {
        // 为默认创建项时 可以直接创建
        if (createStepItems.size == 1) return true
        // 判断上一条添加项目是否是在添加状态 如果是则无法添加下一步
        val previousStatus =
            ((createStepItems[createStepItems.size - 2]) as CreateStepItem.AddStepContentItem).createStepContent.status
        return previousStatus != ADD_STEP_CONTENT_STATUS_ADD
    }

}