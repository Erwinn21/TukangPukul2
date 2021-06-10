package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences

class sizeSharedPref(context: Context) {
    private val sizeKey = "KeyRand"
    private val mySharePref : SharedPreferences
    init {
        mySharePref = context.getSharedPreferences("sharePrefSize", Context.MODE_PRIVATE)
    }
    var size : Int

    get() = mySharePref.getInt(sizeKey,0)
    set(value) {
        if(value<=0)
            mySharePref.edit().putInt(sizeKey,0).commit()
        else
            mySharePref.edit().putInt(sizeKey,value).commit()
    }
}