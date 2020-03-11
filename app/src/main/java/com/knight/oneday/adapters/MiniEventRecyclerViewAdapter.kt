package com.knight.oneday.adapters

import android.graphics.Canvas
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.data.Event

/**
 * @author knight
 * create at 20-3-11 下午8:36
 * 极简版事件Adapter
 * https://www.jianshu.com/p/043b1c069868 吸顶分类 明日找个最终方案实现一下
 */
class MiniEventRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val events: MutableList<Event> = mutableListOf()
    // 记录以显示的title
    private val title: MutableList<String> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemViewType(position: Int): Int {

        return super.getItemViewType(position)
    }

    companion object {
        const val VIEW_TYPE_TITLE = 0
        const val VIEW_TYPE_CONTENT = 1
    }

}