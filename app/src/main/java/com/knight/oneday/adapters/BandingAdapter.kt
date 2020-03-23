package com.knight.oneday.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.knight.oneday.R
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.EventType

@BindingAdapter("app:eventStateIcon")
fun eventStateIcon(iv: ImageView, state: EventState) {
    val resId =
        if (state == EventState.FINISHED) R.drawable.ic_finished else R.drawable.ic_unfinished
    iv.setImageDrawable(iv.context.getDrawable(resId))
}

@BindingAdapter("app:eventIsImportantIcon")
fun eventIsImportantIcon(iv: ImageView, eventType: EventType) {
    if (eventType == EventType.IMPORTANT || eventType == EventType.MINI_IMPORTANT) {
        iv.setImageDrawable(iv.context.getDrawable(R.drawable.ic_important_topic_accent))
    } else {
        iv.visibility = View.GONE
    }
}

@BindingAdapter("app:addEventIsImportant")
fun addEventIsImportant(iv: ImageView, eventType: EventType) {
    if (eventType == EventType.IMPORTANT || eventType == EventType.MINI_IMPORTANT) {
        iv.setImageDrawable(iv.context.getDrawable(R.drawable.ic_important_topic_accent))
    } else {
        iv.setImageDrawable(iv.context.getDrawable(R.drawable.ic_important_topic))
    }
}

@BindingAdapter("app:canSendIcon")
fun canSendIcon(iv: ImageView, content: String) {
    val resId =
        if (content.isEmpty()) R.drawable.ic_cannot_send else R.drawable.ic_send
    iv.setImageDrawable(iv.context.getDrawable(resId))
}


@BindingAdapter("app:textColorByState")
fun textColorByState(tv: TextView, eventState: EventState) {
    with(tv.context.resources) {
        val textColor =
            if (eventState == EventState.FINISHED) getColor(R.color.color_9D9D9D) else getColor(
                R.color.color_fff
            )
        tv.setTextColor(textColor)
    }

}
