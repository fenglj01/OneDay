package com.knight.oneday

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * 设置界面
 */
class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        val themePreference: ListPreference? = findPreference(THEME_KEY)
        val themeArrayOptionsAboveQ = resources.getStringArray(R.array.theme_options_above_q)
        val themeArrayOptionsBelowQ = resources.getStringArray(R.array.theme_options_below_q)
        themePreference?.entries =
            if (Build.VERSION.SDK_INT >= 29) themeArrayOptionsAboveQ else themeArrayOptionsBelowQ
        val themeArrayValues = resources.getStringArray(R.array.theme_values)
        themePreference?.summaryProvider =
            Preference.SummaryProvider<ListPreference> { preference ->
                when (preference.value) {
//                 Light Theme
                    themeArrayValues[0] -> themeArrayOptionsAboveQ[0]
//                 Dark Theme
                    themeArrayValues[1] -> themeArrayOptionsAboveQ[1]
//                 System Defined Theme/Auto Battery
                    themeArrayValues[2] -> if (Build.VERSION.SDK_INT >= 29) themeArrayOptionsAboveQ[2] else themeArrayOptionsBelowQ[2]
                    else -> getString(R.string.def)
                }
            }
    }

    companion object {
        const val THEME_KEY = "theme"
    }
}
