package com.dk.myweatherapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.dk.myweatherapp.data.CHANNEL_ID
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotifyService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.notification?.title.toString()
        val body = message.notification?.body.toString()
        pushNotification(title, body)

        super.onMessageReceived(message)
    }

    private fun pushNotification(title: String, body: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_small_icon)
            setContentTitle(title)
            setContentText(body)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotifyChanel(notificationManager)
        }
        notification.setChannelId(CHANNEL_ID)
        notificationManager.notify(0, notification.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotifyChanel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(CHANNEL_ID, "КАНАААААЛ", NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "Очень нужный канал"
        }
        notificationManager.createNotificationChannel(channel)
    }

    override fun onNewToken(token: String) {
        println(token)
        super.onNewToken(token)
    }

}