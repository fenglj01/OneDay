package com.knight.oneday.setting

import com.knight.oneday.utilities.PreferenceAttrProxy
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_IS_FIRST_INSTALL
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_ALLOW_FINISHED
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_REMIND_DELETE_EVENT

/**
 * Create by FLJ in 2020/5/19 15:17
 * 设置
 */
object SettingPreferences {

    var showAllowFinished: Boolean by PreferenceAttrProxy(
        KEY_SHOW_ALLOW_FINISHED,
        true
    )

    var showRemindDelete: Boolean by PreferenceAttrProxy(
        KEY_SHOW_REMIND_DELETE_EVENT,
        true
    )

    var isFirstInstall: Boolean by PreferenceAttrProxy(
        KEY_IS_FIRST_INSTALL,
        true
    )


}