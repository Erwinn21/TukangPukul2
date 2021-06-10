package com.example.myapplication

import android.content.Context

val PREF_NAME = "MyPref"
val KEYS_WIDGET_ID = "widgetIds"
class WidgetPref (context: Context) {
    val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    fun setId(ids : MutableSet<String>) {
        val editor = pref.edit()
        editor.putStringSet(KEYS_WIDGET_ID, ids)
        editor.apply()
    }
    fun getId() = pref.getStringSet(KEYS_WIDGET_ID, hashSetOf())
}