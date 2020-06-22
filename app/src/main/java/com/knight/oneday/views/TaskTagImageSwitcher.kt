package com.knight.oneday.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.ViewSwitcher
import com.knight.oneday.R
import java.lang.RuntimeException

class TaskTagImageSwitcher(context: Context, attrs: AttributeSet?) :
    ImageSwitcher(context, attrs), ViewSwitcher.ViewFactory {

    private val imageList = listOf<Int>(
        R.mipmap.ill_type_no_type,
        R.mipmap.ill_type_life,
        R.mipmap.ill_type_work,
        R.mipmap.ill_type_game,
        R.mipmap.ill_type_health
    )
    private var index = 0
        set(value) {
            if(field == value) return
            field = value
            setImageResource(imageList[index])
        }

    init {
        setInAnimation(context, R.anim.dialog_push_in)
        setOutAnimation(context, R.anim.dialog_push_out)
        setFactory(this)
    }

    override fun makeView(): View {
        val iv = ImageView(context)
        iv.setImageResource(imageList[index])
        return iv
    }

    fun changeSelectedIndex(selectedIndex: Int) {
        if (selectedIndex !in imageList.indices) throw RuntimeException("selectedIndex must in imageList indices!")
        index = selectedIndex
    }

}