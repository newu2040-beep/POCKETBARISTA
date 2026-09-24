package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.RecipePocketApp
import com.example.model.BaristaNoteEntity
import com.example.model.BaristaTheme
import com.example.model.BrewReminderEntity
import com.example.model.ExtractionLogEntity
import com.example.model.MeasurementUnit
import com.example.model.RecipeEntity
import com.example.model.ThemeMode
import com.example.model.UserProfile
import com.example.util.InvoiceExporter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = (application as RecipePocketApp).repository

    // Data streams
    val allRecipes: StateFlow<List<RecipeEntity>> = repository.allRecipes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteRecipes: StateFlow<List<RecipeEntity>> = repository.favoriteRecipes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedRecipes: StateFlow<List<RecipeEntity>> = repository.savedForLaterRecipes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customRecipes: StateFlow<List<RecipeEntity>> = repository.customRecipes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val extractionLogs: StateFlow<List<ExtractionLogEntity>> = repository.allExtractionLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val baristaNotes: StateFlow<List<BaristaNoteEntity>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val reminders: StateFlow<List<BrewReminderEntity>> = repository.allReminders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val baristaTheme: StateFlow<BaristaTheme> = repository.baristaTheme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BaristaTheme.ESPRESSO)

    val themeMode: StateFlow<ThemeMode> = repository.themeMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeMode.SYSTEM)

    val measurementUnit: StateFlow<MeasurementUnit> = repository.measurementUnit
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MeasurementUnit.METRIC)

    val hapticsEnabled: StateFlow<Boolean> = repository.hapticsEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val soundEnabled: StateFlow<Boolean> = repository.soundEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val defaultRatio: StateFlow<String> = repository.defaultRatio
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "1:16")

    val compactMode: StateFlow<Boolean> = repository.compactMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val userProfile: StateFlow<UserProfile> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfile())

    // Filter & Search states
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _selectedDifficulty = MutableStateFlow<String?>(null)
    val selectedDifficulty: StateFlow<String?> = _selectedDifficulty.asStateFlow()

    private val _selectedBrewMethod = MutableStateFlow<String?>(null)
    val selectedBrewMethod: StateFlow<String?> = _selectedBrewMethod.asStateFlow()

    val filteredRecipes: StateFlow<List<RecipeEntity>> = combine(
        allRecipes,
        _searchQuery,
        _selectedCategory,
        _selectedDifficulty,
        _selectedBrewMethod
    ) { recipes, query, category, difficulty, method ->
        recipes.filter { recipe ->
            val matchesQuery = query.isBlank() ||
                    recipe.title.contains(query, ignoreCase = true) ||
                    recipe.subtitle.contains(query, ignoreCase = true) ||
                    recipe.description.contains(query, ignoreCase = true) ||
                    recipe.tagsJson.contains(query, ignoreCase = true)

            val matchesCategory = category == "All" || recipe.category.equals(category, ignoreCase = true)
            val matchesDifficulty = difficulty == null || recipe.difficulty.equals(difficulty, ignoreCase = true)
            val matchesMethod = method == null || recipe.brewMethod.contains(method, ignoreCase = true)

            matchesQuery && matchesCategory && matchesDifficulty && matchesMethod
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setSelectedDifficulty(difficulty: String?) {
        _selectedDifficulty.value = difficulty
    }

    fun setSelectedBrewMethod(method: String?) {
        _selectedBrewMethod.value = method
    }

    // Recipe Actions
    fun toggleFavorite(recipeId: String, currentVal: Boolean) {
        viewModelScope.launch {
            vibrate(50)
            repository.setFavorite(recipeId, !currentVal)
        }
    }

    fun toggleSavedForLater(recipeId: String, currentVal: Boolean) {
        viewModelScope.launch {
            vibrate(50)
            repository.setSavedForLater(recipeId, !currentVal)
        }
    }

    fun recordRecipeView(recipeId: String) {
        viewModelScope.launch {
            repository.incrementViewCount(recipeId)
        }
    }

    fun saveRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.saveRecipe(recipe)
            vibrate(80)
        }
    }

    fun deleteRecipe(recipeId: String) {
        viewModelScope.launch {
            repository.deleteRecipe(recipeId)
            vibrate(100)
        }
    }

    // Extraction Log Actions
    fun addExtractionLog(log: ExtractionLogEntity) {
        viewModelScope.launch {
            repository.addExtractionLog(log)
            vibrate(80)
        }
    }

    fun deleteExtractionLog(id: Long) {
        viewModelScope.launch {
            repository.deleteExtractionLog(id)
        }
    }

    // Barista Notes Actions
    fun saveNote(note: BaristaNoteEntity) {
        viewModelScope.launch {
            repository.saveNote(note)
            vibrate(50)
        }
    }

    fun updateNote(note: BaristaNoteEntity) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    fun toggleNotePinned(id: Long, currentVal: Boolean) {
        viewModelScope.launch {
            repository.setNotePinned(id, !currentVal)
            vibrate(40)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    // Reminders Actions
    fun saveReminder(reminder: BrewReminderEntity) {
        viewModelScope.launch {
            repository.saveReminder(reminder)
            vibrate(60)
        }
    }

    fun toggleReminder(id: Long, currentVal: Boolean) {
        viewModelScope.launch {
            repository.setReminderEnabled(id, !currentVal)
            vibrate(40)
        }
    }

    fun deleteReminder(id: Long) {
        viewModelScope.launch {
            repository.deleteReminder(id)
        }
    }

    // Settings
    fun setBaristaTheme(theme: BaristaTheme) {
        viewModelScope.launch {
            repository.setBaristaTheme(theme)
            vibrate(40)
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            repository.setThemeMode(mode)
            vibrate(40)
        }
    }

    fun setMeasurementUnit(unit: MeasurementUnit) {
        viewModelScope.launch {
            repository.setMeasurementUnit(unit)
            vibrate(40)
        }
    }

    fun setHapticsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.setHapticsEnabled(enabled)
        }
    }

    fun setSoundEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.setSoundEnabled(enabled)
        }
    }

    fun setDefaultRatio(ratio: String) {
        viewModelScope.launch {
            repository.setDefaultRatio(ratio)
        }
    }

    fun setCompactMode(enabled: Boolean) {
        viewModelScope.launch {
            repository.setCompactMode(enabled)
            vibrate(30)
        }
    }

    fun exportPdfInvoice(context: Context) {
        viewModelScope.launch {
            val file = InvoiceExporter.exportPdfInvoice(context, userProfile.value, allRecipes.value, extractionLogs.value)
            InvoiceExporter.shareFile(context, file, "application/pdf", "RECIPEPOCKET Barista Invoice & Report")
        }
    }

    fun exportCsvInvoice(context: Context) {
        viewModelScope.launch {
            val file = InvoiceExporter.exportCsvInvoice(context, userProfile.value, allRecipes.value, extractionLogs.value)
            InvoiceExporter.shareFile(context, file, "text/csv", "RECIPEPOCKET Barista Invoice (CSV)")
        }
    }

    fun exportTxtInvoice(context: Context) {
        viewModelScope.launch {
            val file = InvoiceExporter.exportTxtInvoice(context, userProfile.value, allRecipes.value, extractionLogs.value)
            InvoiceExporter.shareFile(context, file, "text/plain", "RECIPEPOCKET Barista Invoice (Text)")
        }
    }

    fun updateUserProfile(profile: UserProfile) {
        viewModelScope.launch {
            repository.updateUserProfile(profile)
            vibrate(50)
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            repository.completeOnboarding()
            vibrate(100)
        }
    }

    // Backup / Restore JSON
    suspend fun exportDataJson(): String {
        return repository.exportDataJson(allRecipes.value, extractionLogs.value, baristaNotes.value)
    }

    suspend fun importDataJson(json: String): Boolean {
        val result = repository.importDataJson(json)
        if (result) vibrate(120)
        return result
    }

    // Vibrator helper
    fun vibrate(durationMillis: Long) {
        if (!hapticsEnabled.value) return
        try {
            val context = getApplication<Application>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                manager?.defaultVibrator?.vibrate(
                    VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                @Suppress("DEPRECATION")
                vibrator?.vibrate(durationMillis)
            }
        } catch (_: Exception) {}
    }

    // ==========================================
    // ESPRESSO SHOT TIMER STATE
    // ==========================================
    private val _espressoTimerSeconds = MutableStateFlow(0.0f)
    val espressoTimerSeconds: StateFlow<Float> = _espressoTimerSeconds.asStateFlow()

    private val _espressoIsRunning = MutableStateFlow(false)
    val espressoIsRunning: StateFlow<Boolean> = _espressoIsRunning.asStateFlow()

    private val _espressoTargetSeconds = MutableStateFlow(28f)
    val espressoTargetSeconds: StateFlow<Float> = _espressoTargetSeconds.asStateFlow()

    private val _espressoTargetYield = MutableStateFlow(36f)
    val espressoTargetYield: StateFlow<Float> = _espressoTargetYield.asStateFlow()

    private val _espressoDose = MutableStateFlow(18f)
    val espressoDose: StateFlow<Float> = _espressoDose.asStateFlow()

    private var espressoJob: Job? = null

    fun setEspressoTargetSeconds(sec: Float) { _espressoTargetSeconds.value = sec }
    fun setEspressoTargetYield(yield: Float) { _espressoTargetYield.value = yield }
    fun setEspressoDose(dose: Float) { _espressoDose.value = dose }

    fun startEspressoTimer() {
        if (_espressoIsRunning.value) return
        _espressoIsRunning.value = true
        vibrate(60)
        espressoJob = viewModelScope.launch {
            val startTime = System.currentTimeMillis() - (_espressoTimerSeconds.value * 1000).toLong()
            while (_espressoIsRunning.value) {
                val now = System.currentTimeMillis()
                _espressoTimerSeconds.value = (now - startTime) / 1000f

                // Target reached notification haptic
                if (_espressoTimerSeconds.value >= _espressoTargetSeconds.value &&
                    _espressoTimerSeconds.value - 0.1f < _espressoTargetSeconds.value) {
                    vibrate(200)
                }

                delay(50)
            }
        }
    }

    fun pauseEspressoTimer() {
        _espressoIsRunning.value = false
        espressoJob?.cancel()
        espressoJob = null
        vibrate(40)
    }

    fun resetEspressoTimer() {
        _espressoIsRunning.value = false
        espressoJob?.cancel()
        espressoJob = null
        _espressoTimerSeconds.value = 0f
        vibrate(40)
    }
}
