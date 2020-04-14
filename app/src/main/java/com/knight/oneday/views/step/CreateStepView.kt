package com.knight.oneday.views.step

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by FLJ in 2020/4/14 14:36
 * 创建步骤View,将适配操作封装进来 只暴露给外部一个获取的列表
 */
class CreateStepView : RecyclerView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, set: AttributeSet?) : this(context, set, 0)

    constructor(context: Context, set: AttributeSet?, def: Int) : super(context, set, def) {
        initAdapter()
    }

    private fun initAdapter() {

    }



}