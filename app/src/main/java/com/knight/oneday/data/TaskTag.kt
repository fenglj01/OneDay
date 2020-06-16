package com.knight.oneday.data

import androidx.recyclerview.widget.DiffUtil

/**
 * @author knight
 * create at 20-3-25 下午7:17
 * 事件分类 使用typealias来使得类型更加语义化 这个东西可能会考虑存放到sqlite中去 现在可以暂时不考虑
 */
typealias TaskTag = String

object TaskTagDiff : DiffUtil.ItemCallback<TaskTag>() {
    override fun areItemsTheSame(oldItem: TaskTag, newItem: TaskTag): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: TaskTag, newItem: TaskTag): Boolean =
        oldItem == newItem
}