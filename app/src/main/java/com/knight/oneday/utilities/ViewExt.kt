package com.knight.oneday.utilities

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.Resources
import android.graphics.Paint
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.Checkable
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.knight.oneday.OneDayApp
import com.knight.oneday.views.AnimationEndListener

/**
 * @author knight
 * create at 20-3-9 下午8:36
 * View的一些扩展
 */

/* 防止重复点击 */
inline fun <T : View> T.singleClick(time: Long = 800, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}

fun <T : View> T.singleClick(onClickListener: View.OnClickListener, time: Long = 800) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            onClickListener.onClick(this)
        }
    }
}

var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0


fun Fragment.getInputManagerService(): InputMethodManager =
    requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager


val Float.dp: Float                 // [xxhdpi](360 -> 1080)
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics
    )

val Int.dp: Int
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()


val Float.sp: Float                 // [xxhdpi](360 -> 1080)
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics
    )


val Int.sp: Int
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

val Float.px: Float
    get() = dp2px(dp = this)

fun dp2px(context: Context = OneDayApp.instance(), dp: Float): Float =
    dp * context.resources.displayMetrics.density


fun px2dp(context: Context, px: Float): Float = px / context.resources.displayMetrics.density

fun sp2px(context: Context = OneDayApp.instance(), sp: Float): Float =
    sp * context.resources.displayMetrics.scaledDensity + 0.5f

fun px2sp(context: Context = OneDayApp.instance(), px: Float): Float =
    px / context.resources.displayMetrics.scaledDensity + 0.5f


fun TextView.strikeText() {
    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    invalidate()
}

fun TextView.clearStrikeText() {
    paintFlags = 0
    invalidate()
}

fun View.alphaAnimationShow() {
    val showAnimation = AlphaAnimation(0f, 1f)
    showAnimation.duration = 200
    showAnimation.interpolator = AccelerateInterpolator()
    showAnimation.setAnimationListener(object : AnimationEndListener() {
        override fun onAnimationEnd(animation: Animation?) {
            isVisible = true
        }
    })
    startAnimation(showAnimation)
}

fun View.alphaAnimationHide() {
    isVisible = false
    val hideAnimation = AlphaAnimation(1f, 0f)
    hideAnimation.duration = 200
    hideAnimation.interpolator = DecelerateInterpolator()
    hideAnimation.setAnimationListener(object : AnimationEndListener() {
        override fun onAnimationEnd(animation: Animation?) {
            isVisible = false
        }
    })
    startAnimation(hideAnimation)
}



