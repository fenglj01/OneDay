package com.knight.oneday.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.knight.oneday.R
import com.knight.oneday.setting.SettingPreferences
import com.knight.oneday.utilities.alphaAnimationShow
import com.ramotion.foldingcell.animations.AnimationEndListener
import kotlinx.android.synthetic.main.fragment_start.*

/**
 * A simple [Fragment] subclass.
 */
class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val showAnimation = AlphaAnimation(0f, 1f)
        showAnimation.duration = 800
        showAnimation.interpolator = AccelerateInterpolator()
        showAnimation.setAnimationListener(object : AnimationEndListener() {
            override fun onAnimationEnd(animation: Animation?) {
                findNavController().navigate(
                    if (SettingPreferences.isFirstInstall)
                        R.id.action_startFragment_to_welcomeFragment
                    else
                        R.id.action_startFragment_to_taskFragment
                )
            }
        })
        icon.startAnimation(showAnimation)
    }

}
