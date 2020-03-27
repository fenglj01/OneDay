package com.knight.oneday

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.knight.oneday.databinding.ActivityMainBinding
import com.knight.oneday.nav.*
import com.knight.oneday.utilities.InjectorUtils
import com.knight.oneday.utilities.contentView
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.viewmodels.DayEventAndStepViewModel
import kotlin.LazyThreadSafetyMode.NONE

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var navController: NavController
    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)
    private val bottomNavDrawer: BottomNavDrawerFragment by lazy(NONE) {
        supportFragmentManager.findFragmentById(R.id.bottom_nav_drawer) as BottomNavDrawerFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavController()
        setUpBottomBarAndFab()
        observeUiMode()
    }

    private fun initNavController() {
        binding.run {
            navController = findNavController(R.id.nav_host_fragment)
            navController.addOnDestinationChangedListener(this@MainActivity)
        }
    }

    private fun setUpBottomBarAndFab() {
        // init fab
        binding.fab.apply {
            setShowMotionSpecResource(R.animator.fab_show)
            setHideMotionSpecResource(R.animator.fab_hide)
            singleClick {
                navController.navigate(R.id.action_miniFragment_to_createEventFragment)
            }
        }
        bottomNavDrawer.apply {
            // 控制drawer按钮的旋转 控制title的显示 逆时针旋转
            addOnSlideAction(HalfClockwiseRotateSlideAction(binding.bottomAppBarChevron))
            addOnSlideAction(AlphaSlideAction(binding.bottomAppBarTitle, true))
            // fab 跟随navDrawer来确定是否显示
            addOnStateChangedAction(ShowHideFabStateAction(binding.fab))
            // 更改主题切换按钮
            addOnStateChangedAction(ChangeUiModeMenuStateAction { showUiMode ->
                changeBottomMenu(showUiMode)
            })
            // 这个作用不知道是什么
            //addOnSandwichSlideAction(HalfCounterClockwiseRotateSlideAction(binding.bottomAppBarChevron))
        }
        binding.bottomAppBarContentContainer.singleClick {
            bottomNavDrawer.toggle()
        }
    }

    private fun changeBottomMenu(showUiMode: Boolean) {

        binding.bottomAppBar.replaceMenu(
            if (showUiMode) {
                R.menu.bottom_app_bar_menu_ui_mode
            } else {
                getBottomAppBarMenuForDestination()
            }
        )
    }

    private fun getBottomAppBarMenuForDestination(): Int = R.menu.bottom_app_bar_menu_search

    /**
     * 监听主题切换状态
     */
    private fun observeUiMode() {
        (OneDayApp.instance() as OneDayApp).themePreference.uiModeLive.observe(this) { uiMode ->
            Log.d("TAG_UiMode", "$uiMode")
            when (uiMode) {
                "Light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "Dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                "System" ->
                    if (Build.VERSION.SDK_INT >= 29)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    else
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            }
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.miniFragment -> {
                showBottomAppBarAndFab()
            }
            R.id.createEventFragment -> {
                hideBottomAppBarAndFab()
            }
        }
    }

    private fun showBottomAppBarAndFab() {
        binding.run {
            bottomAppBar.performShow()
            fab.show()
        }
    }

    private fun hideBottomAppBarAndFab() {
        binding.run {
            bottomAppBar.performHide()
            fab.hide()
        }
    }
}
