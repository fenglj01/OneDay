package com.knight.oneday.setting

import com.knight.oneday.R


/**
 * 设置界面
 */
class SettingFragment : BaseSettingFragment() {

    override fun preferenceXml(): Int = R.xml.preferences

    override fun afterPreferenceXml() {

    }

}
