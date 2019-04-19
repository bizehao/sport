package com.sport.utilities

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sport.SportApplication

/**
 * User: bizehao
 * Date: 2019-04-10
 * Time: 上午9:19
 * Description: SharedPreferences工具类
 */
object SharePreferencesUtil {

    /**
     * 存储boolean类型的键值对到SharedPreferences文件当中。
     * @param key
     * 存储的键
     * @param value
     * 存储的值
     */
    fun save(key: String, value: Boolean) {
        val editor = getPreference().edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * 存储int类型的键值对到SharedPreferences文件当中。
     * @param key
     * 存储的键
     * @param value
     * 存储的值
     */
    fun save(key: String, value: Int) {
        val editor = getPreference().edit()
        editor.putInt(key, value)
        editor.apply()
    }

    /**
     * 存储long类型的键值对到SharedPreferences文件当中。
     * @param key
     * 存储的键
     * @param value
     * 存储的值
     */
    fun save(key: String, value: Long) {
        val editor = getPreference().edit()
        editor.putLong(key, value)
        editor.apply()
    }

    /**
     * 存储String类型的键值对到SharedPreferences文件当中。
     * @param key
     * 存储的键
     * @param value
     * 存储的值
     */
    fun save(key: String, value: String) {
        val editor = getPreference().edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * 存储boolean类型的键值对到SharedPreferences文件当中。
     * @param key
     * 存储的键
     * @param value
     * 存储的值
     */
    fun save(key: String, value: Float) {
        val editor = getPreference().edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    /**
     * 存储boolean类型的键值对到SharedPreferences文件当中。
     * @param key
     * 存储的键
     * @param value
     * 存储的值
     */
    fun <T> save(key: String, value: T) {
        val dataString = Gson().toJson(value)
        val editor = getPreference().edit()
        editor.putString(key, dataString)
        editor.apply()
    }

    /**
     * 从SharedPreferences文件当中读取参数传入键相应的boolean类型的值。
     * @param key
     * 读取的键
     * @param defValue
     * 如果读取不到值，返回的默认值
     * @return boolean类型的值，如果读取不到，则返回默认值
     */
    fun read(key: String, defValue: Boolean): Boolean {
        val prefs = getPreference()
        return prefs.getBoolean(key, defValue)
    }

    /**
     * 从SharedPreferences文件当中读取参数传入键相应的boolean类型的值。
     * @param key
     * 读取的键
     * @param defValue
     * 如果读取不到值，返回的默认值
     * @return boolean类型的值，如果读取不到，则返回默认值
     */
    fun read(key: String, defValue: Int): Int {
        val prefs = getPreference()
        return prefs.getInt(key, defValue)
    }

    /**
     * 从SharedPreferences文件当中读取参数传入键相应的boolean类型的值。
     * @param key
     * 读取的键
     * @param defValue
     * 如果读取不到值，返回的默认值
     * @return boolean类型的值，如果读取不到，则返回默认值
     */
    fun read(key: String, defValue: Float): Float {
        val prefs = getPreference()
        return prefs.getFloat(key, defValue)
    }

    /**
     * 从SharedPreferences文件当中读取参数传入键相应的boolean类型的值。
     * @param key
     * 读取的键
     * @param defValue
     * 如果读取不到值，返回的默认值
     * @return boolean类型的值，如果读取不到，则返回默认值
     */
    fun read(key: String, defValue: Long): Long {
        val prefs = getPreference()
        return prefs.getLong(key, defValue)
    }

    /**
     * 从SharedPreferences文件当中读取参数传入键相应的boolean类型的值。
     * @param key
     * 读取的键
     * @param defValue
     * 如果读取不到值，返回的默认值
     * @return boolean类型的值，如果读取不到，则返回默认值
     */
    fun read(key: String, defValue: String): String? {
        val prefs = getPreference()
        return prefs.getString(key, defValue)
    }

    /**
     * 从SharedPreferences文件当中读取参数传入键相应的boolean类型的值。
     * @param key
     * 读取的键
     * @param defValue
     * 如果读取不到值，返回的默认值
     * @return boolean类型的值，如果读取不到，则返回默认值
     */
    fun <T> read(key: String, defValue: T): T? {
        val prefs = getPreference()
        val dataString = prefs.getString(key, null)
        var value: T? = null
        val dataType = object : TypeToken<T>() {}.type
        if (dataString != null) {
            value = Gson().fromJson(dataString, dataType)
        }
        return value
    }

    /**
     * 判断SharedPreferences文件当中是否包含指定的键值。
     * @param key
     * 判断键是否存在
     * @return 键已存在返回true，否则返回false。
     */
    operator fun contains(key: String): Boolean {
        val prefs = getPreference()
        return prefs.contains(key)
    }

    /**
     * 清理SharedPreferences文件当中传入键所对应的值。
     * @param key
     * 想要清除的键
     */
    fun clear(key: String) {
        val editor = getPreference().edit()
        editor.remove(key)
        editor.apply()
    }

    /**
     * 将SharedPreferences文件中存储的所有值清除。
     */
    fun clearAll() {
        val editor = getPreference().edit()
        editor.clear()
        editor.apply()
    }

    private fun getPreference(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(
            SportApplication.context
        )
    }
}