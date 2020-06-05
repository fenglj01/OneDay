package com.knight.oneday

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import com.knight.oneday.adapters.ExpandableHomeItemAdapter
import com.knight.oneday.data.Event
import com.knight.oneday.data.EventAndEventSteps
import com.knight.oneday.databinding.FragmentMiniBinding
import com.knight.oneday.utilities.*
import com.knight.oneday.viewmodels.MiniViewModel
import com.knight.oneday.views.showSnackBar
import com.knight.oneday.views.swipe.ReboundingSwipeActionCallback
import java.time.LocalDateTime

/**
 * @author knight
 * create at 20-3-9 下午7:21
 * 极简风格的主页
 */
class MiniFragment : Fragment(), ExpandableHomeItemAdapter.EventItemListener {

    private val miniVm: MiniViewModel by viewModels {
        InjectorUtils.miniEventViewModelFactory(
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

    }

    override fun onEventDoneChanged(event: Event, isDone: Boolean) {
        miniVm.updateEventDoneStatus(event, isDone)
    }

    override fun onEventLongPressed(event: Event) {
        showSnackBar(binding.rvEvent, event.content)
    }

    override fun onEventClicked(event: Event) {

    }
}
