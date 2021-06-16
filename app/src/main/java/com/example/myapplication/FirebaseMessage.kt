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

//mengimplementasi FirebaseMessagingService
class FirebaseMessage : FirebaseMessagingService() {
    //jika ada pesan yang dikirimkan dari FCM maka fungsi onMessageReceived akan merespon
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        //jika pesan yang dikirim tidak kosong maka sebuah notification akan muncul
        //notification tersebut akan berisi title dan body dari message nya
        if(p0.notification != null) {
            showNotif(p0.notification!!.title, p0.notification!!.body)
        }
    }

    private fun showNotif(title: String?, message: String?) {
        //notification channel
        //mengimplementasikan getSystemService
        var notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //jika versi dari android adalah oreo atau lebih tinggi maka :
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //mendeklarasikan NotificationChannel dengan id notif1 dan mengirimkannya ke notification manager
            val notifChannel = NotificationChannel("notif1", "Notification", NotificationManager.IMPORTANCE_DEFAULT)
            //jika notif1 berhasil dijalankan maka akan memunculkan popup dengan text FCM Channel 1
            notifChannel.description = "FCM Channel 1"
            //menganable light notification dengan warna merah
            notifChannel.enableLights(true)
            notifChannel.lightColor = Color.RED
            //membuat notification channel
            notifManager.createNotificationChannel(notifChannel)
        }
        //mengimplementasikan NotificationCompat dengan channel id notif1 untuk menampilkan isi pesan ke notifikasi
        var notifyNotif = NotificationCompat.Builder(
            this, "notif1", ).apply {
            setDefaults(Notification.DEFAULT_ALL)
            //menampilkan notifikasi secara real time
            setWhen(System.currentTimeMillis())
            //menampilkan icon kecil pada taskbar notifikasi
            setSmallIcon(R.mipmap.ic_launcher)
            //menampilkan title notifikasi
            setContentTitle(title)
            //menampilkan isi text/body dari notifikasi
            setContentText(message)
            setContentInfo("Information")
        }
        notifManager.notify(1, notifyNotif.build())
    }
}