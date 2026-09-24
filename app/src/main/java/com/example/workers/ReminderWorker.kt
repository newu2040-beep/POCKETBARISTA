package com.example.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val title = inputData.getString("title") ?: "RECIPEPOCKET Reminder"
        val description = inputData.getString("description") ?: "Time for your scheduled barista activity!"
        val reminderId = inputData.getInt("reminderId", 1001)

        NotificationHelper.showNotification(
            context = applicationContext,
            channelId = NotificationHelper.CHANNEL_ID_REMINDERS,
            notificationId = reminderId,
            title = title,
            message = description
        )

        return Result.success()
    }
}
