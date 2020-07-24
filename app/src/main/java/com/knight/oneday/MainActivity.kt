package com.knight.oneday

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.google.android.gms.ads.AdRequest
import com.knight.oneday.databinding.ActivityMainBinding
import com.knight.oneday.nav.*
import com.knight.oneday.utilities.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.LazyThreadSafetyMode.NONE


class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener,
    Toolbar.OnMenuItemClickListener {

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
                bottomNavDrawer.appJumpStrategy.jumpByFloatingBar()
            }
        }
        bottomNavDrawer.apply {
            // 控制drawer按钮的旋转 控制title的显示 逆时针旋转
            addOnSlideAction(HalfClockwiseRotateSlideAction(binding.bottomAppBarChevron))
            addOnSlideAction(AlphaSlideAction(binding.bottomAppBarTitle, true))
            // fab 跟随navDrawer来确定是否显示
            addOnStateChangedAction(ShowHideFabStateAction(binding.fab, navController))
            // 更改主题切换按钮
            addOnStateChangedAction(ChangeUiModeMenuStateAction { showUiMode ->
                changeBottomMenu(showUiMode)
            })
            // 这个作用不知道是什么
            //addOnSandwichSlideAction(HalfCounterClockwiseRotateSlideAction(binding.bottomAppBarChevron))
        }
        binding.bottomAppBar.setOnMenuItemClickListener(this)
        binding.bottomAppBarContentContainer.singleClick {
            bottomNavDrawer.toggle()
        }
        changeBottomMenu(false)
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


    private fun showUiModeChangeDialog() {
        MenuBottomSheetDialogFragment(R.menu.dark_theme_bottom_sheet_menu) {
            onDarkThemeMenuItemSelected(it.itemId)
        }.show(supportFragmentManager, null)
    }

    private fun onDarkThemeMenuItemSelected(id: Int): Boolean {
        val theme = when (id) {
            R.id.menu_light -> ThemePreference.PREFERENCE_THEME_ARRAY[0]
            R.id.menu_dark -> ThemePreference.PREFERENCE_THEME_ARRAY[1]
            else -> ThemePreference.PREFERENCE_THEME_ARRAY[0]
        }
        if (theme != (OneDayApp.instance() as OneDayApp).themePreference.uiModeLive.value) {
            (OneDayApp.instance() as OneDayApp).themePreference.changeUiMode(theme)
            return true
        }
        return false
    }


    private fun getBottomAppBarMenuForDestination(): Int = R.menu.bottom_app_bar_menu_search

    /**
     * 监听主题切换状态
     */
    @SuppressLint("WrongConstant")
    private fun observeUiMode() {
        (OneDayApp.instance() as OneDayApp).themePreference.uiModeLive.observe(this) { uiMode ->
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
        BottomNavDrawerFragment.navTag.observe(this, Observer {
            if (navController.currentDestination?.id == R.id.categoryFragment) {
                binding.bottomAppBarTitle.text = getString(getAppBarTitleByTaskType(it))
                binding.bottomAppBarIcon.setImageResource(getAppBarIconByTaskType(it))
            }
        })
    }

    private fun setBottomAppBarAndFab(id: Int) {
        when (id) {
            R.id.taskFragment -> {
                binding.bottomAppBar.isVisible = true
                bottomNavDrawer.close()
                setBottomAppBarAndFabByTaskFrag()
            }
            R.id.addTaskFragment -> {
                hideBottomAppBarAndFab()
            }
            R.id.settingFragment -> {
                bottomNavDrawer.close()
                hideBottomAppBarAndFab()
            }
            R.id.categoryFragment -> {
                bottomNavDrawer.close()
                showBottomAppBarAndFab()
            }
            R.id.searchFragment -> {
                hideBottomAppBarAndFab()
            }
            R.id.aboutFragment -> {
                bottomNavDrawer.close()
                hideBottomAppBarAndFab()

            }
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        setBottomAppBarAndFab(destination.id)
    }

    private fun setBottomAppBarAndFabByTaskFrag() {
        binding.run {
            bottomAppBarIcon.setImageResource(R.mipmap.icon_round)
            bottomAppBarTitle.text = ""
            bottomAppBar.performShow()
            fab.show()
        }
        NavigationModel.setNavigationMenuItemChecked(0)
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
            bottomAppBar.isVisible = false
            fab.isVisible = false
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_settings -> {
                bottomNavDrawer.close()
                showUiModeChangeDialog()
            }
            R.id.menu_search -> {
                bottomNavDrawer.close()
                bottomNavDrawer.appJumpStrategy.jumpSearch()
            }
        }
        return true
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.taskFragment -> finish()
        }
        super.onBackPressed()

    }
}
