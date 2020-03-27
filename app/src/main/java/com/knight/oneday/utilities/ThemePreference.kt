package com.knight.oneday.utilities

import android.content.SharedPreferences
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knight.oneday.OneDayApp
import com.knight.oneday.R

/**
 * @author knight
 * create at 20-3-21 上午9:28
 * 主题 黑暗模式和普通模式的切换功能(一个很好的可读与外 可变与内的例子 写的非常灵活有思想)
 */
class ThemePreference(private val sp: SharedPreferences) {

    private val _appTheme: String?
        get() = sp.getString(PREFERENCE_THEME_KEY, PREFERENCE_THEME_DEF_VAL)

    private val _uiModeLive: MutableLiveData<String> = MutableLiveData()

    val uiModeLive: LiveData<String>
        get() = _uiModeLive

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                PREFERENCE_THEME_KEY -> {
                    _uiModeLive.value = _appTheme
                }
            }
        }

    fun changeUiMode(mode: String) {
        sp.edit().putString(PREFERENCE_THEME_KEY, mode).apply()
        _uiModeLive.value = mode
    }

    fun nowUiModeIsLight() = uiModeLive.value == PREFERENCE_THEME_ARRAY[0]

    init {
        _uiModeLive.value = _appTheme
        sp.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    companion object {
        private const val PREFERENCE_THEME_KEY = "theme"
        private const val PREFERENCE_THEME_DEF_VAL = "System"

        val PREFERENCE_THEME_ARRAY = arrayOf("Light", "Dark", "System")

    }

}