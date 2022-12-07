package com.dk.myweatherapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.dk.myweatherapp.data.NOTIFY_BODY
import com.dk.myweatherapp.data.NOTIFY_TITLE
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotifyService : FirebaseMessagingService() {
    private var notificationID = 0

    override fun onMessageReceived(message: RemoteMessage) {
        val notify = message.data
        val title = notify[NOTIFY_TITLE]
        val body = notify[NOTIFY_BODY]
        if (!title.isNullOrEmpty() && !body.isNullOrEmpty()) {
            pushNotification(title, body)
        }
        super.onMessageReceived(message)
    }

    private fun pushNotification(title: String, body: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this,"Super Channel").apply {
            setSmallIcon(R.drawable.ic_small_icon)
            setContentTitle(title)
            setContentText(body)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotifyChanel(notificationManager)
        }
        notification.setChannelId("Super Channel")
        notificationID++
        notificationManager.notify(notificationID, notification.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotifyChanel(notificationManager: NotificationManager) {
        val channel = NotificationChannel("Super Channel", "КАНАААААЛ", NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "Очень нужный канал"
        }
        notificationManager.createNotificationChannel(channel)
    }

    override fun onNewToken(token: String) {
        println(token)
        super.onNewToken(token)
    }

}