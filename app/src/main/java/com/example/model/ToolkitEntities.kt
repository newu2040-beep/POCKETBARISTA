package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "extraction_logs")
data class ExtractionLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateMillis: Long = System.currentTimeMillis(),
    val coffeeBean: String,
    val roaster: String,
    val roastDate: String = "",
    val doseGrams: Float,
    val yieldGrams: Float,
    val brewTimeSeconds: Float,
    val grindSetting: String,
    val waterTempCelsius: Float = 93f,
    val ratioText: String = "",
    val rating: Int = 5, // 1 to 5
    val acidityScore: Int = 3, // 1 to 5
    val sweetnessScore: Int = 4, // 1 to 5
    val bodyScore: Int = 4, // 1 to 5
    val bitternessScore: Int = 2, // 1 to 5
    val tastingNotes: String = "",
    val method: String = "Espresso"
)

@Entity(tableName = "barista_notes")
data class BaristaNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val categoryTag: String = "Cupping", // "Cupping", "Bean Review", "Technique", "Gear", "Water Recipe"
    val isPinned: Boolean = false,
    val photoUri: String? = null,
    val dateMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "brew_reminders")
data class BrewReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val reminderType: String, // "COLD_BREW", "CLEAN_MACHINE", "PRACTICE_LATTE_ART", "RESTOCK_BEANS", "MORNING_BREW"
    val targetTimeMillis: Long,
    val isRecurring: Boolean = false,
    val recurrenceRule: String = "NONE", // "DAILY", "WEEKLY", "NONE"
    val isEnabled: Boolean = true
)
