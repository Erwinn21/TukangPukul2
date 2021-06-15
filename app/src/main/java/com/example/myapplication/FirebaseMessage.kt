package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessage : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        if(p0.notification != null) {
            showNotif(p0.notification!!.title, p0.notification!!.body)
        }
    }

    private fun showNotif(title: String?, message: String?) {
        var notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifChannel = NotificationChannel("notif1", "Notification", NotificationManager.IMPORTANCE_DEFAULT)
            notifChannel.description = "FCM Channel 1"
            notifChannel.enableLights(true)
            notifChannel.lightColor = Color.RED
            notifManager.createNotificationChannel(notifChannel)
        }
        var notifyNotif = NotificationCompat.Builder(
            this, "notif1", ).apply {
            setDefaults(Notification.DEFAULT_ALL)
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(title)
            setContentText(message)
            setContentInfo("Information")
        }
        notifManager.notify(1, notifyNotif.build())
    }
}