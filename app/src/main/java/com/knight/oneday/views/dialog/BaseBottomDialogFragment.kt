package com.knight.oneday.views.dialog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.knight.oneday.R

/**
 * Create by FLJ in 2019/11/8 13:50
 * 底部弹出框重构
 */
abstract class BaseBottomDialogFragment : DialogFragment() {

    // 弹框视图
    lateinit var dialogView: View

    override fun onStart() {
        super.onStart()
        setDirectionAndWidth()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogView = inflater.inflate(dialogId(), container, false)
        initData()
        return dialogView
    }

    abstract fun dialogId(): Int

    abstract fun initView()

    abstract fun initEvent()

    abstract fun initData()

    /**
     * 设置弹出方向和宽度 默认底部和match
     */
    open fun setDirectionAndWidth() {
        val window = dialog!!.window
        val params = window!!.attributes
        params.gravity = Gravity.BOTTOM
        params.width = resources.displayMetrics.widthPixels
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.setWindowAnimations(R.style.BaseBottomDialog)
        window.attributes = params
    }
}