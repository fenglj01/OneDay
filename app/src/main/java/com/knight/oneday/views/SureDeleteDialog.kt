package com.knight.oneday.views

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.knight.oneday.R
import java.lang.RuntimeException
import java.util.zip.Inflater

/**
 * Create by FLJ in 2020/6/5 14:55
 * 确认删除弹框
 */
class SureDeleteDialog : DialogFragment() {

    /*R.layout.dialog_never_remind_delete*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_never_remind_delete, container)
    }

    override fun onStart() {
        super.onStart()
        setDirectionAndWidth()
    }

    private fun setDirectionAndWidth() {
        val window = dialog?.window
        val params = window?.attributes
        params?.gravity = Gravity.CENTER
        params?.width = (resources.displayMetrics.widthPixels * 0.75F).toInt()
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.attributes = params
    }

}