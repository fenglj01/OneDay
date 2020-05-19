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
class SettingFragment : BaseSettingFragment() {

    override fun preferenceXml(): Int = R.xml.preferences

    override fun afterPreferenceXml() {

    }

}
