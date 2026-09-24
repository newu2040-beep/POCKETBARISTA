package com.example.data

import com.example.model.BaristaNoteEntity
import com.example.model.BaristaTheme
import com.example.model.BrewReminderEntity
import com.example.model.ExtractionLogEntity
import com.example.model.MeasurementUnit
import com.example.model.RecipeEntity
import com.example.model.ThemeMode
import com.example.model.UserProfile
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import org.json.JSONObject

class RecipePocketRepository(
    private val database: AppDatabase,
    private val preferences: UserPreferencesRepository
) {
    // Recipes
    val allRecipes: Flow<List<RecipeEntity>> = database.recipeDao().getAllRecipes()
    val favoriteRecipes: Flow<List<RecipeEntity>> = database.recipeDao().getFavoriteRecipes()
    val savedForLaterRecipes: Flow<List<RecipeEntity>> = database.recipeDao().getSavedForLaterRecipes()
    val customRecipes: Flow<List<RecipeEntity>> = database.recipeDao().getCustomRecipes()

    fun getRecipeById(id: String): Flow<RecipeEntity?> = database.recipeDao().getRecipeById(id)

    suspend fun saveRecipe(recipe: RecipeEntity) {
        database.recipeDao().insertRecipe(recipe)
    }

    suspend fun setFavorite(id: String, isFavorite: Boolean) {
        database.recipeDao().setFavorite(id, isFavorite)
    }

    suspend fun setSavedForLater(id: String, isSaved: Boolean) {
        database.recipeDao().setSavedForLater(id, isSaved)
    }

    suspend fun incrementViewCount(id: String) {
        database.recipeDao().incrementViewCount(id)
    }

    suspend fun deleteRecipe(id: String) {
        database.recipeDao().deleteRecipeById(id)
    }

    // Extraction Logs
    val allExtractionLogs: Flow<List<ExtractionLogEntity>> = database.extractionLogDao().getAllLogs()

    suspend fun addExtractionLog(log: ExtractionLogEntity): Long {
        return database.extractionLogDao().insertLog(log)
    }

    suspend fun deleteExtractionLog(id: Long) {
        database.extractionLogDao().deleteLogById(id)
    }

    // Notes
    val allNotes: Flow<List<BaristaNoteEntity>> = database.baristaNoteDao().getAllNotes()

    suspend fun saveNote(note: BaristaNoteEntity): Long {
        return database.baristaNoteDao().insertNote(note)
    }

    suspend fun updateNote(note: BaristaNoteEntity) {
        database.baristaNoteDao().updateNote(note)
    }

    suspend fun setNotePinned(id: Long, isPinned: Boolean) {
        database.baristaNoteDao().setPinned(id, isPinned)
    }

    suspend fun deleteNote(id: Long) {
        database.baristaNoteDao().deleteNoteById(id)
    }

    // Reminders
    val allReminders: Flow<List<BrewReminderEntity>> = database.brewReminderDao().getAllReminders()

    suspend fun saveReminder(reminder: BrewReminderEntity): Long {
        return database.brewReminderDao().insertReminder(reminder)
    }

    suspend fun updateReminder(reminder: BrewReminderEntity) {
        database.brewReminderDao().updateReminder(reminder)
    }

    suspend fun setReminderEnabled(id: Long, isEnabled: Boolean) {
        database.brewReminderDao().setEnabled(id, isEnabled)
    }

    suspend fun deleteReminder(id: Long) {
        database.brewReminderDao().deleteReminderById(id)
    }

    // Preferences & Settings
    val baristaTheme: Flow<BaristaTheme> = preferences.baristaTheme
    val themeMode: Flow<ThemeMode> = preferences.themeMode
    val measurementUnit: Flow<MeasurementUnit> = preferences.measurementUnit
    val hapticsEnabled: Flow<Boolean> = preferences.hapticsEnabled
    val soundEnabled: Flow<Boolean> = preferences.soundEnabled
    val defaultRatio: Flow<String> = preferences.defaultRatio
    val compactMode: Flow<Boolean> = preferences.compactMode
    val userProfile: Flow<UserProfile> = preferences.userProfile

    suspend fun setBaristaTheme(theme: BaristaTheme) = preferences.setBaristaTheme(theme)
    suspend fun setThemeMode(mode: ThemeMode) = preferences.setThemeMode(mode)
    suspend fun setMeasurementUnit(unit: MeasurementUnit) = preferences.setMeasurementUnit(unit)
    suspend fun setHapticsEnabled(enabled: Boolean) = preferences.setHapticsEnabled(enabled)
    suspend fun setSoundEnabled(enabled: Boolean) = preferences.setSoundEnabled(enabled)
    suspend fun setDefaultRatio(ratio: String) = preferences.setDefaultRatio(ratio)
    suspend fun setCompactMode(enabled: Boolean) = preferences.setCompactMode(enabled)
    suspend fun updateUserProfile(profile: UserProfile) = preferences.updateUserProfile(profile)
    suspend fun completeOnboarding() = preferences.completeOnboarding()

    // Backup and Restore
    suspend fun exportDataJson(allRecipesList: List<RecipeEntity>, allLogsList: List<ExtractionLogEntity>, allNotesList: List<BaristaNoteEntity>): String {
        val root = JSONObject()
        root.put("version", 1)
        root.put("appName", "RECIPEPOCKET")
        root.put("exportTimestamp", System.currentTimeMillis())

        val recipesArr = JSONArray()
        for (r in allRecipesList.filter { it.isCustom }) {
            val ro = JSONObject()
            ro.put("id", r.id)
            ro.put("title", r.title)
            ro.put("subtitle", r.subtitle)
            ro.put("category", r.category)
            ro.put("brewMethod", r.brewMethod)
            ro.put("description", r.description)
            ro.put("difficulty", r.difficulty)
            ro.put("grindSize", r.grindSize)
            ro.put("recommendedRatio", r.recommendedRatio)
            ro.put("waterTemperature", r.waterTemperature)
            ro.put("prepTimeMinutes", r.prepTimeMinutes)
            ro.put("brewTimeSeconds", r.brewTimeSeconds)
            ro.put("servings", r.servings)
            ro.put("caffeineMgApprox", r.caffeineMgApprox)
            ro.put("houseTip", r.houseTip)
            ro.put("equipmentJson", r.equipmentJson)
            ro.put("ingredientsJson", r.ingredientsJson)
            ro.put("instructionsJson", r.instructionsJson)
            ro.put("stickersJson", r.stickersJson ?: "")
            recipesArr.put(ro)
        }
        root.put("customRecipes", recipesArr)

        val logsArr = JSONArray()
        for (l in allLogsList) {
            val lo = JSONObject()
            lo.put("coffeeBean", l.coffeeBean)
            lo.put("roaster", l.roaster)
            lo.put("roastDate", l.roastDate)
            lo.put("doseGrams", l.doseGrams.toDouble())
            lo.put("yieldGrams", l.yieldGrams.toDouble())
            lo.put("brewTimeSeconds", l.brewTimeSeconds.toDouble())
            lo.put("grindSetting", l.grindSetting)
            lo.put("rating", l.rating)
            lo.put("tastingNotes", l.tastingNotes)
            logsArr.put(lo)
        }
        root.put("journalLogs", logsArr)

        val notesArr = JSONArray()
        for (n in allNotesList) {
            val no = JSONObject()
            no.put("title", n.title)
            no.put("content", n.content)
            no.put("categoryTag", n.categoryTag)
            no.put("isPinned", n.isPinned)
            notesArr.put(no)
        }
        root.put("notes", notesArr)

        return root.toString(2)
    }

    suspend fun importDataJson(jsonString: String): Boolean {
        return try {
            val root = JSONObject(jsonString)
            if (root.has("customRecipes")) {
                val recipesArr = root.getJSONArray("customRecipes")
                for (i in 0 until recipesArr.length()) {
                    val ro = recipesArr.getJSONObject(i)
                    val recipe = RecipeEntity(
                        id = ro.optString("id", "custom_${System.currentTimeMillis()}_$i"),
                        title = ro.optString("title", "Imported Recipe"),
                        subtitle = ro.optString("subtitle", ""),
                        category = ro.optString("category", "Espresso & Classics"),
                        brewMethod = ro.optString("brewMethod", "Espresso"),
                        description = ro.optString("description", ""),
                        difficulty = ro.optString("difficulty", "Beginner"),
                        grindSize = ro.optString("grindSize", "Fine"),
                        recommendedRatio = ro.optString("recommendedRatio", "1:2"),
                        waterTemperature = ro.optString("waterTemperature", "93°C"),
                        prepTimeMinutes = ro.optInt("prepTimeMinutes", 3),
                        brewTimeSeconds = ro.optInt("brewTimeSeconds", 30),
                        servings = ro.optInt("servings", 1),
                        caffeineMgApprox = ro.optInt("caffeineMgApprox", 64),
                        houseTip = ro.optString("houseTip", ""),
                        equipmentJson = ro.optString("equipmentJson", "[]"),
                        ingredientsJson = ro.optString("ingredientsJson", "[]"),
                        instructionsJson = ro.optString("instructionsJson", "[]"),
                        stickersJson = ro.optString("stickersJson", null),
                        isCustom = true
                    )
                    database.recipeDao().insertRecipe(recipe)
                }
            }

            if (root.has("journalLogs")) {
                val logsArr = root.getJSONArray("journalLogs")
                for (i in 0 until logsArr.length()) {
                    val lo = logsArr.getJSONObject(i)
                    val log = ExtractionLogEntity(
                        coffeeBean = lo.optString("coffeeBean", "Imported Bean"),
                        roaster = lo.optString("roaster", "Local Roaster"),
                        roastDate = lo.optString("roastDate", ""),
                        doseGrams = lo.optDouble("doseGrams", 18.0).toFloat(),
                        yieldGrams = lo.optDouble("yieldGrams", 36.0).toFloat(),
                        brewTimeSeconds = lo.optDouble("brewTimeSeconds", 28.0).toFloat(),
                        grindSetting = lo.optString("grindSetting", "Medium-Fine"),
                        rating = lo.optInt("rating", 5),
                        tastingNotes = lo.optString("tastingNotes", "")
                    )
                    database.extractionLogDao().insertLog(log)
                }
            }

            if (root.has("notes")) {
                val notesArr = root.getJSONArray("notes")
                for (i in 0 until notesArr.length()) {
                    val no = notesArr.getJSONObject(i)
                    val note = BaristaNoteEntity(
                        title = no.optString("title", "Imported Note"),
                        content = no.optString("content", ""),
                        categoryTag = no.optString("categoryTag", "Cupping"),
                        isPinned = no.optBoolean("isPinned", false)
                    )
                    database.baristaNoteDao().insertNote(note)
                }
            }
            true
        } catch (_: Exception) {
            false
        }
    }
}
