package com.knight.oneday.nav

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Create by FLJ in 2020/3/26 13:21
 * 监听navDrawer得状态变换
 */
interface OnStateChangedAction {
    fun onStateChanged(sheet: View, newState: Int)
}

/**
 * 根据navDrawer得变化 修改fab得状态
 */
class ShowHideFabStateAction(
    private val fab: FloatingActionButton
) : OnStateChangedAction {
    override fun onStateChanged(sheet: View, newState: Int) {
        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
            fab.show()
        } else {
            fab.hide()
        }
    }
}