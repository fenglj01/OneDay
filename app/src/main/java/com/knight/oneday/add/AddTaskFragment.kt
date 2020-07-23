package com.knight.oneday.add

import android.app.Activity
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.google.android.material.transition.MaterialContainerTransform
import com.knight.oneday.R
import com.knight.oneday.adapters.TagPickerAdapter
import com.knight.oneday.databinding.FragmentAddEventBinding
import com.knight.oneday.nav.NavigationModel
import com.knight.oneday.utilities.*
import com.knight.oneday.views.RemindMeView
import com.knight.oneday.views.choice.ChoiceInputView
import com.knight.oneday.views.dialog.DatePickerDialog
import com.knight.oneday.views.dialog.TimePickerDialog
import com.knight.oneday.views.showSnackBar
import com.knight.oneday.views.themeInterpolator
import com.ramotion.directselect.DSListView

/**
 * Create by FLJ in 2020/6/11 9:30
 * 创建事件 思考过后 化繁为简 暂时不要加入步骤 清单这样的功能了 交给binding 减少代码
 */
class AddTaskFragment : Fragment(), ChoiceInputView.OnChoiceInputClicked,
    DSListView.OnDSListViewStatusChangedListener, RemindMeView.OnRemindStatusChangedListener {

    private lateinit var binding: FragmentAddEventBinding
    private val timePicker: TimePickerDialog by lazy { TimePickerDialog(obtainTimePickerListener()) }
    private val datePicker: DatePickerDialog by lazy { DatePickerDialog(obtainDatePickerListener()) }

    private val addVM by viewModels<AddTaskViewModel> {
        InjectorUtils.addEventViewModelFactory(
            requireContext()
        )
    }

    companion object {

        const val HIDE_SOFT_INPUT_TAG = 2020

        const val TAG_TIME_PICKER_DIALOG = "time_picker_dialog"
        const val TAG_DATE_PICKER_DIALOG = "date_picker_dialog"

        const val ARG_SELECTED_CALENDAR = "arg_selected_calendar"

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
        binding.onRemindStatusChangedListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startTransitions()
        initView()
        initEvent()
    }

    private fun initView() {

        val safeArgs: AddTaskFragmentArgs by navArgs()

        safeArgs.task?.let { task ->
            binding.addTitleTv.text = getString(R.string.title_edit_task)
            binding.addTagIs.changeSelectedIndex(TaskType.values().indexOf(task.taskType))
            binding.addNameEdit.setText(task.content)
            binding.addDateCiv.setContentText(task.dueDateTime.timeInMillis.formatWeekMonthDay())
            binding.addTimeCiv.setContentText(task.dueDateTime.timeInMillis.formatHourMin())
            binding.addBtn.setText(R.string.btn_edit_task)
            binding.addTagDsl.selectedIndex = TaskType.values().indexOf(task.taskType)
            addVM.initViewModelByTask(task)
        }

        safeArgs.date?.let { sysCalendar ->
            /* 传递当前选择的日期 */
            datePicker.arguments = Bundle().apply {
                putSerializable(ARG_SELECTED_CALENDAR, sysCalendar)
            }
            binding.addDateCiv.setContentText(sysCalendar.timeInMillis.formatWeekMonthDay())
        }

        safeArgs.category.let { taskType ->
            binding.addTagDsl.selectedIndex = TaskType.values().indexOf(taskType)
        }

    }

    private fun initEvent() {
        addVM.viewModelStatus.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                AddTaskViewModel.ADD_STATUS_SUCCESS, AddTaskViewModel.EDIT_STATUS_SUCCESS -> {
                    findNavController().navigateUp()
                }
                AddTaskViewModel.ADD_STATUS_CONTENT_IS_EMPTY, AddTaskViewModel.EDIT_STATUS_CONTENT_IS_EMPTY -> {
                    showSnackBar(binding.root, R.string.add_event_content_is_empty)
                }
                AddTaskViewModel.ADD_STATUS_FAIL -> {
                    showSnackBar(binding.root, R.string.add_event_fail)
                }
                AddTaskViewModel.EDIT_STATUS_FAIL -> {
                    showSnackBar(binding.root, R.string.edit_task_fail)
                }
                AddTaskViewModel.EDIT_STATUS_NOTHING_CHANGED -> {
                    findNavController().navigateUp()
                }
            }
        })
        binding.apply {
            addBackIc.singleClick {
                hideSoftInput()
                findNavController().navigateUp()
            }
            addBtn.singleClick {
                hideSoftInput()
                addVM.confirm()
            }
            addNameEdit.addTextChangedListener { content ->
                content?.let { addVM.vmTaskContent = content.toString() }
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
                datePicker.show(requireActivity().supportFragmentManager, TAG_TIME_PICKER_DIALOG)
            }
            1 -> {
                timePicker.show(requireActivity().supportFragmentManager, TAG_DATE_PICKER_DIALOG)
            }
        }
    }

    override fun onShow() {
        setContentSplitMotionEventEnable(true)
        hideSoftInput()
        binding.addBtn.alphaAnimationHide()
    }

    override fun onHide() {
        setContentSplitMotionEventEnable(false)
        addVM.changeEventType(binding.addTagDsl.selectedIndex)
        binding.addTagIs.changeSelectedIndex(binding.addTagDsl.selectedIndex)
        binding.addBtn.alphaAnimationShow()
    }

    private fun setContentSplitMotionEventEnable(isShowDSListView: Boolean) {
        val enable = !isShowDSListView
        // 灵活的处理整个布局的多点触控问题
        binding.addEventContent.isMotionEventSplittingEnabled = enable
    }

    private fun obtainTimePickerListener(): TimePickerDialog.TimePickerListener =
        object : TimePickerDialog.TimePickerConfirmListener() {
            override fun onTimeConfirm(hourOfDay: Int, minute: Int) {
                addVM.changeHourAndMinute(hourOfDay, minute)
                binding.addTimeCiv.setContentText(addVM.prepareHourMinStr(hourOfDay, minute))
            }
        }

    private fun obtainDatePickerListener(): DatePickerDialog.OnDatePickerListener =
        object : DatePickerDialog.OnDatePickerConfirmListener() {
            override fun onDateConfirm(timeInMills: Long, year: Int, month: Int, day: Int) {
                addVM.changeDate(year, month, day)
                binding.addDateCiv.setContentText(timeInMills.formatWeekMonthDay())
            }
        }

    override fun onRemindStatusChanged(isRemind: Boolean) {
        if (isRemind) {
            checkCalendarPermission()
        }
    }

    private fun checkCalendarPermission() {

    }
}