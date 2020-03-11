package com.knight.oneday.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.data.Event

/**
 * @author knight
 * create at 20-3-11 下午8:39
 * 极简风格下的EventViewHolder
 */
class EventTitleViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView),
    ViewHolderBind<String> {
    override fun bindData(data: String) {

    }
}

class EventContentViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView),
    ViewHolderBind<Event> {

    override fun bindData(data: Event) {

    }
}

