package com.knight.oneday

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.datepicker.MaterialDatePicker
import com.knight.oneday.adapters.MiniEventRecyclerViewAdapter
import com.knight.oneday.databinding.FragmentMiniBinding
import com.knight.oneday.utilities.*
import com.knight.oneday.viewmodels.MiniViewModel
import com.knight.oneday.views.SectionDecoration
import kotlinx.android.synthetic.main.fragment_mini.*

/**
 * @author knight
 * create at 20-3-9 下午7:21
 * 极简风格的主页
 */
class MiniFragment : Fragment() {

    private val miniVm: MiniViewModel by viewModels {
        InjectorUtils.miniEventViewModelFactory(
            requireContext()
        )
    }
    private val adapter: MiniEventRecyclerViewAdapter by lazy { MiniEventRecyclerViewAdapter() }
    private val inputManager: InputMethodManager by lazy {
        getInputManagerService()
    }
    private lateinit var binding: FragmentMiniBinding

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
        binding.rvEvent.adapter = adapter
    }

    private fun subscribeUi() {
        miniVm.eventList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun initListeners() {

    }



}
