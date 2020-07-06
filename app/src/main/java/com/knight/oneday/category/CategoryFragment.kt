package com.knight.oneday.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.appbar.AppBarLayout
import com.knight.oneday.R
import com.knight.oneday.data.Task
import com.knight.oneday.databinding.FragmentCategoryBinding
import com.knight.oneday.nav.BottomNavDrawerFragment
import com.knight.oneday.setting.SettingPreferences
import com.knight.oneday.task.TaskAdapter
import com.knight.oneday.utilities.DIALOG_TAG_DELETE_EVENT
import com.knight.oneday.utilities.InjectorUtils
import com.knight.oneday.utilities.TaskType
import com.knight.oneday.views.SureDeleteDialog
import com.knight.oneday.views.swipe.ReboundingSwipeActionCallback

/**
 * Create by FLJ in 2020/6/29 9:11
 * 分类
 */
class CategoryFragment : Fragment(), TaskAdapter.TaskEventListener {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: TaskAdapter

    private val vm by viewModels<CategoryViewModel> {
        InjectorUtils.categoryViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTaskRecyclerView()

        observeLiveData()

        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

        })

    }

    private fun initTaskRecyclerView() {
        ItemTouchHelper(ReboundingSwipeActionCallback()).apply {
            attachToRecyclerView(binding.categoryList)
        }
        categoryAdapter = TaskAdapter(this)
        binding.categoryList.adapter = categoryAdapter
    }

    private fun observeLiveData() {
        BottomNavDrawerFragment.navTag.observe(viewLifecycleOwner, Observer {
            vm.searchByType(it)
            binding.categoryIcon.changeSelectedIndex(TaskType.values().indexOf(it))
        })

        vm.taskList.observe(viewLifecycleOwner, Observer {
            categoryAdapter.submitList(it)
            binding.emptyBySearchView.isVisible = it.isEmpty()
        })

        vm.taskCount.observe(viewLifecycleOwner, Observer { count ->
            binding.collapsingToolbar.title = if (count.total != 0) {
                getString(R.string.title_category_frag).format(
                    count.finished,
                    count.expired
                )
            } else {
                binding.appBarLayout.setExpanded(false, true)
                ""
            }
        })
    }

    override fun onTaskClicked(view: View, task: Task) {
        findNavController().navigate(
            CategoryFragmentDirections.actionCategoryFragmentToAddTaskFragment(
                task
            )
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
}