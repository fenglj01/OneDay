package com.knight.oneday.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.knight.oneday.R
import com.knight.oneday.views.themeColor
import kotlinx.android.synthetic.main.fragment_welcome.*

/**
 * A simple [Fragment] subclass.
 */
class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        guide_pager.adapter = CustomFragmentPagerAdapter(
            requireActivity().supportFragmentManager,
            requireContext().themeColor(R.attr.colorSurface),
            requireContext().themeColor(R.attr.colorSecondary)
        )
    }

}
