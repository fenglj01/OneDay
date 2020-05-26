package com.knight.oneday.adapters

import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.knight.oneday.R
import com.knight.oneday.data.Step
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.EventType
import com.knight.oneday.views.getDrawableOrNull
import com.knight.oneday.views.step.*

@BindingAdapter("bindStepNumber")
fun StepNumberView.bindStepNumber(number: Int) {
    stepNumber = number
}

@BindingAdapter("srcDrawable")
fun ImageView.bindSrcDrawable(@DrawableRes drawableRes: Int? = null) {
    drawableRes?.let { setImageResource(it) }
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

@BindingAdapter(
    "paddingLeftSystemWindowInsets",
    "paddingTopSystemWindowInsets",
    "paddingRightSystemWindowInsets",
    "paddingBottomSystemWindowInsets",
    requireAll = false
)
fun View.applySystemWindowInsetsPadding(
    previousApplyLeft: Boolean,
    previousApplyTop: Boolean,
    previousApplyRight: Boolean,
    previousApplyBottom: Boolean,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean
) {
    if (previousApplyLeft == applyLeft &&
        previousApplyTop == applyTop &&
        previousApplyRight == applyRight &&
        previousApplyBottom == applyBottom
    ) {
        return
    }

    doOnApplyWindowInsets { view, insets, padding, _, _ ->
        val left = if (applyLeft) insets.systemWindowInsetLeft else 0
        val top = if (applyTop) insets.systemWindowInsetTop else 0
        val right = if (applyRight) insets.systemWindowInsetRight else 0
        val bottom = if (applyBottom) insets.systemWindowInsetBottom else 0

        view.setPadding(
            padding.left + left,
            padding.top + top,
            padding.right + right,
            padding.bottom + bottom
        )
    }
}

class InitialPadding(val left: Int, val top: Int, val right: Int, val bottom: Int)

class InitialMargin(val left: Int, val top: Int, val right: Int, val bottom: Int)

fun View.doOnApplyWindowInsets(
    block: (View, WindowInsets, InitialPadding, InitialMargin, Int) -> Unit
) {
    // Create a snapshot of the view's padding & margin states
    val initialPadding = recordInitialPaddingForView(this)
    val initialMargin = recordInitialMarginForView(this)
    val initialHeight = recordInitialHeightForView(this)
    // Set an actual OnApplyWindowInsetsListener which proxies to the given
    // lambda, also passing in the original padding & margin states
    setOnApplyWindowInsetsListener { v, insets ->
        block(v, insets, initialPadding, initialMargin, initialHeight)
        // Always return the insets, so that children can also use them
        insets
    }
    // request some insets
    requestApplyInsetsWhenAttached()
}

private fun recordInitialPaddingForView(view: View) = InitialPadding(
    view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom
)

private fun recordInitialMarginForView(view: View): InitialMargin {
    val lp = view.layoutParams as? ViewGroup.MarginLayoutParams
        ?: throw IllegalArgumentException("Invalid view layout params")
    return InitialMargin(lp.leftMargin, lp.topMargin, lp.rightMargin, lp.bottomMargin)
}

private fun recordInitialHeightForView(view: View): Int {
    return view.layoutParams.height
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        // We're already attached, just request as normal
        requestApplyInsets()
    } else {
        // We're not attached to the hierarchy, add a listener to
        // request when we are
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

@BindingAdapter("bindStepsOverView")
fun TextView.bindStepsOverView(steps: List<Step>) {
    val size = steps.size
    if (size == 0) {
        visibility = View.GONE
    } else {
        visibility = View.VISIBLE
        val nowStep = steps.first { it.state == EventState.UNFINISHED }
        text = "一共 $size 步 当前第 ${nowStep.serialNumber} 步: ${nowStep.content}"
    }
}

@BindingAdapter("bindStepItem")
fun StepView.bindStepItem(step: Step) {
    stepNumber = step.serialNumber
    stepStatus =
        if (step.state == EventState.UNFINISHED) STEP_STATE_UNFINISHED else STEP_STATE_FINISHED

}

@BindingAdapter("bindStepList")
fun StepListControlView.bindStepList(list: List<Step>) {
    setUpStepList(list)
}

