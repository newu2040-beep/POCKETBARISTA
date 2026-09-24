package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.model.BaristaNoteEntity
import com.example.model.BrewReminderEntity
import com.example.model.ExtractionLogEntity
import com.example.model.RecipeEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        RecipeEntity::class,
        ExtractionLogEntity::class,
        BaristaNoteEntity::class,
        BrewReminderEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun extractionLogDao(): ExtractionLogDao
    abstract fun baristaNoteDao(): BaristaNoteDao
    abstract fun brewReminderDao(): BrewReminderDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_pocket_db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let { database ->
                            scope.launch(Dispatchers.IO) {
                                populateInitialData(database)
                            }
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }

        private suspend fun populateInitialData(database: AppDatabase) {
            database.recipeDao().insertAll(DefaultRecipes.getList())

            // Seed initial extraction log
            database.extractionLogDao().insertLog(
                ExtractionLogEntity(
                    coffeeBean = "Ethiopia Yirgacheffe Gedeb",
                    roaster = "Onyx Coffee Lab",
                    roastDate = "10 days ago",
                    doseGrams = 18.2f,
                    yieldGrams = 37.0f,
                    brewTimeSeconds = 27.5f,
                    grindSetting = "Commandant 12 clicks",
                    waterTempCelsius = 93.5f,
                    ratioText = "1:2.03",
                    rating = 5,
                    acidityScore = 4,
                    sweetnessScore = 5,
                    bodyScore = 4,
                    bitternessScore = 2,
                    tastingNotes = "Meyer lemon, jasmine blossoms, bergamot honey, velvety peach finish."
                )
            )

            // Seed initial barista note
            database.baristaNoteDao().insertNote(
                BaristaNoteEntity(
                    title = "Water Mineralization Formula (Lotus Drops)",
                    content = "For washed high-elevation Ethiopians:\n• Target TDS: 110 ppm\n• Magnesium: 65 ppm (enhances fruit acidity)\n• Calcium: 25 ppm (rounds body)\n• Buffer (Bicarbonate): 20 ppm (preserves delicate citric brightness).",
                    categoryTag = "Water Recipe",
                    isPinned = true
                )
            )
            database.baristaNoteDao().insertNote(
                BaristaNoteEntity(
                    title = "Milk Steaming Vortex Sweet Spot",
                    content = "Keep steam wand positioned at 3 o'clock in pitcher, tip tilted 15 degrees inward. Submerge 5mm to stop air introduction once pitcher reaches hand temperature (37°C) to get silky microfoam for swans.",
                    categoryTag = "Technique",
                    isPinned = true
                )
            )

            // Seed initial reminders
            database.brewReminderDao().insertReminder(
                BrewReminderEntity(
                    title = "Check Cold Brew Steeping",
                    description = "16-hour immersion ready to be filtered and bottled.",
                    reminderType = "COLD_BREW",
                    targetTimeMillis = System.currentTimeMillis() + 16 * 3600 * 1000L,
                    isRecurring = false,
                    isEnabled = true
                )
            )
            database.brewReminderDao().insertReminder(
                BrewReminderEntity(
                    title = "Weekly Espresso Backflush & Clean",
                    description = "Cafiza backflush grouphead and soak portafilter basket in hot water.",
                    reminderType = "CLEAN_MACHINE",
                    targetTimeMillis = System.currentTimeMillis() + 86400000L,
                    isRecurring = true,
                    recurrenceRule = "WEEKLY",
                    isEnabled = true
                )
            )
        }
    }
}
