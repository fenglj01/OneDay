package com.knight.oneday.nav

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.shape.MaterialShapeDrawable
import com.knight.oneday.R
import com.knight.oneday.databinding.FragmentBottomNavDrawerBinding
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.views.themeColor
import com.knight.oneday.views.themeInterpolator

class BottomNavDrawerFragment : Fragment(), NavBottomAdapter.NavigationAdapterListener {

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

    private val foregroundShapeDrawable: MaterialShapeDrawable by lazy(LazyThreadSafetyMode.NONE) {
        val foregroundContext = binding.foregroundContainer.context
        MaterialShapeDrawable(
            foregroundContext,
            null,
            R.attr.bottomSheetStyle,
            0
        ).apply {
            fillColor = ColorStateList.valueOf(
                foregroundContext.themeColor(R.attr.colorPrimarySurface)
            )
            elevation = resources.getDimension(R.dimen.dp_16)
            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_NEVER
            initializeElevationOverlay(requireContext())
            shapeAppearanceModel = shapeAppearanceModel.toBuilder()
                .setTopEdge(
                    SemiCircleEdgeCutoutTreatment(
                        resources.getDimension(R.dimen.dp_8),
                        resources.getDimension(R.dimen.dp_24),
                        0F,
                        resources.getDimension(R.dimen.navigation_drawer_profile_image_size_padded)
                    )
                )
                .build()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomNavDrawerBinding.inflate(inflater, container, false)
        binding.foregroundContainer.setOnApplyWindowInsetsListener { view, windowInsets ->
            /*记录窗口的顶部插图，以便在底页向上滑动时可以应用迎合屏幕的顶部边缘*/
            view.setTag(
                R.id.tag_system_window_inset_top,
                windowInsets.systemWindowInsetTop
            )
            windowInsets
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            backgroundContainer.background = backgroundShapeDrawable
            foregroundContainer.background = foregroundShapeDrawable
            // 屏幕view 点击隐藏 (达到点击drawer外部点击消失？)
            scrimView.singleClick { close() }
            // 一系列的监听操作
            behavior.state = STATE_HIDDEN
        }
        initNavigationMenu()
    }

    private fun initNavigationMenu() {
        binding.run {
            val adapter = NavBottomAdapter(this@BottomNavDrawerFragment)
            navRecyclerView.adapter = adapter
            NavigationModel.navigationList.observe(this@BottomNavDrawerFragment) {
                adapter.submitList(it)
            }
            NavigationModel.setNavigationMenuItemChecked(0)
        }
    }

    private fun close() {
        behavior.state = STATE_HIDDEN
    }

    private fun open() {
        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun toggleSandwich() {

    }

    fun toggle() {
        when {
            sandwichState == SandwichState.OPEN -> toggleSandwich()
            behavior.state == STATE_HIDDEN -> open()
            behavior.state == STATE_HIDDEN
                    || behavior.state == BottomSheetBehavior.STATE_HALF_EXPANDED
                    || behavior.state == BottomSheetBehavior.STATE_EXPANDED
                    || behavior.state == BottomSheetBehavior.STATE_COLLAPSED -> close()
        }
    }

    override fun onNavMenuItemClicked(item: NavigationModelItem.NavMenuItem) {

    }

    override fun onNavEventTagClicked(folder: NavigationModelItem.NavEventTag) {
    }
}