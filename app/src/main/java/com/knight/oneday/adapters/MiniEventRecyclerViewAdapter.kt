package com.knight.oneday.adapters

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.R
import com.knight.oneday.adapters.holders.MiniEventViewHolder
import com.knight.oneday.data.Event

/**
 * @author knight
 * create at 20-3-11 下午8:36
 * 极简版事件Adapter
 * https://www.jianshu.com/p/043b1c069868 吸顶分类 明日找个最终方案实现一下
 */
class MiniEventRecyclerViewAdapter : RecyclerView.Adapter<MiniEventViewHolder>() {

    private val events: MutableList<Event> = mutableListOf()
    private lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniEventViewHolder {
        if (!::layoutInflater.isInitialized)
            layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.rv_item_mini_event, parent, false)
        return MiniEventViewHolder(view)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: MiniEventViewHolder, position: Int) {
        holder.bindData(events[position])
    }

    fun submitList(list: List<Event>) {
        events.addAll(list)
        notifyDataSetChanged()
    }

}