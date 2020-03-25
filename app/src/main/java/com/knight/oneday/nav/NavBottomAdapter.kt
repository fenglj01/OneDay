package com.knight.oneday.nav

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by FLJ in 2020/3/25 15:20
 * 底部adapter
 */
class NavBottomAdapter :
    ListAdapter<NavigationModelItem, NavigationViewHolder<NavigationModelItem>>(NavigationModelItem.NavModeItemDiff) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NavigationViewHolder<NavigationModelItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(
        holder: NavigationViewHolder<NavigationModelItem>,
        position: Int
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}