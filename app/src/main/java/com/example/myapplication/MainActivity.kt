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
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var interAds : InterstitialAd? = null
    private lateinit var mySharedPref: sizeSharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //inisialisasi ads
        MobileAds.initialize(this) {}
        //iklan akan diload
        adView.loadAd(AdRequest.Builder().build())

        //menutup ads secara sementara
        closeAd.setOnClickListener {
            adView.destroy()
        }

        //membuat button signUp menjadi invisible
        signUp.visibility = View.GONE
        //load adMob dan build add tersebut
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                //jika load tersebut fail maka akan menjalankan function ini
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    //jika load failed maka akan muncul popup "Load Failed"
                    Toast.makeText(this@MainActivity, "Load Failed", Toast.LENGTH_SHORT).show()
                }
                //jika load berhasil maka akan menjalankan InterstitialAd
                //dan membuat button signUp menjadi visible
                override fun onAdLoaded(p0: InterstitialAd) {
                    super.onAdLoaded(p0)
                    interAds = p0
                    signUp.visibility = View.VISIBLE
                }
            }
        )

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

    //jika tombol signUp telah di klik maka function ini akan dijalankan
    fun showInterstitial(view: View) {
        //jika interAds memiliki data maka iklan akan ditampilkan sesudah kembali dari activity SignUp
        if(interAds != null) {
            interAds?.show(this)
            val mySignUp = Intent(this@MainActivity, SignUp::class.java)
            startActivity(mySignUp)
        }
    }
}