package com.knight.oneday

import android.os.Bundle
import android.transition.Slide
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.knight.oneday.adapters.TagPickerAdapter
import com.knight.oneday.data.RemindDate
import com.knight.oneday.databinding.FragmentCreateEventBinding
import com.knight.oneday.nav.NavigationModel
import com.knight.oneday.utilities.*
import com.knight.oneday.viewmodels.CreateEventViewModel
import com.knight.oneday.views.DateTimeChoiceDialogFragment
import com.knight.oneday.views.OnButtonClickListener
import com.knight.oneday.views.showSnackBar
import com.knight.oneday.views.themeInterpolator
import com.ramotion.directselect.DSListView
import kotlinx.android.synthetic.main.dialog_date_time_chioce.*
import kotlinx.android.synthetic.main.fragment_create_event.*

class CreateEventFragment : Fragment() {

    private lateinit var binding: FragmentCreateEventBinding
    private val dateDialog: DateTimeChoiceDialogFragment by lazy { DateTimeChoiceDialogFragment() }

    private val createViewModel: CreateEventViewModel by viewModels {
        InjectorUtils.createEventViewModelFactory(
            requireContext()
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareTransitions()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
                hideSoftInput()
                findNavController().navigateUp()
            }

            eventDateTv.singleClick {
                dateDialog.show(parentFragmentManager, DATE_PICKER_TAG)
            }

            eventSendEditIb.setTriggerConditionByEditText(eventOverviewEdt) { editContentLength ->
                editContentLength > 1
            }

            eventSendEditIb.onButtonClickListener = object : OnButtonClickListener {

                override fun onStateOnForwardClick() {
                    showSnackBar(eventSendEditIb, R.string.event_overview_length_hint)
                }

                override fun onStateOnReserveClick() {
                    hideSoftInput()
                    createViewModel.eventContent = eventOverviewEdt.text.toString()
                    createViewModel.submitSteps(binding.createStepView.getStepContentList())
                    createViewModel.createEvent()
                }
            }
        }

        dateDialog.choiceCalendar.observe(viewLifecycleOwner, Observer { remindDate ->
            if (isShowRemindNotAllowExpiredEvent(remindDate)) {
                showSnackBar(
                    binding.root,
                    R.string.now_not_show_expried_event,
                    duration = Snackbar.LENGTH_LONG,
                    actionText = R.string.dont_remind_agin,
                    action = {
                        /* 选择了不再提示后 就不再展示出来 */
                        SettingPreferences.showRemindNotAllowExpired = false
                    }
                )
            }
            event_date_tv.text = remindDate.timeInMillis.formatYearMonthDay()
            createViewModel.eventRemindDate = remindDate.timeInMillis
        })

        createViewModel.viewModelStatus.observe(viewLifecycleOwner, Observer { viewModelStatus ->
            when (viewModelStatus) {
                VIEW_MODEL_STATUS_ON_FAIL -> showSnackBar(
                    binding.createEventCardView,
                    R.string.create_event_fail
                )
                VIEW_MODEL_STATUS_ON_SUCCESS -> findNavController().navigateUp()
                else -> {
                }
            }
        })

    }

    /**
     *  判断当前是否要展示给用户列表没有展示过期事件
     *  @param remindDate 提示时间
     * */
    private fun isShowRemindNotAllowExpiredEvent(remindDate: RemindDate): Boolean =
        remindDate.timeInMillis <= currentTimeMills() && SettingPreferences.showRemindNotAllowExpired

    private fun initDropMenu() {
        val tagItems = NavigationModel.getNavTagItems()
        with(binding.eventTagPickerList) {

            setAdapter(
                TagPickerAdapter(
                    requireContext(),
                    R.layout.event_tag_cell_list_item_layout,
                    tagItems
                )
            )

            selectedIndex = 0

            setStatusChangedListener(object : DSListView.OnDSListViewStatusChangedListener {
                override fun onShow() {
                    setContentSplitMotionEventEnable(true)
                    hideSoftInput()
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


    private fun hideSoftInput() {
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
        const val DATE_PICKER_TAG = "create_event_date_picker"
    }

}
