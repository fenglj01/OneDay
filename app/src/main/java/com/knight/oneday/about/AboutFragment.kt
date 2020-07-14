package com.knight.oneday.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.knight.oneday.R
import com.knight.oneday.databinding.FragmentAboutBinding
import kotlinx.android.synthetic.main.fragment_about.*

/**
 * Create by FLJ in 2020/7/14 14:42
 * 关于界面
 */
class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.aboutTool.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

}