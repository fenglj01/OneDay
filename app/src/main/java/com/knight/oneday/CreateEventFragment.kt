package com.knight.oneday

import android.os.Bundle
import android.transition.Slide
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.transition.MaterialContainerTransform
import com.knight.oneday.adapters.TagPickerAdapterJava
import com.knight.oneday.databinding.FragmentCreateEventBinding
import com.knight.oneday.nav.NavigationModel
import com.knight.oneday.utilities.InjectorUtils
import com.knight.oneday.utilities.getInputManagerService
import com.knight.oneday.utilities.getMaterialDatePickerBuilder
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.viewmodels.CreateEventViewModel
import com.knight.oneday.views.themeInterpolator
import com.ramotion.directselect.DSListView

class CreateEventFragment : Fragment() {

    private lateinit var binding: FragmentCreateEventBinding

    private val createViewModel: CreateEventViewModel by viewModels {
        InjectorUtils.createEventViewModelFactory(
            requireContext()
        )
    }

    private val datePicker: MaterialDatePicker<*> by lazy {
        binding.root.getMaterialDatePickerBuilder().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareTransitions()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateEventBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = createViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDropMenu()
        initListener()
        startTransitions()
    }

    private fun initListener() {
        binding.apply {
            closeIv.singleClick {
                findNavController().navigateUp()
            }
            eventDateTv.singleClick {
                datePicker.show(parentFragmentManager, "")
            }
        }
        datePicker.addOnPositiveButtonClickListener {
            binding.eventDateTv.text = datePicker.headerText
            datePicker.selection
        }
    }

    /**
     * 下拉按钮 (输入内容后可以新建保存)
     */
    private fun initDropMenu() {
        val tagItems = NavigationModel.getNavTagItems()
        with(binding.eventTagPickerList) {

            setAdapter(
                TagPickerAdapterJava(
                    requireContext(),
                    R.layout.event_tag_cell_list_item_layout,
                    tagItems
                )
            )

            selectedIndex = 0

            setStatusChangedListener(object : DSListView.OnDSListViewStatusChangedListener {
                override fun onShow() {
                    setContentSplitMotionEventEnable(true)
                    hideSoftInputByDSListOnShow()
                }

                override fun onHide() {
                    setContentSplitMotionEventEnable(false)
                }
            })
        }
    }

    private fun setContentSplitMotionEventEnable(isShowDSListView: Boolean) {
        val enable = !isShowDSListView
        // 灵活的处理整个布局的多点触控问题
        binding.eventCreateContent.isMotionEventSplittingEnabled = enable
    }

    private fun hideSoftInputByDSListOnShow() {
        getInputManagerService().hideSoftInputFromWindow(
            binding.eventCreateContent.windowToken,
            HIDE_SOFT_INPUT_TAG
        )
    }

    private fun prepareTransitions() {
        postponeEnterTransition()
    }

    private fun startTransitions() {
        binding.executePendingBindings()

        enterTransition = MaterialContainerTransform(requireContext()).apply {
            startView = requireActivity().findViewById(R.id.fab)
            endView = binding.createEventCardView
            duration = resources.getInteger(R.integer.one_day_motion_default_large).toLong()
            interpolator = requireContext().themeInterpolator(R.attr.motionInterpolatorPersistent)
        }

        returnTransition = Slide().apply {
            duration = resources.getInteger(R.integer.one_day_motion_duration_medium).toLong()
            interpolator = requireContext().themeInterpolator(R.attr.motionInterpolatorOutgoing)
        }

        startPostponedEnterTransition()
    }

    companion object {
        const val HIDE_SOFT_INPUT_TAG = 0
    }

}
