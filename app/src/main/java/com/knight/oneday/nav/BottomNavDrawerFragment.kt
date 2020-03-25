package com.knight.oneday.nav

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.shape.MaterialShapeDrawable
import com.knight.oneday.R
import com.knight.oneday.databinding.FragmentBottomNavDrawerBinding
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.views.themeColor
import com.knight.oneday.views.themeInterpolator

class BottomNavDrawerFragment : Fragment() {

    /**
     * 底部弹出框状态
     */
    enum class SandwichState {
        OPEN,
        CLOSE,
        SETTLING
    }

    private lateinit var binding: FragmentBottomNavDrawerBinding
    // 角标相关 状态 动画 插值器
    private var sandwichState: SandwichState = SandwichState.CLOSE
    private var sandwichAnim: ValueAnimator? = null
    private val sandwichInterp by lazy(LazyThreadSafetyMode.NONE) {
        requireContext().themeInterpolator(R.attr.motionInterpolatorPersistent)
    }
    private val behavior: BottomSheetBehavior<FrameLayout> by lazy(LazyThreadSafetyMode.NONE) {
        BottomSheetBehavior.from(binding.backgroundContainer)
    }

    // 背景形状
    private val backgroundShapeDrawable: MaterialShapeDrawable by lazy(LazyThreadSafetyMode.NONE) {
        val backgroundContext = binding.backgroundContainer.context
        MaterialShapeDrawable(
            backgroundContext,
            null,
            R.attr.bottomSheetStyle,
            0
        ).apply {
            fillColor = ColorStateList.valueOf(
                backgroundContext.themeColor(R.attr.colorPrimarySurfaceVariant)
            )
            elevation = resources.getDimension(R.dimen.dp_8)
            initializeElevationOverlay(requireContext())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomNavDrawerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            backgroundContainer.background = backgroundShapeDrawable
            // 屏幕view 点击隐藏 (达到点击drawer外部点击消失？)
            scrimView.singleClick { close() }
            // 一系列的监听操作

            behavior.state = STATE_HIDDEN

        }
    }

    private fun close() {

    }


}