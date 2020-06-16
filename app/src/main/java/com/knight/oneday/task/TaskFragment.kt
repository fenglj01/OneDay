package com.knight.oneday.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.knight.oneday.R
import com.knight.oneday.adapters.ExpandableHomeItemAdapter
import com.knight.oneday.adapters.TagPickerAdapter
import com.knight.oneday.calendar.CalendarToolView
import com.knight.oneday.data.Step
import com.knight.oneday.data.Task
import com.knight.oneday.data.TaskAndEventSteps
import com.knight.oneday.databinding.FragmentTaskBinding
import com.knight.oneday.nav.NavigationModel
import com.knight.oneday.utilities.formatWeekMonthDay

/**
 * Create by FLJ in 2020/6/11 9:28
 * 主页 在参阅一些好的手机后准备重构  ming天开搞
 */
class TaskFragment : Fragment(), CalendarView.OnCalendarSelectListener {

    private lateinit var binding: FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        binding.vm = TaskViewModel()
        binding.calendarSelectedListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendar()

    }

    private fun initCalendar() {
        binding.taskCurrentDate.onCurrentDayClicked =
            object : CalendarToolView.OnCurrentDayClicked {
                override fun onCurrentDayClicked() {
                    binding.taskCalendarView.scrollToCurrent()
                }
            }
        binding.taskList.layoutManager = LinearLayoutManager(requireContext())
        val ad = ExpandableHomeItemAdapter(object : ExpandableHomeItemAdapter.EventItemListener {
            override fun onEventDoneChanged(task: Task, isDone: Boolean) {

            }

            override fun onEventLongPressed(task: Task) {
            }

            override fun onEventClicked(task: Task) {
            }

            override fun onStepIbClicked(step: Step, isDone: Boolean) {
            }
        }, binding.taskList)
        binding.taskList.adapter = ad
        ad.submitList(
            mutableListOf(
                TaskAndEventSteps(
                    task = Task("aa")
                ),
                TaskAndEventSteps(
                    task = Task("aa")
                ), TaskAndEventSteps(
                    task = Task("aa")
                ),
                TaskAndEventSteps(
                    task = Task("aa")
                ),
                TaskAndEventSteps(
                    task = Task("aa")
                ),
                TaskAndEventSteps(
                    task = Task("aa")
                ), TaskAndEventSteps(
                    task = Task("aa")
                ), TaskAndEventSteps(
                    task = Task("aa")
                )
            )
        )
    }

    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
        binding.taskCurrentDate.changeSelectedDay(
            calendar?.timeInMillis?.formatWeekMonthDay() ?: ""
        )
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
    }
}