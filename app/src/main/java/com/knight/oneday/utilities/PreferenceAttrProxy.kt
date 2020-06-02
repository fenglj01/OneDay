package com.knight.oneday.utilities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.knight.oneday.OneDayApp
import java.io.*
import java.nio.charset.StandardCharsets
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Create by FLJ in 2020/5/19 15:16
 * 设置的属性代理
 */
class PreferenceAttrProxy<T>(val key: String, private val defaultValue: T) :
    ReadWriteProperty<Any?, T> {

    companion object {

        const val KEY_SHOW_ALLOW_EXPIRED = "show_allow_expired"
        const val KEY_SHOW_ALLOW_FINISHED = "show_allow_finished"
        const val KEY_SHOW_SNACK_REMIND_NOT_SHOW_EXPIRED_EVENT = "remind_not_show_expired"

        const val KEY_SHOW_LIST_SHOW_STRATEGY = "list_show_strategy"
        const val KEY_SHOW_LIST_GROUP_PRIORITY = "list_group_priority"

        const val VALUE_LIST_STRATEGY_TODAY = "today"
        const val VALUE_LIST_STRATEGY_ALL = "all"

        const val VALUE_LIST_GP_UNFINISHED = "unfinished"
        const val VALUE_LIST_GP_FINISHED = "finished"

        const val KEY_SHOW_LIST_ORDER_IS_DESC = "list_order_is_desc"
        const val KEY_SHOW_LIST_ORDER_METHOD = "list_order"

        const val VALUE_LIST_ORDER_REMIND = "remind"
        const val VALUE_LIST_ORDER_CREATE = "create"

    }

    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(
            OneDayApp.instance()
        )
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getValue(key, defaultValue)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putValue(key, value)
    }

    @SuppressLint("CommitPrefEdits")
    private fun <T> putValue(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> putString(name, serialize(value))
        }.apply()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(name: String, default: T): T = with(prefs) {
        val res: Any? = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> {
                val serializeStr = getString(name, serialize(default))
                if (null == serializeStr) {
                    null
                } else {
                    deSerialization(serializeStr)
                }
            }
        }
        return if (null != res) {
            res as T
        } else {
            default
        }
    }

    @Throws(IOException::class)
    private fun <OBJ> serialize(obj: OBJ): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString(StandardCharsets.ISO_8859_1.name())
        serStr = java.net.URLEncoder.encode(serStr, StandardCharsets.UTF_8.name())
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun <OBJ> deSerialization(str: String): OBJ {
        val redStr = java.net.URLDecoder.decode(str, StandardCharsets.UTF_8.name())
        val byteArrayInputStream =
            ByteArrayInputStream(redStr.toByteArray(charset(StandardCharsets.ISO_8859_1.name())))
        val objectInputStream = ObjectInputStream(byteArrayInputStream)
        val obj = objectInputStream.readObject() as OBJ
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }
}