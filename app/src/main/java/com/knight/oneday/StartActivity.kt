package com.knight.oneday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.preference.PreferenceManager
import com.knight.oneday.setting.SettingPreferences
import com.knight.oneday.utilities.ThemePreference
import com.knight.oneday.views.AnimationEndListener
import kotlinx.android.synthetic.main.activity_start.*

/**
 * Create by FLJ in 2020/7/14 11:03
 * 启动页
 */
class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start)

        startAnimation()
    }

    private fun startAnimation() {
        val showAnimation = AlphaAnimation(0f, 1f)
        showAnimation.duration = 1200
        showAnimation.interpolator = AccelerateInterpolator()
        showAnimation.setAnimationListener(object : AnimationEndListener() {
            override fun onAnimationEnd(animation: Animation?) {
                val intent = if (SettingPreferences.isFirstInstall) {
                    Intent(this@StartActivity, TutorialPageActivity::class.java)
                } else {
                    Intent(this@StartActivity, MainActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        })
        icon.startAnimation(showAnimation)
    }
}
