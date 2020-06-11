package com.knight.oneday.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.knight.oneday.R

/**
 * Create by FLJ in 2020/6/11 9:30
 * 创建事件 思考过后 化繁为简 暂时不要加入步骤 清单这样的功能了
 */
class AddEventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add_event, container, false)
    }

}