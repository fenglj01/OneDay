package com.knight.oneday.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.knight.oneday.R
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

}