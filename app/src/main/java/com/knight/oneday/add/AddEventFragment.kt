package com.knight.oneday.add

import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.BarUtils
import com.google.android.material.transition.MaterialContainerTransform
import com.knight.oneday.R
import com.knight.oneday.adapters.TagPickerAdapter
import com.knight.oneday.create.CreateEventFragment
import com.knight.oneday.databinding.FragmentAddEventBinding
import com.knight.oneday.nav.NavigationModel
import com.knight.oneday.utilities.InjectorUtils
import com.knight.oneday.utilities.getInputManagerService
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.views.choice.ChoiceInputView
import com.knight.oneday.views.dialog.TimePickerDialog
import com.knight.oneday.views.themeInterpolator
import com.ramotion.directselect.DSListView

/**
 * Create by FLJ in 2020/6/11 9:30
 * 创建事件 思考过后 化繁为简 暂时不要加入步骤 清单这样的功能了 交给binding 减少代码
 */
class AddEventFragment : Fragment(), ChoiceInputView.OnChoiceInputClicked,
    DSListView.OnDSListViewStatusChangedListener {

    private lateinit var binding: FragmentAddEventBinding
    private val timePicker: TimePickerDialog by lazy { TimePickerDialog(obtainTimePickerListener()) }

    private val addVM by viewModels<AddEventViewModel> {
        InjectorUtils.addEventViewModelFactory(
            requireContext()
        )
    }

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
        binding.vm = addVM
        binding.onChoiceInputClicked = this
        binding.tagAdapter = TagPickerAdapter(
            requireContext(),
            R.layout.event_tag_cell_list_item_layout,
            NavigationModel.getNavTagItems()
        )
        binding.onDLStatusChangedListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startTransitions()
        initView()
        initEvent()
    }

    private fun initView() {
        binding.apply {

        }
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

    override fun onChoiceInputClicked(contentViewId: Int) {
        when (contentViewId) {
            0 -> {
            }
            1 -> {
                timePicker.show(requireActivity().supportFragmentManager, "")
            }
        }
    }

    override fun onShow() {
        setContentSplitMotionEventEnable(true)
        hideSoftInput()
    }

    override fun onHide() {
        setContentSplitMotionEventEnable(false)
    }

    private fun setContentSplitMotionEventEnable(isShowDSListView: Boolean) {
        val enable = !isShowDSListView
        // 灵活的处理整个布局的多点触控问题
        binding.addEventContent.isMotionEventSplittingEnabled = enable
    }

    private fun obtainTimePickerListener(): TimePickerDialog.TimePickerListener =
        object : TimePickerDialog.TimePickerConfirmListener() {
            override fun onTimeConfirm(hourOfDay: Int, minute: Int) {
                binding.addTimeCiv.setContentText(addVM.prepareHourMinStr(hourOfDay, minute))
            }
        }
}