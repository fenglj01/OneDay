package com.knight.oneday.search

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.knight.oneday.R
import com.knight.oneday.data.Task
import com.knight.oneday.databinding.FragmentSearchBinding
import com.knight.oneday.setting.SettingPreferences
import com.knight.oneday.task.TaskAdapter
import com.knight.oneday.utilities.DIALOG_TAG_DELETE_EVENT
import com.knight.oneday.utilities.InjectorUtils
import com.knight.oneday.utilities.getInputManagerService
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.views.SureDeleteDialog
import com.knight.oneday.views.swipe.ReboundingSwipeActionCallback

/**
 * Create by FLJ in 2020/6/30 16:42
 * 查询
 */
class SearchFragment : Fragment(), TaskAdapter.TaskEventListener {

    private val vm by viewModels<SearchViewModel> {
        InjectorUtils.searchViewModelFactory(
            requireContext()
        )
    }

    private val adapter by lazy { TaskAdapter(this) }

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        observeLiveData()

    }

    private fun initView() {
        ItemTouchHelper(ReboundingSwipeActionCallback()).apply {
            attachToRecyclerView(binding.searchTaskList)
        }
        binding.searchTaskList.adapter = adapter
        binding.searchBackIb.singleClick {
            hideSoftInput()
            findNavController().navigateUp()
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    vm.searchByText(it)
                }
                return false
            }
        })
    }

    private fun observeLiveData() {
        vm.taskList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            binding.searchEmptyView.isVisible = it.isEmpty()
        })
    }

    override fun onTaskClicked(view: View, task: Task) {
        hideSoftInput()
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToAddTaskFragment(task)
        )
    }

    override fun onTaskLongClicked(task: Task): Boolean {
        if (SettingPreferences.showRemindDelete) {
            SureDeleteDialog().apply {
                onSure = {
                    vm.deleteTask(task)
                }
            }.show(requireActivity().supportFragmentManager, DIALOG_TAG_DELETE_EVENT)
        } else {
            vm.deleteTask(task)
        }
        return true
    }

    override fun onTaskStatusChanged(task: Task, isDone: Boolean) {
        vm.changeTaskStatus(task, isDone)
    }

    private fun hideSoftInput() {
        getInputManagerService().hideSoftInputFromWindow(
            binding.searchBar.windowToken,
            2000
        )
    }
}