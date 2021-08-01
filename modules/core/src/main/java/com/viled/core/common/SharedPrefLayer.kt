package com.viled.core.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPrefLayer(context: Context) {

    private var shared: SharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
    private val gson: Gson = Gson()

    fun saveMyObject(item: Any) {
        val parameter = item::class.java.toString()
        val objectAsString = gson.toJson(item)
        val editor = shared.edit()
        editor.putString(parameter, objectAsString).apply()
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun <reified T> getMyObject(): T {
        val parameter = T::class.java.toString()
        val savedObjectAsString: String = shared.getString(parameter, "") ?: ""
        return gson.fromJson(savedObjectAsString, T::class.java) as T
    }

    companion object {
        private const val SHARED_NAME = "shared_pref_file"
    }
}