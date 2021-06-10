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
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        if(myPref == null){
            myPref = WidgetPref(context)
        }
        var ids = myPref?.getId()
        myPref?.getId()?.clear()

        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        if(ids != null)
            myPref?.setId(ids)
    }

    override fun onEnabled(context: Context) {
        var intentAlarm = Intent(context, InformationWidget::class.java).let {
            it.action = ACTION_AUTO_UPDATE
            PendingIntent.getBroadcast(context, 101, it, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        var cal = Calendar.getInstance()
        cal.add(Calendar.SECOND, 5)
        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC, cal.timeInMillis,5000L,intentAlarm)
    }

    override fun onDisabled(context: Context) {
        var intentAlarm = Intent (context, InformationWidget::class.java).let {
            it.action = ACTION_AUTO_UPDATE
            PendingIntent.getBroadcast(context, 101, it, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(intentAlarm)
    }

    override fun onReceive(context : Context?, intent : Intent?) {
        // Enter relevant functionality for when the last widget is disabled
        if(intent?.action!!.equals(ACTION_AUTO_UPDATE)){
            if (myPref == null)
                myPref = WidgetPref (context!!)
            for (ids in myPref?.getId()!!) {
                updateAppWidget(context!!, AppWidgetManager.getInstance(context), ids.toInt() )
            }
        }
        super.onReceive(context, intent)
    }
    companion object {
        var menu = DataInformation()
        var ACTION_AUTO_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE"
        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val widgetText = menu.getMenu()
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.information_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

