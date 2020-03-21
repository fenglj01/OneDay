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
        toolBar.setOnMenuItemClickListener { item: MenuItem? ->
            when (item?.itemId) {
                R.id.menu_setting -> {
                    findNavController().navigate(R.id.action_miniFragment_to_settingFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun initEventRecyclerView() {
        adapter.onItemEvent = object : MiniEventRecyclerViewAdapter.OnItemEvent {
            override fun onItemFinishClick(eventId: Long) {
                miniVm.changeEventState(eventId)
                adapter.notifyDataSetChanged()
            }
        }
        binding.rvEvent.adapter = adapter
        /*binding.rvEvent.addItemDecoration(
            SectionDecoration(
                requireContext(),
                getSectionCallback()
            )
        )*/
    }

    private fun subscribeUi() {
        miniVm.eventList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            Log.d("TAG", "$it")
        }
    }

    private fun initListeners() {
        fabAdd.singleClick {
            showInputView()
        }
        ivSend.singleClick {
            hideInputView()
            if (edtEvent.text.isNullOrEmpty()) return@singleClick
            miniVm.addEvent()
        }
        contentView.addOnKeyBoardHidden {
           /* clInput.visibility = View.INVISIBLE
            fabAdd.show()*/
        }
        edtEvent.addTextChangedListener(
            afterTextChanged = {
                it?.run { miniVm.changeContent(toString()) }
            }
        )
        ivRemind.singleClick {
            showDatePickerDialog()
        }
    }


    @SuppressLint("RestrictedApi")
    private fun showInputView() {
        // 使用动画效果fab 会随着布局跑 看起来有点鬼畜 这里直接变为不可见
        fabAdd.visibility = View.INVISIBLE
        clInput.visibility = View.VISIBLE
        edtEvent.requestFocus()
        inputManager.showSoftInput(edtEvent, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideInputView() {
        fabAdd.show()
        clInput.visibility = View.INVISIBLE
        inputManager.hideSoftInputFromWindow(activity!!.window.peekDecorView().windowToken, 0)
    }

    private fun getSectionCallback(): SectionDecoration.SectionCallback =
        object : SectionDecoration.SectionCallback {
            override fun getSectionContent(dataPosition: Int): String {
                if (dataPosition == -1) return ""
                val event = adapter.getEvent(dataPosition)
                if (event.state == EventState.FINISHED) return getString(R.string.section_finished)
                return reminderSection(event.createTime, event.reminderTime)
            }
        }

    private fun showDatePickerDialog() {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.build().show(activity!!.supportFragmentManager, null)
    }


}
