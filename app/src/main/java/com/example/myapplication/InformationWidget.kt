package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class InformationWidget : AppWidgetProvider() {
    private var myPref :WidgetPref? = null
    //perintah yang dijalankan secara otomatis dari updatePeriodMillis pada xml info
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        //proses pencatatan id

        if(myPref == null) {
            myPref = WidgetPref(context)
        }
        var ids = myPref?.getId()
        myPref?.getId()?.clear()

        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            //semua id widget yang dibentuk akan disimpan ke dalam shared preferences
            ids?.add(appWidgetId.toString())
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

        //kirimkan semua id ke dalam shared preferences jika ada
        if(ids != null)
            myPref?.setId(ids)
    }

    //data yang ditampilkan saat pertama kali dijalankan
    override fun onEnabled(context: Context) {
        //intentAlarm mengirimkan broadcast ke information widget untuk diambil onReceiver
        var intentAlarm = Intent(context, InformationWidget::class.java).let {
            it.action = ACTION_AUTO_UPDATE
            PendingIntent.getBroadcast(context, 101, it, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        //tentukan interval waktu setiap kapan data di-update
        var cal = Calendar.getInstance()
        cal.add(Calendar.SECOND, 5)
        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC, cal.timeInMillis,5000L,intentAlarm)
    }

    //saat widget di-remove
    override fun onDisabled(context: Context) {
        var intentAlarm = Intent (context, InformationWidget::class.java).let {
            it.action = ACTION_AUTO_UPDATE
            PendingIntent.getBroadcast(context, 101, it, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //cancel intentAlarm yang sedang dijalankan
        alarmManager.cancel(intentAlarm)
    }

    override fun onReceive(context : Context?, intent : Intent?) {
        // Enter relevant functionality for when the last widget is disabled
        if(intent?.action!!.equals(ACTION_AUTO_UPDATE)){
            //ambil semua id yang ada di shared preferences
            if (myPref == null)
                myPref = WidgetPref (context!!)

            for (ids in myPref?.getId()!!) {
                //widget akan diupdate tiap 5 detik dengan pesan yang sudah ditentukan
                updateAppWidget(context!!, AppWidgetManager.getInstance(context), ids.toInt() )
            }
        }
        super.onReceive(context, intent)
    }
    companion object {
        //mendeklarasi class DataInformation
        var menu = DataInformation()
        //mendeklarasi sebuah permission untuk meng-update widget
        var ACTION_AUTO_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE"
        //fungsi untuk meng-update text yang ada di widget
        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            //textview yang ada di widget akan menampilkan
            //data dari fungsi getMenu
            val widgetText = menu.getMenu()
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.information_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

