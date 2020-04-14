package com.knight.oneday.views

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBar(
    view: View,
    @StringRes strId: Int,
    duration: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(view, strId, duration).show()
}