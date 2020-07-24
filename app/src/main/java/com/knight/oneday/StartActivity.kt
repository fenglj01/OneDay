package com.knight.oneday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.view.isVisible
import androidx.preference.PreferenceManager
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.TimeUtils
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
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

        initAds()

        startAnimation()

    }

    private fun startAnimation() {
        val showAnimation = AlphaAnimation(0f, 1f)
        showAnimation.duration = 2000
        showAnimation.interpolator = AccelerateInterpolator()
        showAnimation.setAnimationListener(object : AnimationEndListener() {
            override fun onAnimationEnd(animation: Animation?) {
                icon.isVisible = true
            }
        })
        icon.startAnimation(showAnimation)
    }

    private fun goNext() {
        val intent = if (SettingPreferences.isFirstInstall) {
            Intent(this@StartActivity, TutorialPageActivity::class.java)
        } else {
            Intent(this@StartActivity, MainActivity::class.java)
        }
        startActivity(intent)
        finish()
    }


    private fun initAds() {
        ad_view.run {
            val adRequest = AdRequest.Builder().build()
            loadAd(adRequest)
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    postDelayed({
                        goNext()
                    }, 2000L)
                }

                override fun onAdFailedToLoad(p0: Int) {
                    super.onAdFailedToLoad(p0)
                    postDelayed({
                        goNext()
                    }, 2000L)
                }
            }
        }
    }
}
