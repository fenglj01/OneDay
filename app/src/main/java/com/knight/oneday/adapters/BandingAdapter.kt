package com.knight.oneday.adapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.knight.oneday.R
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.EventType

@BindingAdapter("app:eventState")
fun eventState(iv: ImageView, state: EventState) {
    val resId =
        if (state == EventState.FINISHED) R.drawable.ic_finished else R.drawable.ic_unfinished
    iv.setImageDrawable(iv.context.getDrawable(resId))
}

@BindingAdapter("app:eventIsImportant")
fun eventIsImportant(iv: ImageView, eventType: EventType) {
    if (eventType == EventType.IMPORTANT || eventType == EventType.MINI_IMPORTANT) {
        iv.setImageDrawable(iv.context.getDrawable(R.drawable.ic_important_topic_accent))
    } else {
        iv.visibility = View.GONE
    }
}
