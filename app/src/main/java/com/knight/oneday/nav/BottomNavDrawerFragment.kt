package com.knight.oneday.nav

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
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
import kotlin.math.abs

class BottomNavDrawerFragment : Fragment(), NavBottomAdapter.NavigationAdapterListener {

    /**
     * 底部弹出框状态
     */
    enum class SandwichState {
        OPEN,
        CLOSED,
        SETTLING
    }

    private lateinit var binding: FragmentBottomNavDrawerBinding
    // 角标相关 状态 动画 插值器
    private var sandwichState: SandwichState = SandwichState.CLOSED
    private var sandwichAnim: ValueAnimator? = null
    private val sandwichInterp by lazy(LazyThreadSafetyMode.NONE) {
        requireContext().themeInterpolator(R.attr.motionInterpolatorPersistent)
    }
    private val behavior: BottomSheetBehavior<FrameLayout> by lazy(LazyThreadSafetyMode.NONE) {
        BottomSheetBehavior.from(binding.backgroundContainer)
    }
    private val bottomSheetCallback = BottomNavigationDrawerCallback()

    private val sandwichSlideActions = mutableListOf<OnSandwichSlideAction>()

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
    // 前景
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
    // 返回键监听
    private val closeDrawerOnBackPressed = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            close()
        }
    }

    // 旋转 也代表着滑动
    private var sandwichProgress: Float = 0F
        set(value) {
            if (field != value) {
                sandwichSlideActions.forEach { it.onSlide(value) }
                val newState = when (value) {
                    0F -> SandwichState.CLOSED
                    1F -> SandwichState.OPEN
                    else -> SandwichState.SETTLING
                }
                sandwichState = newState
                field = value
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 在返回键触发时 将NavDrawer收回
        requireActivity().onBackPressedDispatcher.addCallback(this, closeDrawerOnBackPressed)
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
            bottomSheetCallback.apply {
                // scrimView的透明度 可见性监听
                addOnSlideAction(AlphaSlideAction(scrimView))
                addOnStateChangedAction(VisibilityStateAction(scrimView))
                // 使得返回键在drawer显示得时候拦截返回键 达到点击返回键后隐藏drawer再次点击则为退出app事件
                addOnStateChangedAction(object : OnStateChangedAction {
                    override fun onStateChanged(sheet: View, newState: Int) {
                        closeDrawerOnBackPressed.isEnabled = newState != STATE_HIDDEN
                    }
                })
                // recyclerView监听
                addOnStateChangedAction(ScrollToTopStateAction(navRecyclerView))
                // sandwich open close监听
                addOnStateChangedAction(object : OnStateChangedAction {
                    override fun onStateChanged(sheet: View, newState: Int) {
                        sandwichAnim?.cancel()
                        sandwichProgress = 0F
                    }
                })

            }
            behavior.addBottomSheetCallback(bottomSheetCallback)
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

    fun close() {
        behavior.state = STATE_HIDDEN
    }

    private fun open() {
        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun toggleSandwich() {
        val initialProgress = sandwichProgress
        val newProgress = when (sandwichState) {
            SandwichState.CLOSED -> {
                binding.backgroundContainer.setTag(
                    R.id.tag_view_top_snapshot,
                    binding.backgroundContainer.top
                )
                1F
            }
            SandwichState.OPEN -> 0F
            SandwichState.SETTLING -> return
        }
        sandwichAnim?.cancel()
        sandwichAnim = ValueAnimator.ofFloat(initialProgress, newProgress).apply {
            addUpdateListener { sandwichProgress = animatedValue as Float }
            interpolator = sandwichInterp
            duration = (abs(newProgress - initialProgress) *
                    resources.getInteger(R.integer.one_day_motion_duration_medium)).toLong()
        }
        sandwichAnim?.start()
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

    fun addOnSlideAction(action: OnSlideAction) {
        bottomSheetCallback.addOnSlideAction(action)
    }

    fun addOnStateChangedAction(action: OnStateChangedAction) {
        bottomSheetCallback.addOnStateChangedAction(action)
    }

    /**
     * Add actions to be run when the slide offset (animation progress) or the sandwiching account
     * picker has changed.
     */
    fun addOnSandwichSlideAction(action: OnSandwichSlideAction) {
        sandwichSlideActions.add(action)
    }

}