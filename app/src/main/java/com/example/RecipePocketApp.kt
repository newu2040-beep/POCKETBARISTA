package com.example

import android.app.Application
import com.example.data.AppDatabase
import com.example.data.RecipePocketRepository
import com.example.data.UserPreferencesRepository
import com.example.workers.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class RecipePocketApp : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val preferencesRepository by lazy { UserPreferencesRepository(this) }
    val repository by lazy { RecipePocketRepository(database, preferencesRepository) }

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createNotificationChannels(this)
    }
}
