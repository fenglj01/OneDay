package com.knight.oneday.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import com.knight.oneday.R
import com.knight.oneday.data.TaskTag
import com.knight.oneday.data.TaskTagDiff
import com.knight.oneday.utilities.TaskType

/**
 * Create by FLJ in 2020/3/25 14:54
 * 底部弹出操作框Item 因为后期可能会拓展 所以做成了密封类 来确认这件事情
 */
sealed class NavigationModelItem {

    // 菜单
    data class NavMenuItem(
        val id: Int,
        @DrawableRes val icon: Int,
        @StringRes val titleRes: Int,
        var checked: Boolean
    ) : NavigationModelItem()

    // 分割线
    data class NavDivider(val title: String) : NavigationModelItem()

    // 事件分类
    data class NavTaskTag(
        val id: Int,
        val taskTag: TaskTag, @DrawableRes var icon: Int = R.drawable.ic_one_day_tag,
        var taskType: TaskType = TaskType.NO_CATEGORY,
        var checked: Boolean = false
    ) : NavigationModelItem()

    object NavTaskTagDiff : DiffUtil.ItemCallback<NavTaskTag>() {
        override fun areItemsTheSame(oldItem: NavTaskTag, newItem: NavTaskTag): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NavTaskTag, newItem: NavTaskTag): Boolean {
            return oldItem.id == newItem.id && oldItem.checked == newItem.checked && oldItem.taskType == newItem.taskType
        }
    }

    // 相同区分
    object NavModeItemDiff : DiffUtil.ItemCallback<NavigationModelItem>() {
        override fun areItemsTheSame(
            oldItem: NavigationModelItem,
            newItem: NavigationModelItem
        ): Boolean {
            return when {
                oldItem is NavMenuItem && newItem is NavMenuItem -> oldItem.id == newItem.id
                oldItem is NavDivider && newItem is NavDivider -> oldItem.title == newItem.title
                oldItem is NavTaskTag && newItem is NavTaskTag -> oldItem.id == newItem.id
                else -> oldItem == newItem
            }
        }

        override fun areContentsTheSame(
            oldItem: NavigationModelItem,
            newItem: NavigationModelItem
        ): Boolean {
            return when {
                oldItem is NavMenuItem && newItem is NavMenuItem -> oldItem.id == newItem.id && oldItem.icon == newItem.icon && oldItem.titleRes == newItem.titleRes && oldItem.checked == newItem.checked
                oldItem is NavDivider && newItem is NavDivider -> oldItem.title == newItem.title
                oldItem is NavTaskTag && newItem is NavTaskTag -> NavTaskTagDiff.areContentsTheSame(
                    oldItem,
                    newItem
                )
                else -> false
            }
        }
    }
}