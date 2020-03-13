package com.knight.oneday.adapters.holders

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.R
import com.knight.oneday.data.Event
import java.util.*

/**
 * @author knight
 * create at 20-3-11 下午8:39
 * 极简风格下的EventViewHolder
 */
class MiniEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    ViewHolderBind<Event> {


    override fun bindData(data: Event) {
        itemView.findViewById<AppCompatTextView>(R.id.eventContent).text =
            "${data.content} - ${data.createTime[Calendar.DAY_OF_MONTH]}"
    }
}

