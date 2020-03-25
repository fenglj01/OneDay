package com.knight.oneday.data

import androidx.recyclerview.widget.DiffUtil

/**
 * @author knight
 * create at 20-3-25 下午7:17
 * 事件分类 使用typealias来使得类型更加语义化
 */
typealias EventTag = String

object EventTagDiff : DiffUtil.ItemCallback<EventTag>() {
    override fun areItemsTheSame(oldItem: EventTag, newItem: EventTag): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: EventTag, newItem: EventTag): Boolean =
        oldItem == newItem
}