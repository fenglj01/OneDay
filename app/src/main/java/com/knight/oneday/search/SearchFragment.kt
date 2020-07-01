package com.knight.oneday.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.knight.oneday.R
import com.knight.oneday.data.Task
import com.knight.oneday.databinding.FragmentSearchBinding
import com.knight.oneday.task.TaskAdapter
import com.knight.oneday.utilities.InjectorUtils

/**
 * Create by FLJ in 2020/6/30 16:42
 * 查询
 */
class SearchFragment : Fragment() {

    private val vm by viewModels<SearchViewModel> {
        InjectorUtils.searchViewModelFactory(
            requireContext()
        )
    }

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
        binding.searchTaskList.adapter = TaskAdapter(object :TaskAdapter.TaskEventListener{
            override fun onTaskClicked(view: View, task: Task) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTaskLongClicked(task: Task): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTaskStatusChanged(task: Task, isDone: Boolean) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}