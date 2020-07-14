package com.knight.oneday.nav

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.knight.oneday.R

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
    private val fab: FloatingActionButton,
    private val navController: NavController
) : OnStateChangedAction {
    override fun onStateChanged(sheet: View, newState: Int) {
        when (navController.currentDestination?.id) {
            R.id.settingFragment -> fab.hide()
            R.id.aboutFragment -> fab.hide()
            else -> {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    fab.show()
                } else {
                    fab.hide()
                }
            }
        }
    }
}

/**
 * 根据navDrawer的变化 修改可见度
 */
class VisibilityStateAction(
    private val view: View,
    private val reverse: Boolean = false
) : OnStateChangedAction {
    override fun onStateChanged(sheet: View, newState: Int) {
        val stateHiddenVisibility = if (!reverse) View.GONE else View.VISIBLE
        val stateDefaultVisibility = if (!reverse) View.VISIBLE else View.GONE
        when (newState) {
            BottomSheetBehavior.STATE_HIDDEN -> view.visibility = stateHiddenVisibility
            else -> view.visibility = stateDefaultVisibility
        }
    }
}

class ScrollToTopStateAction(
    private val recyclerView: RecyclerView
) : OnStateChangedAction {
    override fun onStateChanged(sheet: View, newState: Int) {
        if (newState == BottomSheetBehavior.STATE_HIDDEN) recyclerView.scrollToPosition(0)
    }
}

class ChangeUiModeMenuStateAction(
    private val onShouldShowUiModeMenu: (showUiMode: Boolean) -> Unit
) : OnStateChangedAction {

    private var hasCalledShowUiModeMenu: Boolean = false

    override fun onStateChanged(sheet: View, newState: Int) {
        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
            hasCalledShowUiModeMenu = false
            onShouldShowUiModeMenu(false)
        } else {
            if (!hasCalledShowUiModeMenu) {
                hasCalledShowUiModeMenu = true
                onShouldShowUiModeMenu(true)
            }
        }
    }
}
