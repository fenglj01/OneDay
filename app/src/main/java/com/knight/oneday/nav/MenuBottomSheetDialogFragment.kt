package com.knight.oneday.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import com.knight.oneday.R

/**
 * Create by FLJ in 2020/3/25 10:53
 * 首页底部按钮群弹出
 */
class MenuBottomSheetDialogFragment(
    private val menuRes: Int,
    private val onNavigationItemSelected: (MenuItem) -> Boolean
) : BottomSheetDialogFragment() {
    private lateinit var navigationView: NavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.menu_bottom_sheet_dialog_layout,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView = view.findViewById(R.id.navigation_view)
        navigationView.inflateMenu(menuRes)
        navigationView.setNavigationItemSelectedListener {
            val consumed = onNavigationItemSelected(it)
            if (consumed) dismiss()
            consumed
        }
    }
}