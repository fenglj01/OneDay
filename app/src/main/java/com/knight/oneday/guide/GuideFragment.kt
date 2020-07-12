package com.knight.oneday.guide

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView

import com.knight.oneday.R
import com.knight.oneday.views.themeColor

private const val GUIDE_BACKGROUND_COLOR = "background"
private const val GUIDE_RESOURCE = "resource"
private const val GUIDE_TITLE = "title"
private const val GUIDE_CONTENT = "content"

class GuideFragment : Fragment() {

    private var guideBackgroundColor: Int = Color.WHITE
    private var guideTitle: String = ""
    private var guideContent: String = ""
    private var guideResources: Int = R.mipmap.ill_welcome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            guideBackgroundColor = it.getInt(GUIDE_BACKGROUND_COLOR)
            guideTitle = it.getString(GUIDE_TITLE, "")
            guideContent = it.getString(GUIDE_CONTENT, "")
            guideResources = it.getInt(GUIDE_RESOURCE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guide, container, false).apply {

            setBackgroundColor(guideBackgroundColor)

            findViewById<AppCompatTextView>(R.id.title).text = guideTitle
            findViewById<AppCompatTextView>(R.id.content).text = guideContent

            findViewById<AppCompatImageView>(R.id.resource).setImageResource(guideResources)

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: String, param3: String, param4: Int) =
            GuideFragment().apply {
                arguments = Bundle().apply {
                    putInt(GUIDE_BACKGROUND_COLOR, param1)
                    putString(GUIDE_TITLE, param2)
                    putString(GUIDE_CONTENT, param3)
                    putInt(GUIDE_RESOURCE, param4)
                }
            }
    }
}
