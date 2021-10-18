package com.viled.core.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPrefLayer(context: Context) {

    private var sharedPref: SharedPreferences =
        context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
    private val gson: Gson = Gson()

    var isFirstEntry
        get() = sharedPref.getBoolean(IS_FIRST_ENTRY, true) ?: true
        set(value) {
            val editor = sharedPref.edit()
            editor.putBoolean(IS_FIRST_ENTRY, value)
            editor.apply()
        }

    var keyName
        get() = sharedPref.getString(KEY_NAME, "") ?: ""
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(KEY_NAME, value)
            editor.apply()
        }

    var token
        get() = sharedPref.getString(TOKEN, "") ?: ""
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(TOKEN, value)
            editor.apply()
        }

    var isBiometry
        get() = sharedPref.getBoolean(BIOMETRY, false)
        set(value) {
            val editor = sharedPref.edit()
            editor.putBoolean(BIOMETRY, value)
            editor.apply()
        }

    fun saveMyObject(item: Any) {
        val parameter = item::class.java.toString()
        val objectAsString = gson.toJson(item)
        val editor = sharedPref.edit()
        editor.putString(parameter, objectAsString).apply()
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun <reified T> getMyObject(): T {
        val parameter = T::class.java.toString()
        val savedObjectAsString: String = sharedPref.getString(parameter, "") ?: ""
        return gson.fromJson(savedObjectAsString, T::class.java) as T
    }


    companion object {
        private const val SHARED_NAME = "shared_pref_file"
        private const val IS_FIRST_ENTRY = "is_first_entry"
        private const val KEY_NAME = "key_name"
        private const val TOKEN = "token"
        private const val BIOMETRY = "biometry"
    }
}