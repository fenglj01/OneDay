package com.knight.oneday

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.knight.oneday.utilities.InjectorUtils
import com.knight.oneday.utilities.getInputManager
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.viewmodels.MiniViewModel
import kotlinx.android.synthetic.main.fragment_mini.*
import kotlinx.android.synthetic.main.input_event_layout.*

/**
 * @author knight
 * create at 20-3-9 下午7:21
 * 极简风格的主页
 */
class MiniFragment : Fragment() {

    private val miniVm: MiniViewModel by lazy { InjectorUtils.miniEventViewModelFactory(context!!) }
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
        initListener()
    }

    private fun initListener() {
        fabAdd.singleClick {
            showInputView()
        }
        ivSend.singleClick {
            hideInputView()
        }
    }

    private fun showInputView() {
        rlInput.visibility = View.VISIBLE
        // 获取焦点
        edtEvent.requestFocus()
        inputManager.showSoftInput(edtEvent, InputMethodManager.SHOW_IMPLICIT)
        fabAdd.hide()
    }

    private fun hideInputView() {
        rlInput.visibility = View.INVISIBLE
        fabAdd.show()
        inputManager.hideSoftInputFromWindow(activity!!.window.peekDecorView().windowToken, 0)
    }


}
