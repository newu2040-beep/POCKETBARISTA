package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.BaristaNoteEntity
import com.example.model.BrewReminderEntity
import com.example.model.ExtractionLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExtractionLogDao {
    @Query("SELECT * FROM extraction_logs ORDER BY dateMillis DESC")
    fun getAllLogs(): Flow<List<ExtractionLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: ExtractionLogEntity): Long

    @Delete
    suspend fun deleteLog(log: ExtractionLogEntity)

    @Query("DELETE FROM extraction_logs WHERE id = :id")
    suspend fun deleteLogById(id: Long)

    @Query("SELECT COUNT(*) FROM extraction_logs")
    suspend fun getLogCount(): Int
}

@Dao
interface BaristaNoteDao {
    @Query("SELECT * FROM barista_notes ORDER BY isPinned DESC, dateMillis DESC")
    fun getAllNotes(): Flow<List<BaristaNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: BaristaNoteEntity): Long

    @Update
    suspend fun updateNote(note: BaristaNoteEntity)

    @Query("UPDATE barista_notes SET isPinned = :isPinned WHERE id = :id")
    suspend fun setPinned(id: Long, isPinned: Boolean)

    @Delete
    suspend fun deleteNote(note: BaristaNoteEntity)

    @Query("DELETE FROM barista_notes WHERE id = :id")
    suspend fun deleteNoteById(id: Long)

    @Query("SELECT COUNT(*) FROM barista_notes")
    suspend fun getNotesCount(): Int
}

@Dao
interface BrewReminderDao {
    @Query("SELECT * FROM brew_reminders ORDER BY targetTimeMillis ASC")
    fun getAllReminders(): Flow<List<BrewReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: BrewReminderEntity): Long

    @Update
    suspend fun updateReminder(reminder: BrewReminderEntity)

    @Query("UPDATE brew_reminders SET isEnabled = :isEnabled WHERE id = :id")
    suspend fun setEnabled(id: Long, isEnabled: Boolean)

    @Delete
    suspend fun deleteReminder(reminder: BrewReminderEntity)

    @Query("DELETE FROM brew_reminders WHERE id = :id")
    suspend fun deleteReminderById(id: Long)
}
