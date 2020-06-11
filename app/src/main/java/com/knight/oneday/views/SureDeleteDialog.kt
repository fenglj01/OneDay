package com.knight.oneday.views

import android.os.Bundle
import android.view.*
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.knight.oneday.R
import com.knight.oneday.setting.SettingPreferences
import com.knight.oneday.utilities.singleClick

/**
 * Create by FLJ in 2020/6/5 14:55
 * 确认删除弹框
 */
class SureDeleteDialog : DialogFragment() {

    private lateinit var dialogView: View

    var onSure: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialogView = inflater.inflate(R.layout.dialog_never_remind_delete, container)
        return dialogView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogView.findViewById<Switch>(R.id.sw).setOnCheckedChangeListener { _, isChecked ->
            SettingPreferences.showRemindDelete = !isChecked
        }
        dialogView.findViewById<TextView>(R.id.tv_sure).singleClick {
            dialog?.dismiss()
            onSure?.invoke()
        }
        dialogView.findViewById<TextView>(R.id.tv_cancel).singleClick {
            dialog?.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        setDirectionAndWidth()
    }

    private fun setDirectionAndWidth() {
        val window = dialog?.window
        val params = window?.attributes
        params?.gravity = Gravity.CENTER
        params?.width = (resources.displayMetrics.widthPixels * 0.9F).toInt()
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.attributes = params
    }

}