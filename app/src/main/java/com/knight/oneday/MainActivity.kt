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
import com.knight.oneday.utilities.InjectorUtils
import com.knight.oneday.utilities.contentView
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.viewmodels.DayEventAndStepViewModel

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var navController: NavController
    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBottomBarAndFab()
        observeUiMode()
    }

    private fun setUpBottomBarAndFab() {
        binding.run {
            navController = findNavController(R.id.nav_host_fragment)
            navController.addOnDestinationChangedListener(this@MainActivity)
        }

        binding.fab.apply {
            singleClick {
                navController.navigate(R.id.action_miniFragment_to_createEventFragment)
            }
        }
    }


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
            }
            R.id.createEventFragment -> {
            }
        }
    }
}
