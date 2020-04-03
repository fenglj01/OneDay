package com.knight.oneday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.knight.oneday.databinding.FragmentCreateEventBinding
import com.knight.oneday.nav.NavigationModel
import com.knight.oneday.utilities.InjectorUtils
import com.knight.oneday.utilities.getMaterialDatePickerBuilder
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.viewmodels.CreateEventViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CreateEventFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentCreateEventBinding

    private val createViewModel: CreateEventViewModel by viewModels {
        InjectorUtils.createEventViewModelFactory(
            requireContext()
        )
    }

    private val datePicker: MaterialDatePicker<*> by lazy {
        binding.root.getMaterialDatePickerBuilder().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateEventBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = createViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDropMenu()
        initListener()
    }

    private fun initListener() {
        binding.apply {
            closeIv.singleClick {
                findNavController().navigateUp()
            }
            eventDateTv.singleClick {
                datePicker.show(parentFragmentManager, "")
            }
        }
        datePicker.addOnPositiveButtonClickListener {
            binding.eventDateTv.text = datePicker.headerText
            datePicker.selection
        }
    }

    /**
     * 下拉按钮 (输入内容后可以新建保存)
     */
    private fun initDropMenu() {
        val items = NavigationModel.getNavTagString()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_text_item, items)
        (binding.eventTagAtv as? AutoCompleteTextView)?.setAdapter(adapter)
    }

}
