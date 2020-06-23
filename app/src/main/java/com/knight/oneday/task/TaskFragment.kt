package com.knight.oneday.task

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
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
import com.knight.oneday.utilities.InjectorUtils
import com.knight.oneday.utilities.UiCalendar
import com.knight.oneday.utilities.UiScheme
import com.knight.oneday.utilities.formatWeekMonthDay
import com.knight.oneday.views.choice.OneDayMonthView
import com.knight.oneday.views.choice.OneDayWeekView
import com.knight.oneday.views.swipe.ReboundingSwipeActionCallback
import kotlinx.android.synthetic.main.dialog_date_time_chioce.*

/**
 * Create by FLJ in 2020/6/11 9:28
 * 主页 在参阅一些好的设计后准备重构
 */
class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private lateinit var uiPresenter: TaskUiPresenter
    private lateinit var taskAdapter: TaskAdapter

    private val taskVm: TaskViewModel by viewModels {
        InjectorUtils.taskViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiPresenter = TaskUiPresenter(taskVm, binding, requireActivity())
        binding.vm = taskVm
        binding.calendarSelectedListener = uiPresenter

        initCalendar()

        initRecyclerView()

        observerLiveData()

        taskVm.refreshTaskList()
    }

    private fun observerLiveData() {
        taskVm.taskList.observe(viewLifecycleOwner, Observer {
            taskAdapter.submitList(it)
        })
        taskVm.taskMonthScheme.observe(viewLifecycleOwner, Observer {
             binding.taskCalendarView.setSchemeDate(it)
             Log.d("TaskViewModel", "${it.size} $it")
        })
    }

    private fun initRecyclerView() {
        ItemTouchHelper(ReboundingSwipeActionCallback()).apply {
            attachToRecyclerView(binding.taskList)
        }
        taskAdapter = TaskAdapter(uiPresenter)
        binding.taskList.adapter = taskAdapter
    }

    private fun initCalendar() {
        binding.taskCalendarView.setMonthView(OneDayMonthView::class.java)
        binding.taskCalendarView.setWeekView(OneDayWeekView::class.java)
        binding.taskCurrentDate.onCurrentDayClicked =
            object : CalendarToolView.OnCurrentDayClicked {
                override fun onCurrentDayClicked() {
                    binding.taskCalendarView.scrollToCurrent()
                }
            }
       /* val map = mutableMapOf<String, Calendar>()
        map.put(getUiCalendar().toString(), getUiCalendar())
        map.put(getUiCalendar().toString(), getUiCalendar())
        map.put(getUiCalendar().toString(), getUiCalendar())
        map.put(getUiCalendar().toString(), getUiCalendar())
        binding.taskCalendarView.setSchemeDate(map)*/

    }

    private fun getUiCalendar(): Calendar {
        val uiCalendar = Calendar()
        uiCalendar.year = binding.taskCalendarView.curYear
        uiCalendar.month = binding.taskCalendarView.curMonth
        uiCalendar.day = binding.taskCalendarView.curDay

        uiCalendar.schemeColor = Color.BLACK
        uiCalendar.scheme = "1"

        uiCalendar.addScheme(Calendar.Scheme(Color.RED, "1"))
        uiCalendar.addScheme(Calendar.Scheme(Color.GREEN, "2"))

        Log.d("TaskViewModel", "$uiCalendar")
        return uiCalendar
    }

}