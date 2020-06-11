package com.knight.oneday.add

import android.os.Bundle
import android.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.BarUtils
import com.google.android.material.transition.MaterialContainerTransform
import com.knight.oneday.R
import com.knight.oneday.create.CreateEventFragment
import com.knight.oneday.databinding.FragmentAddEventBinding
import com.knight.oneday.utilities.getInputManagerService
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.views.themeInterpolator

/**
 * Create by FLJ in 2020/6/11 9:30
 * 创建事件 思考过后 化繁为简 暂时不要加入步骤 清单这样的功能了
 */
class AddEventFragment : Fragment() {

    private lateinit var binding: FragmentAddEventBinding

    companion object {

        const val HIDE_SOFT_INPUT_TAG = 2020

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startTransitions()
        initEvent()
    }

    private fun initEvent() {
        binding.apply {
            addBackIc.singleClick {
                hideSoftInput()
                findNavController().navigateUp()
            }
        }
    }

    private fun hideSoftInput() {
        getInputManagerService().hideSoftInputFromWindow(
            binding.addEventContent.windowToken,
            HIDE_SOFT_INPUT_TAG
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareTransitions()
    }

    private fun prepareTransitions() {
        postponeEnterTransition()
    }

    private fun startTransitions() {
        binding.executePendingBindings()

        enterTransition = MaterialContainerTransform(requireContext()).apply {
            startView = requireActivity().findViewById(R.id.fab)
            endView = binding.addEventContent
            duration = resources.getInteger(R.integer.one_day_motion_default_large).toLong()
            interpolator = requireContext().themeInterpolator(R.attr.motionInterpolatorPersistent)
        }

        returnTransition = Slide().apply {
            duration = resources.getInteger(R.integer.one_day_motion_duration_medium).toLong()
            interpolator = requireContext().themeInterpolator(R.attr.motionInterpolatorOutgoing)
        }

        startPostponedEnterTransition()
    }


}