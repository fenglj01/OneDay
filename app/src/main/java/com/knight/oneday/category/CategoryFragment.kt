package com.knight.oneday.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import com.knight.oneday.data.Task
import com.knight.oneday.databinding.FragmentCategoryBinding
import com.knight.oneday.nav.BottomNavDrawerFragment
import com.knight.oneday.task.TaskAdapter
import com.knight.oneday.utilities.InjectorUtils
import com.knight.oneday.utilities.TaskType
import com.knight.oneday.views.swipe.ReboundingSwipeActionCallback

/**
 * Create by FLJ in 2020/6/29 9:11
 * 分类
 */
class CategoryFragment : Fragment(), TaskAdapter.TaskEventListener {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryTaskAdapter

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


    }

    private fun initTaskRecyclerView() {
        ItemTouchHelper(ReboundingSwipeActionCallback()).apply {
            attachToRecyclerView(binding.categoryList)
        }
        categoryAdapter = CategoryTaskAdapter(this)
        binding.categoryList.adapter = categoryAdapter
    }

    private fun observeLiveData() {
        BottomNavDrawerFragment.navTag.observe(viewLifecycleOwner, Observer {
            Log.d("TaskType->Observe", "$it")
            vm.searchByType(it)
        })

        vm.taskList.observe(viewLifecycleOwner, Observer {
            Log.d("TaskType->list", "$it")
            categoryAdapter.submitList(it)
        })
    }

    override fun onTaskClicked(view: View, task: Task) {

    }

    override fun onTaskLongClicked(task: Task): Boolean {
        return false
    }

    override fun onTaskStatusChanged(task: Task, isDone: Boolean) {
    }
}