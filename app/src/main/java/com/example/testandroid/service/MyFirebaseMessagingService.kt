package com.example.testandroid.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.example.testandroid.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseMessagingService"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
        //todo: handle send new token to server
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "Message Notification: " + message.notification)
        createNotification(message.notification?.title ?: "No title",
            message.notification?.body ?: "No body")
    }

    private fun createNotification(title: String, content: String) {
        createNotificationChannel(applicationContext)

        // Build the notification
        val notificationBuilder = Notification.Builder(applicationContext, "default_channel_id")
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)
        val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
        notificationManager.notify(0, notificationBuilder.build())

    }

    private fun createNotificationChannel(context: Context) {
        val channelId = "default_channel_id"
        val channelName = "Default Channel"
        val channelDescription = "This is the default notification channel"

        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = channelDescription
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}