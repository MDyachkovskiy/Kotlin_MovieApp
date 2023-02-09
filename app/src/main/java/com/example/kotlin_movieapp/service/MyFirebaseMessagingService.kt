package com.example.kotlin_movieapp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.kotlin_movieapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val PUSH_KEY_TITLE = "title"
        private const val PUSH_KEY_MESSAGE = "message"

        private const val LOW_PRIOR_CHANNEL_ID = "low_channel_id"
        private const val HIGH_PRIOR_CHANNEL_ID = "high_channel_id"

        private const val LOW_NOTIFICATION_ID = 1
        private const val HIGH_NOTIFICATION_ID = 2
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.v("MyFirebaseMessagingService", "onNewToken " + token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val remoteMessageData = message.data

        if(remoteMessageData.isNotEmpty()) {
            handleDataMessage(remoteMessageData.toMap())
        }
    }

    private fun handleDataMessage(data: Map<String, String>) {
        val title = data[PUSH_KEY_TITLE]
        val message = data[PUSH_KEY_MESSAGE]

        if(!title.isNullOrBlank() && !message.isNullOrBlank()){
            showNotification(title, message)
        }
    }

    private fun showNotification(title: String, message: String) {

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val lowPriorNotificationBuilder = NotificationCompat.Builder(applicationContext,
            LOW_PRIOR_CHANNEL_ID
        ).apply{
            setSmallIcon(R.drawable.ic_baseline_local_movies_24)
            setContentTitle(title)
            setContentText(message)
            priority = NotificationCompat.PRIORITY_LOW
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createLowPriorNotificationChannel(notificationManager)
        }
        notificationManager.notify(LOW_NOTIFICATION_ID, lowPriorNotificationBuilder.build())

        val highPriorNotificationBuilder = NotificationCompat.Builder(applicationContext,
            HIGH_PRIOR_CHANNEL_ID
        ).apply{
            setSmallIcon(R.drawable.ic_baseline_local_movies_24)
            setContentTitle(title)
            setContentText(message)
            priority = NotificationCompat.PRIORITY_HIGH
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createHighPriorNotificationChannel(notificationManager)
        }
        notificationManager.notify(HIGH_NOTIFICATION_ID, highPriorNotificationBuilder.build())

    }

    private fun createLowPriorNotificationChannel(notificationManager: NotificationManager){
        val name = getString(R.string.lowPriorChannelName)
        val descriptionText = getString(R.string.lowPriorChannelDesc)

        val importance = NotificationManager.IMPORTANCE_LOW

        val channel =NotificationChannel(LOW_PRIOR_CHANNEL_ID, name, importance).apply{
            description = descriptionText
        }

        notificationManager.createNotificationChannel(channel)
    }

    private fun createHighPriorNotificationChannel(notificationManager: NotificationManager){
        val name = getString(R.string.lowPriorChannelName)
        val descriptionText = getString(R.string.lowPriorChannelDesc)

        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel =NotificationChannel(HIGH_PRIOR_CHANNEL_ID, name, importance).apply{
            description = descriptionText
        }

        notificationManager.createNotificationChannel(channel)
    }
}