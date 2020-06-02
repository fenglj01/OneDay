package com.knight.oneday

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.navigation.fragment.findNavController
import androidx.preference.*
import androidx.preference.Preference.OnPreferenceChangeListener
import com.google.android.material.snackbar.Snackbar
import com.knight.oneday.generated.callback.OnClickListener
import com.knight.oneday.utilities.PreferenceAttrProxy
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_ALLOW_EXPIRED
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_ALLOW_FINISHED
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_LIST_GROUP_PRIORITY
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.KEY_SHOW_LIST_SHOW_STRATEGY
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.VALUE_LIST_GP_FINISHED
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.VALUE_LIST_GP_UNFINISHED
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.VALUE_LIST_STRATEGY_ALL
import com.knight.oneday.utilities.PreferenceAttrProxy.Companion.VALUE_LIST_STRATEGY_TODAY
import com.knight.oneday.utilities.SettingPreferences
import com.knight.oneday.views.showSnackBar
import kotlinx.android.synthetic.main.fragment_preferences.*

/**
 * 设置界面
 */
class SettingFragment : BaseSettingFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun preferenceXml(): Int = R.xml.preferences

    override fun afterPreferenceXml() {
        /* 有些设置是要联动反应的 */
        PreferenceManager.getDefaultSharedPreferences(context)
            .registerOnSharedPreferenceChangeListener(this)
        /* 是否可以显示完成任务的限制 */
        preferenceScreen.get<SwitchPreference>(KEY_SHOW_ALLOW_FINISHED)
            ?.onPreferenceChangeListener = object : OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
                if (preference.sharedPreferences.getString(
                        KEY_SHOW_LIST_GROUP_PRIORITY,
                        VALUE_LIST_GP_UNFINISHED
                    ) == VALUE_LIST_GP_FINISHED
                    && newValue is Boolean
                    && newValue == false
                ) {
                    showSnackBar(
                        setting_toolbar,
                        R.string.can_not_setting_false_show_finished,
                        Snackbar.LENGTH_LONG
                    )
                    return false
                }
                return true
            }
        }
    }

    override fun onSharedPreferenceChanged(sp: SharedPreferences?, key: String?) {
        when (key) {
            KEY_SHOW_LIST_SHOW_STRATEGY -> {
                /* 当用户调整显示列表为所有日期的时候 那么过期任务也就默认要显示了 */
                if (sp?.getString(key, VALUE_LIST_STRATEGY_TODAY) == VALUE_LIST_STRATEGY_ALL) {
                    preferenceScreen.get<SwitchPreference>(KEY_SHOW_ALLOW_EXPIRED)?.isChecked = true
                }
            }
            KEY_SHOW_LIST_GROUP_PRIORITY -> {
                /* 当选择完成优先的时候 完成任务的显示就会默认打开 */
                if (sp?.getString(key, VALUE_LIST_GP_UNFINISHED) == VALUE_LIST_GP_FINISHED) {
                    preferenceScreen.get<SwitchPreference>(KEY_SHOW_ALLOW_FINISHED)?.isChecked =
                        true
                }
            }
        }
    }
}
