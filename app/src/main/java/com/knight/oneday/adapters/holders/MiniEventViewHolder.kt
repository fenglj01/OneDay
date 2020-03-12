package com.knight.oneday.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.data.Event

/**
 * @author knight
 * create at 20-3-11 下午8:39
 * 极简风格下的EventViewHolder
 */
class MiniEventViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView),
    ViewHolderBind<String> {
    override fun bindData(data: String) {

    }
}

