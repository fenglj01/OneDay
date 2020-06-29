package com.knight.oneday.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.knight.oneday.databinding.FragmentCategoryBinding
import com.knight.oneday.nav.BottomNavDrawerFragment

/**
 * Create by FLJ in 2020/6/29 9:11
 * 分类
 */
class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding

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
        val safeArgs: CategoryFragmentArgs by navArgs()
        safeArgs.taskType.run {
            Log.d("TaskType", "$this")
        }
        BottomNavDrawerFragment.navTag.observe(viewLifecycleOwner, Observer {
            Log.d("TaskTypeOb", "$it")
        })
    }


}