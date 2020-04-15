package com.knight.oneday.views.step

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.R

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
        val insertIndex = createStepItems.size - 1
        createStepItems.add(
            insertIndex,
            CreateStepItem.AddStepContentItem(CreateStepContent(serialNumber = insertIndex + 1))
        )
        adapter?.notifyItemInserted(insertIndex)
    }

    override fun onAddStepContentRemoveClick() {

    }

    companion object {
        const val STATE_INIT = 0
        const val STATE_HOLD = 1
    }


}