package com.knight.oneday.utilities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Rect
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Checkable
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton

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


fun getInputManager(activity: Activity): InputMethodManager =
    activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

/**
 * 监听输入键盘的显示和隐藏
 */
inline fun ViewGroup.addKeyBoardListener(
    crossinline onShow: () -> Unit,
    crossinline onHide: () -> Unit
) {
    viewTreeObserver.addOnGlobalLayoutListener {
        val rect = Rect()
        getWindowVisibleDisplayFrame(rect)
        val screenHeight = rootView.height
        val keypadHeight = screenHeight - rect.bottom
        if (keypadHeight > screenHeight * 0.15) {
            onShow.invoke()
        } else {
            onHide.invoke()
        }
    }
}

inline fun ViewGroup.addOnKeyBoardHidden(crossinline onHidden: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener {
        val rect = Rect()
        getWindowVisibleDisplayFrame(rect)
        val screenHeight = rootView.height
        val keypadHeight = screenHeight - rect.bottom
        if (keypadHeight < screenHeight * 0.15) {
            onHidden.invoke()
        }
    }
}


