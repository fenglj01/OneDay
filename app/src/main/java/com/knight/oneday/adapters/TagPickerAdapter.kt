package com.knight.oneday.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.knight.oneday.R
import com.knight.oneday.nav.NavigationModelItem

/**
 * Create by FLJ in 2020/4/9 14:40
 * 类别选择器适配器
 */
class TagPickerAdapter(
    context: Context,
    @LayoutRes val resource: Int,
    private val tags: List<NavigationModelItem.NavEventTag>
) : ArrayAdapter<NavigationModelItem.NavEventTag>(context, resource, tags) {

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true

    override fun getCount(): Int = tags.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val vh: ViewHolder
        val rootView: View
        if (null == convertView) {
            rootView = LayoutInflater.from(context).inflate(resource, parent, false)
            vh = ViewHolder(
                rootView.findViewById(R.id.cell_title_tv),
                rootView.findViewById(R.id.cell_icon_iv)
            )
            rootView.tag = vh
        } else {
            rootView = convertView
            vh = convertView.tag as ViewHolder
        }
        getItem(position)?.run {
            vh.tagIcon.setImageResource(icon)
            vh.tagTitle.text = taskTag
        }
        return rootView
    }

    private class ViewHolder(val tagTitle: TextView, val tagIcon: ImageView)

}