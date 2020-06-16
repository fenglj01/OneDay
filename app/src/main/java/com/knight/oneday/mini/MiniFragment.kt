package com.knight.oneday.mini

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.knight.oneday.R
import com.knight.oneday.adapters.ExpandableHomeItemAdapter
import com.knight.oneday.data.Task
import com.knight.oneday.data.Step
import com.knight.oneday.databinding.FragmentMiniBinding
import com.knight.oneday.setting.SettingPreferences
import com.knight.oneday.utilities.*
import com.knight.oneday.views.SectionDecoration
import com.knight.oneday.views.SureDeleteDialog
import com.knight.oneday.views.swipe.ReboundingSwipeActionCallback

/**
 * @author knight
 * create at 20-3-9 下午7:21
 * 极简风格的主页
 */
class MiniFragment : Fragment(), ExpandableHomeItemAdapter.EventItemListener,
    SharedPreferences.OnSharedPreferenceChangeListener, SectionDecoration.SectionCallback {

    private val miniVm: MiniViewModel by viewModels {
        InjectorUtils.eventWithStepViewModelFactory(
            requireContext()
        )
    }
    private lateinit var binding: FragmentMiniBinding
    private val adapter: ExpandableHomeItemAdapter by lazy {
        ExpandableHomeItemAdapter(
            this,
            binding.rvEvent
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMiniBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = miniVm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initEventRecyclerView()
        subscribeUi()

    }

    private fun initEventRecyclerView() {
        ItemTouchHelper(ReboundingSwipeActionCallback()).apply {
            attachToRecyclerView(binding.rvEvent)
        }
//        binding.rvEvent.addItemDecoration(SectionDecoration(requireContext(), this@MiniFragment))
        binding.rvEvent.adapter = adapter
    }

    private fun subscribeUi() {
        miniVm.eventList.observe(viewLifecycleOwner) { listEvents ->
            adapter.submitList(listEvents) {
                /*用于currentListChange中计算出变化的位置后 进行滚动*/
                if (adapter.currentChangedItemIndex in listEvents.indices) {
                    binding.rvEvent.smoothScrollToPosition(adapter.currentChangedItemIndex)
                    adapter.refreshCurrentChangeItemIndex()
                }
            }
        }
    }

    private fun initListeners() {
        PreferenceManager.getDefaultSharedPreferences(context)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == PreferenceAttrProxy.KEY_SHOW_ALLOW_FINISHED) miniVm.refreshList()
    }

    override fun getSectionContent(dataPosition: Int): String {
        if (dataPosition !in adapter.currentList.indices) return ""
        adapter.currentList[dataPosition].task.let { event ->
            return when {
                event.isDone -> getString(R.string.section_finished)
                event.isExpired() -> getString(R.string.section_expired)
                else -> getString(R.string.section_plan)
            }
        }
    }

    override fun onEventDoneChanged(task: Task, isDone: Boolean) {
        miniVm.updateEventDoneStatus(task, isDone)
    }

    override fun onEventLongPressed(task: Task) {
        if (SettingPreferences.showRemindDelete) {
            SureDeleteDialog().apply {
                onSure = {
                    miniVm.removeEvent(task)
                }
            }.show(requireActivity().supportFragmentManager, DIALOG_TAG_DELETE_EVENT)
        } else {
            miniVm.removeEvent(task)
        }

    }

    override fun onEventClicked(task: Task) {

    }

    override fun onStepIbClicked(step: Step, isDone: Boolean) {
        miniVm.updateStepState(isDone, step.stepId)
    }
}
