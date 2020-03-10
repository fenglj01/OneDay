package com.knight.oneday

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.knight.oneday.utilities.InjectorUtils
import com.knight.oneday.utilities.addKeyBoardListener
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
        initListeners()
    }

    private fun initListeners() {
        fabAdd.singleClick {
            showInputView()
        }
        ivSend.singleClick {
            hideInputView()
        }
        contentView.addKeyBoardListener(
            onShow = {
                Log.d("aa","show")
            },
            onHide = {
                Log.d("aa","hide")
            }
        )
    }

    private fun showInputView() {
        rlInput.visibility = View.VISIBLE
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
