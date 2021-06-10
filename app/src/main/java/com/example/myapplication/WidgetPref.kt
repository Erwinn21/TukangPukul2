package com.example.myapplication

import android.content.Context
//deklarasi key
val PREF_NAME = "MyPref"
val KEYS_WIDGET_ID = "widgetIds"
class WidgetPref (context: Context) {
    //buat shared preferences untuk menyimpan semua id widget
    val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    fun setId(ids : MutableSet<String>){
        pref.edit().putStringSet(KEYS_WIDGET_ID, ids).apply()
    }
    fun getId() = pref.getStringSet(KEYS_WIDGET_ID, hashSetOf())
}