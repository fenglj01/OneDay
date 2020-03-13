package com.knight.oneday

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.knight.oneday.adapters.MiniEventRecyclerViewAdapter
import com.knight.oneday.data.Event
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
            context!!
        )
    }

    private val inputManager: InputMethodManager by lazy {
        getInputManager(activity!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mini, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initListeners()
        initTestRecyclerView()
    }

    private fun initToolbar() {
        toolBar.apply {
            title = TimeUtils.getTodayMonthAndDayStr()
        }
    }

    private fun initTestRecyclerView() {
        rvEvent.layoutManager = LinearLayoutManager(context)
        //rvEvent.addItemDecoration(SectionDecoration(context!!))
        val adapter = MiniEventRecyclerViewAdapter()

        val miniData = mutableListOf<Event>()
        for (i in 0..30) {
            miniData.add(
                Event(
                    content = "$i",
                    type = EventType.GUIDE,
                    createTime = when (i) {
                        in 0..5 -> Calendar.getInstance().apply {
                            set(
                                Calendar.DAY_OF_MONTH,
                                13
                            )
                        }
                        in 6..10 -> Calendar.getInstance().apply {
                            set(
                                Calendar.DAY_OF_MONTH,
                                14
                            )
                        }
                        in 11..15 -> Calendar.getInstance().apply {
                            set(
                                Calendar.DAY_OF_MONTH,
                                15
                            )
                        }
                        else -> Calendar.getInstance().apply {
                            set(
                                Calendar.DAY_OF_MONTH,
                                16
                            )
                        }
                    }
                )
            )
        }
        rvEvent.adapter = adapter
        adapter.submitList(miniData)
        val seData = miniData.map {
            SectionBean("${it.createTime.get(Calendar.DAY_OF_MONTH)}")
        }
        rvEvent.addItemDecoration(
            SectionDecoration(context!!, object : SectionDecoration.SectionCallback {
                override fun getSectionContent(dataPosition: Int): String {
                    return "${miniData[dataPosition].reminderTime[Calendar.DAY_OF_MONTH]}"
                }
            })
        )
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
