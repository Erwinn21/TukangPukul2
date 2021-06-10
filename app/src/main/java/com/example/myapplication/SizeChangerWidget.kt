package com.example.myapplication

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class SizeChangerWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        var sizeWidget = sizeSharedPref(context!!)
        if(AddOnClick.equals(intent?.action)){
            sizeWidget.size += 1
        }
        else if(MinOnClick.equals(intent?.action)){
            sizeWidget.size -=1
        }
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val thisWidgetComponenName = ComponentName(context!!.packageName, javaClass.name)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidgetComponenName)
        for(appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
    companion object{
        private val AddOnClick = "AddOnClick"
        private val MinOnClick = "MinOnClick"
        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.size_changer_widget)
            var sizeWidget = sizeSharedPref(context)
            views.setTextViewText(R.id.appwidget_text_size, sizeWidget.size.toString())
            views.setOnClickPendingIntent(R.id.appwidget_buttonPlus, getPendingSelfIntent(context, AddOnClick))
            views.setOnClickPendingIntent(R.id.appwidget_buttonMin, getPendingSelfIntent(context, MinOnClick))
            views.setOnClickPendingIntent(R.id.WidgetLayout, getPendingIntent(context))

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private fun getPendingSelfIntent(context: Context, addOnClick: String): PendingIntent? = Intent(context,
            SizeChangerWidget::class.java).let {
            it.action = addOnClick
            PendingIntent.getBroadcast(context,101,it, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        private fun getPendingIntent(context: Context) : PendingIntent {
            var myIntent = Intent(context, MainActivity::class.java)
            return PendingIntent.getActivity(context, 0, myIntent ,0)
        }
    }
}

