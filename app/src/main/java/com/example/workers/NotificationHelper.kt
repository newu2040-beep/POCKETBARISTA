package com.example.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.MainActivity
import com.example.R

object NotificationHelper {
    const val CHANNEL_ID_TIMERS = "channel_brew_timers"
    const val CHANNEL_ID_REMINDERS = "channel_barista_reminders"

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timersChannel = NotificationChannel(
                CHANNEL_ID_TIMERS,
                "Brewing Timers",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alerts for espresso extractions, pour-over blooms and cold brew steeping"
                enableVibration(true)
            }

            val remindersChannel = NotificationChannel(
                CHANNEL_ID_REMINDERS,
                "Barista Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Equipment maintenance, restock alerts and practice reminders"
                enableVibration(true)
            }

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            manager?.createNotificationChannel(timersChannel)
            manager?.createNotificationChannel(remindersChannel)
        }
    }

    fun showNotification(
        context: Context,
        channelId: String,
        notificationId: Int,
        title: String,
        message: String
    ) {
        try {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context,
                notificationId,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val manager = NotificationManagerCompat.from(context)
            manager.notify(notificationId, builder.build())
        } catch (_: SecurityException) {
            // Handled when notification permission is not granted
        }
    }
}
