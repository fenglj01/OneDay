package com.knight.oneday.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.knight.oneday.R
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.EventType
import com.knight.oneday.views.getDrawableOrNull

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
            if (eventState == EventState.FINISHED) getColor(R.color.white_50) else getColor(
                R.color.black_800
            )
        tv.setTextColor(textColor)
    }

}

@BindingAdapter("layoutFullscreen")
fun View.bindLayoutFullscreen(previousFullscreen: Boolean, fullscreen: Boolean) {
    if (previousFullscreen != fullscreen && fullscreen) {
        systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}

@BindingAdapter(
    "drawableStart",
    "drawableLeft",
    "drawableTop",
    "drawableEnd",
    "drawableRight",
    "drawableBottom",
    requireAll = false
)
fun TextView.bindDrawables(
    @DrawableRes drawableStart: Int? = null,
    @DrawableRes drawableLeft: Int? = null,
    @DrawableRes drawableTop: Int? = null,
    @DrawableRes drawableEnd: Int? = null,
    @DrawableRes drawableRight: Int? = null,
    @DrawableRes drawableBottom: Int? = null
) {
    setCompoundDrawablesWithIntrinsicBounds(
        context.getDrawableOrNull(drawableStart ?: drawableLeft),
        context.getDrawableOrNull(drawableTop),
        context.getDrawableOrNull(drawableEnd ?: drawableRight),
        context.getDrawableOrNull(drawableBottom)
    )
}
