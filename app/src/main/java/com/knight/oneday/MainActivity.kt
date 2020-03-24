package com.knight.oneday

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.knight.oneday.databinding.ActivityMainBinding
import com.knight.oneday.utilities.InjectorUtils
import com.knight.oneday.utilities.contentView
import com.knight.oneday.viewmodels.DayEventAndStepViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: DayEventAndStepViewModel by lazy {
        InjectorUtils.dayEventAndStepViewModelFactory(this)
    }
    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {

        }
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


}
