package com.knight.oneday

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.knight.oneday.adapters.MiniEventRecyclerViewAdapter
import com.knight.oneday.data.Event
import com.knight.oneday.databinding.FragmentMiniBinding
import com.knight.oneday.utilities.*
import com.knight.oneday.viewmodels.MiniViewModel
import com.knight.oneday.views.SectionBean
import com.knight.oneday.views.SectionDecoration
import kotlinx.android.synthetic.main.fragment_mini.*
import kotlinx.android.synthetic.main.input_event_layout.*
import java.util.*

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

    private lateinit var binding: FragmentMiniBinding

    private val inputManager: InputMethodManager by lazy {
        getInputManager(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMiniBinding.inflate(inflater, container, false)
        binding.viewModel = miniVm
        val adapter = MiniEventRecyclerViewAdapter()
        binding.rvEvent.adapter = adapter
        binding.rvEvent.addItemDecoration(
            SectionDecoration(requireContext(),
                object : SectionDecoration.SectionCallback {
                    override fun getSectionContent(dataPosition: Int): String {
                        val event = adapter.getEvent(dataPosition)
                        return reminderSection(event.createTime, event.reminderTime)
                    }
                })
        )
        miniVm.eventList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        fabAdd.singleClick {
            showInputView()
        }
        ivSend.singleClick {
            hideInputView()
        }
        contentView.addOnKeyBoardHidden {
            rlInput.visibility = View.INVISIBLE
            fabAdd.show()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun showInputView() {
        // 使用动画效果fab 会随着布局跑 看起来有点鬼畜 这里直接变为不可见
        fabAdd.visibility = View.INVISIBLE
        rlInput.visibility = View.VISIBLE
        edtEvent.requestFocus()
        inputManager.showSoftInput(edtEvent, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideInputView() {
        fabAdd.show()
        rlInput.visibility = View.INVISIBLE
        inputManager.hideSoftInputFromWindow(activity!!.window.peekDecorView().windowToken, 0)
    }


}
