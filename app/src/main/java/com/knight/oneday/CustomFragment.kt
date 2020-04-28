package com.knight.oneday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.views.step.STEP_STATE_FINISHED
import com.knight.oneday.views.step.STEP_STATE_UNFINISHED
import com.knight.oneday.views.step.StepView.Companion.STEP_STATUS_EXECUTING
import com.knight.oneday.views.step.StepView.Companion.STEP_STATUS_FINISHED
import com.knight.oneday.views.step.StepView.Companion.STEP_STATUS_UNFINISHED
import kotlinx.android.synthetic.main.fragment_custom.*

/**
 * A simple [Fragment] subclass.
 */
class CustomFragment : Fragment() {

    private lateinit var contentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_custom, container, false)

        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv.singleClick {
            el.toggle()
        }
    }


}
