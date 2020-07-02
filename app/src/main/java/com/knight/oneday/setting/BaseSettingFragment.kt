package com.knight.oneday.setting

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.knight.oneday.R
import com.knight.oneday.utilities.dp
import com.knight.oneday.views.themeColor
import kotlinx.android.synthetic.main.fragment_preferences.*

/**
 * Create by FLJ in 2020/5/19 13:22
 * 设置界面的提取
 */
abstract class BaseSettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(preferenceXml(), rootKey)
        afterPreferenceXml()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        view.setBackgroundColor(requireContext().themeColor(R.attr.colorSurface))
    }

    private fun initToolbar() {
        setting_toolbar.title = titleString()
        setting_toolbar.elevation = 2f.dp
        setting_toolbar.setNavigationOnClickListener {
            beforeNavigateUp()
            findNavController().navigateUp()
        }
    }

    abstract fun preferenceXml(): Int

    protected open fun titleString(): String = getString(R.string.menu_setting)

    protected open fun beforeNavigateUp() {

    }

    protected open fun afterPreferenceXml() {
    }
}