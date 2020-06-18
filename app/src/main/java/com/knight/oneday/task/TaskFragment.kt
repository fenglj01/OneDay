package com.knight.oneday.task

import android.os.Bundle
import android.util.Log
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
class TaskFragment : Fragment(), CalendarView.OnCalendarSelectListener,
    TaskAdapter.TaskEventListener {

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
        binding.taskCalendarLayout.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY < 0) {

            } else {

            }
        }
        val taskAdapter = TaskAdapter(this)
        binding.taskList.layoutManager = LinearLayoutManager(requireContext())
        binding.taskList.adapter = taskAdapter
        taskAdapter.submitList(
            listOf(
                Task(
                    content = "我是CokeYY,我喜欢创造易用而美的软件"
                ),
                Task(
                    content = "i am uzi,i can play Ad Carry"
                ),
                Task(
                    content = "c"
                ),
                Task(
                    content = "d"
                ),
                Task(
                    content = "e"
                ),
                Task(
                    content = "f"
                ),
                Task(
                    content = "g"
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

    override fun onTaskClicked(task: Task) {
        Log.d("task", "click $task")
    }

    override fun onTaskLongClicked(task: Task): Boolean {
        Log.d("task", "longClick $task")
        return true
    }

    override fun onTaskStatusChanged(task: Task, isDone: Boolean) {
    }
}