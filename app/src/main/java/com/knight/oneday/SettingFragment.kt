package com.knight.oneday

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
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.knight.oneday.generated.callback.OnClickListener
import com.knight.oneday.views.showSnackBar
import kotlinx.android.synthetic.main.fragment_preferences.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting_toolbar.title = getString(R.string.menu_setting)
        setting_toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }


    companion object {
        const val THEME_KEY = "theme"
    }
}
