package com.knight.oneday.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.knight.oneday.databinding.FragmentCategoryBinding

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
        binding = FragmentCategoryBinding.inflate(inflater, container, true)
        return binding.root
    }
    

}