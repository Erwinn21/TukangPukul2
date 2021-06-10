package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mySharedPref: sizeSharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intentAlarm = Intent (this, InformationWidget::class.java).let {
            it.action = InformationWidget.ACTION_AUTO_UPDATE
            PendingIntent.getBroadcast(this, 101, it, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        var cal = Calendar.getInstance()
        cal.add(Calendar.SECOND, 5)
        var alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC, cal.timeInMillis, 5000L, intentAlarm)
        Log.e("Alarm Start","Alarm Start")

        mySharedPref= sizeSharedPref(this)
        buttonPlus.setOnClickListener {
            mySharedPref.size +=1
            sizeNum.setText(mySharedPref.size.toString())
        }
        buttonMin.setOnClickListener {
            mySharedPref.size -=1
            sizeNum.setText(mySharedPref.size.toString())
        }
    }
    override fun onResume() {
        super.onResume()
        sizeNum.setText(mySharedPref?.size.toString())
    }
    override fun onStop() {
        super.onStop()
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val ids = appWidgetManager.getAppWidgetIds(ComponentName(this, SizeChangerWidget::class.java))
        val updateIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(updateIntent)
    }
}