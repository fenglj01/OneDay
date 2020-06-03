package com.knight.oneday.utilities

import com.knight.oneday.R
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_ALLOW_EXPIRED
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_ALLOW_FINISHED
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_SNACK_REMIND_NOT_SHOW_EXPIRED_EVENT

/**
 * Create by FLJ in 2020/5/19 15:17
 * 设置
 */
object SettingPreferences {

    var showAllowExpired: Boolean by PreferenceAttrProxy(KEY_SHOW_ALLOW_EXPIRED, false)
    var showAllowFinished: Boolean by PreferenceAttrProxy(KEY_SHOW_ALLOW_FINISHED, false)

    /* 是否展示提示用户当前列表不展示过期任务 */
    var showRemindNotAllowExpired: Boolean by PreferenceAttrProxy(
        KEY_SHOW_SNACK_REMIND_NOT_SHOW_EXPIRED_EVENT,
        true
    )

}