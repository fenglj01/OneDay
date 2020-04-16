package com.knight.oneday.views.step

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.R
import com.knight.oneday.views.showSnackBar

/**
 * Create by FLJ in 2020/4/14 14:36
 * 创建步骤View,将适配操作封装进来 只暴露给外部一个获取的列表
 */
class CreateStepView : RecyclerView, CreateStepAdapter.CreateStepAdapterListener {

    /*
        当前状态,初始/拥有,edit icon的显示状态会根据状态的不一样有不同的显示
     */
    private var state = STATE_INIT

    /* 步骤content集合 */
    private val stepContents = mutableListOf<String>()

    private val createStepItems = mutableListOf<CreateStepItem>()

    private lateinit var addStepHint: String
    private lateinit var nextStepHint: String

    constructor(context: Context) : this(context, null)

    constructor(context: Context, set: AttributeSet?) : this(context, set, 0)

    constructor(context: Context, set: AttributeSet?, def: Int) : super(context, set, def) {
        initRes()
        initAdapter()
    }

    private fun initRes() {
        addStepHint = context.resources.getString(R.string.create_step_hint)
        nextStepHint = context.resources.getString(R.string.create_step_next_hint)
    }

    private fun initAdapter() {
        createStepItems.add(CreateStepItem.AddStepIconItem(0))
        val listAdapter = CreateStepAdapter(this)
        adapter = listAdapter
        listAdapter.submitList(createStepItems)
    }

    fun getStepContentList() = stepContents.toList()

    override fun onAddStepClick() {
        Log.d("TAG_CREATE", "addClick")
        if (checkPreviousIsEditFinished()) {
            val insertIndex = createStepItems.size - 1
            createStepItems.add(
                insertIndex,
                CreateStepItem.AddStepContentItem(CreateStepContent(serialNumber = insertIndex + 1))
            )
            adapter?.notifyItemInserted(insertIndex)
        } else {
            context.showSnackBar(this, R.string.plase_finished_previous_step)
        }
    }

    override fun onAddStepContentRemoveClick() {

    }

    private fun checkPreviousIsEditFinished(): Boolean {
        // 为默认创建项时 可以直接创建
        if (createStepItems.size == 1) return true
        // 判断上一条添加项目是否是在添加状态 如果是则无法添加下一步
        val previousStatus =
            ((createStepItems[createStepItems.size - 2]) as CreateStepItem.AddStepContentItem).createStepContent.status
        return previousStatus != ADD_STEP_CONTENT_STATUS_ADD
    }

    companion object {
        const val STATE_INIT = 0
        const val STATE_HOLD = 1
    }


}