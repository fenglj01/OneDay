package com.knight.oneday.utilities

import com.knight.oneday.R
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_ALLOW_EXPIRED
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_ALLOW_FINISHED
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_LIST_GROUP_PRIORITY
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_LIST_SHOW_STRATEGY
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_SNACK_REMIND_NOT_SHOW_EXPIRED_EVENT
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.VALUE_LIST_GP_UNFINISHED
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.VALUE_LIST_STRATEGY_TODAY

/**
 * Create by FLJ in 2020/5/19 15:17
 * 设置
 */
object SettingPreferences {

    val showAllowExpired: Boolean by PreferenceAttrProxy(KEY_SHOW_ALLOW_EXPIRED, false)
    val showAllowFinished: Boolean by PreferenceAttrProxy(KEY_SHOW_ALLOW_FINISHED, false)
    val showListStrategy: String by PreferenceAttrProxy(
        KEY_SHOW_LIST_SHOW_STRATEGY,
        VALUE_LIST_STRATEGY_TODAY
    )
    val showListGroupPriority: String by PreferenceAttrProxy(
        KEY_SHOW_LIST_GROUP_PRIORITY,
        VALUE_LIST_GP_UNFINISHED
    )
    /* 是否展示提示用户当前列表不展示过期任务 */
    var showRemindNotAllowExpired: Boolean by PreferenceAttrProxy(
        KEY_SHOW_SNACK_REMIND_NOT_SHOW_EXPIRED_EVENT,
        true
    )

}