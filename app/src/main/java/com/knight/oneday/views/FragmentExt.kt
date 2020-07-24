package com.knight.oneday.views

import android.content.pm.PackageManager
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBar(
    view: View,
    @StringRes strId: Int,
    duration: Int = Snackbar.LENGTH_SHORT,
    @StringRes actionText: Int? = null,
    action: (() -> Unit)? = null
) {
    val sb = Snackbar.make(view, strId, duration)
    actionText?.run {
        sb.setAction(actionText) {
            action?.invoke()
        }
    }
    sb.show()
}

fun Fragment.showSnackBar(
    view: View,
    str: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    @StringRes actionText: Int? = null,
    action: (() -> Unit)? = null
) {
    val sb = Snackbar.make(view, str, duration)
    actionText?.run {
        sb.setAction(actionText) {
            action?.invoke()
        }
    }
    sb.show()
}
