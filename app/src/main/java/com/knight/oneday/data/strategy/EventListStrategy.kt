package com.knight.oneday.data.strategy

import com.knight.oneday.data.EventRepository
import com.knight.oneday.utilities.SettingPreferences

/**
 * Create by FLJ in 2020/6/2 14:02
 * 首页查询列表的策略
 */
sealed class SearchStrategy(
    var showExpired: Boolean = SettingPreferences.showAllowExpired,
    var showFinished: Boolean = SettingPreferences.showAllowFinished,
    var priority: SearchPriority = SearchPriority.valueOf(SettingPreferences.showListGroupPriority),
    var order: SearchOrder = SearchOrder.valueOf(SettingPreferences.showListOrder),
    var isDesc: Boolean = SettingPreferences.showListOrderIsDesc
) {

    abstract fun search()
}

/* 分组优先 */
enum class SearchPriority(pri: String) {
    UNFINISHED("unfinished"), FINISHED("finished")
}

/* 排序 */
enum class SearchOrder(order: String) {
    REMIND("remind"), CREATE("create")
}

/**
 * Create by FLJ in 2020/6/2 14:09
 * 当日查询
 */
class NowDaySearchStrategy(private val repository: EventRepository) : SearchStrategy() {
    override fun search() {

    }
}

/**
 * Create by FLJ in 2020/6/2 14:10
 * 所有日期查询
 */
class AllDaySearchStrategy(private val repository: EventRepository) : SearchStrategy() {
    override fun search() {

    }
}
