package com.knight.oneday.nav

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by FLJ in 2020/3/25 15:06
 * 底部弹出框菜单ViewHolder
 */
sealed class NavigationViewHolder<T : NavigationModelItem>(view: View) :
    RecyclerView.ViewHolder(view) {

    abstract fun bind(navItem: T)


}