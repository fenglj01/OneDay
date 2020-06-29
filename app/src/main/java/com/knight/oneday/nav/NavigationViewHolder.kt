package com.knight.oneday.nav

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.databinding.NavDividerItemLayoutBinding
import com.knight.oneday.databinding.NavMenuItemLayoutBinding
import com.knight.oneday.databinding.NavTaskTagItemLayoutBinding

/**
 * Create by FLJ in 2020/3/25 15:06
 * 底部弹出框菜单ViewHolder
 */
sealed class NavigationViewHolder<T : NavigationModelItem>(view: View) :
    RecyclerView.ViewHolder(view) {

    abstract fun bind(navItem: T)

    class NavMenuItemViewHolder(
        private val binding: NavMenuItemLayoutBinding,
        private val listener: NavBottomAdapter.NavigationAdapterListener
    ) : NavigationViewHolder<NavigationModelItem.NavMenuItem>(binding.root) {
        override fun bind(navItem: NavigationModelItem.NavMenuItem) {
            binding.run {
                navMenuItem = navItem
                navListener = listener
                executePendingBindings()
            }
        }
    }

    class NavDividerViewHolder(
        private val binding: NavDividerItemLayoutBinding
    ) : NavigationViewHolder<NavigationModelItem.NavDivider>(binding.root) {
        override fun bind(navItem: NavigationModelItem.NavDivider) {
            binding.run {
                navDivider = navItem
                executePendingBindings()
            }
        }
    }

    class NavTaskTagViewHolder(
        private val binding: NavTaskTagItemLayoutBinding,
        private val listener: NavBottomAdapter.NavigationAdapterListener
    ) : NavigationViewHolder<NavigationModelItem.NavTaskTag>(binding.root) {
        override fun bind(navItem: NavigationModelItem.NavTaskTag) {
            binding.run {
                navTaskTag = navItem
                navListener = listener
                executePendingBindings()
            }
        }
    }

}