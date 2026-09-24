package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.RecipeEntity
import com.example.ui.components.FloatingBottomNavBar
import com.example.ui.components.NavScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NotesAndSavedScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RecipeDetailScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.StudioScreen
import com.example.ui.screens.ToolkitScreen
import com.example.ui.theme.LocalCompactMode
import com.example.ui.theme.RecipePocketTheme
import com.example.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val baristaTheme by viewModel.baristaTheme.collectAsStateWithLifecycle()
            val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()

            RecipePocketTheme(
                baristaTheme = baristaTheme,
                themeMode = themeMode
            ) {
                RecipePocketAppContent(viewModel = viewModel)
            }
        }
    }
}

sealed interface AppDestination {
    data class MainTab(val screen: NavScreen) : AppDestination
    data class RecipeDetail(val recipeId: String) : AppDestination
    data class Toolkit(val tabIndex: Int = 0) : AppDestination
    data class Studio(val editRecipeId: String? = null) : AppDestination
    object Settings : AppDestination
}

@Composable
fun RecipePocketAppContent(viewModel: MainViewModel) {
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val allRecipes by viewModel.allRecipes.collectAsStateWithLifecycle()
    val filteredRecipes by viewModel.filteredRecipes.collectAsStateWithLifecycle()
    val favoriteRecipes by viewModel.favoriteRecipes.collectAsStateWithLifecycle()
    val savedRecipes by viewModel.savedRecipes.collectAsStateWithLifecycle()
    val extractionLogs by viewModel.extractionLogs.collectAsStateWithLifecycle()
    val baristaNotes by viewModel.baristaNotes.collectAsStateWithLifecycle()
    val reminders by viewModel.reminders.collectAsStateWithLifecycle()
    val currentTheme by viewModel.baristaTheme.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val measurementUnit by viewModel.measurementUnit.collectAsStateWithLifecycle()
    val hapticsEnabled by viewModel.hapticsEnabled.collectAsStateWithLifecycle()
    val soundEnabled by viewModel.soundEnabled.collectAsStateWithLifecycle()
    val defaultRatio by viewModel.defaultRatio.collectAsStateWithLifecycle()
    val compactMode by viewModel.compactMode.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isSmallScreen = configuration.screenWidthDp < 380 || configuration.screenHeightDp < 720
    val effectiveCompact = compactMode || isSmallScreen

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsStateWithLifecycle()
    val selectedBrewMethod by viewModel.selectedBrewMethod.collectAsStateWithLifecycle()

    // Espresso Timer States
    val espressoSeconds by viewModel.espressoTimerSeconds.collectAsStateWithLifecycle()
    val isEspressoRunning by viewModel.espressoIsRunning.collectAsStateWithLifecycle()
    val targetSeconds by viewModel.espressoTargetSeconds.collectAsStateWithLifecycle()
    val targetYield by viewModel.espressoTargetYield.collectAsStateWithLifecycle()
    val espressoDose by viewModel.espressoDose.collectAsStateWithLifecycle()

    var currentDestination by remember { mutableStateOf<AppDestination>(AppDestination.MainTab(NavScreen.HOME)) }
    var currentMainScreen by remember { mutableStateOf(NavScreen.HOME) }

    // ONBOARDING FIRST RUN CHECK
    if (!userProfile.isOnboarded) {
        CompositionLocalProvider(LocalCompactMode provides effectiveCompact) {
            OnboardingScreen(
                onComplete = { profile ->
                    viewModel.updateUserProfile(profile)
                    viewModel.completeOnboarding()
                }
            )
        }
        return
    }

    CompositionLocalProvider(LocalCompactMode provides effectiveCompact) {
        // BACK HANDLER
        BackHandler(enabled = currentDestination !is AppDestination.MainTab || currentMainScreen != NavScreen.HOME) {
        when (currentDestination) {
            is AppDestination.RecipeDetail,
            is AppDestination.Toolkit,
            is AppDestination.Studio,
            AppDestination.Settings -> {
                currentDestination = AppDestination.MainTab(currentMainScreen)
            }
            is AppDestination.MainTab -> {
                if (currentMainScreen != NavScreen.HOME) {
                    currentMainScreen = NavScreen.HOME
                    currentDestination = AppDestination.MainTab(NavScreen.HOME)
                }
            }
        }
    }

    val isRootTab = currentDestination is AppDestination.MainTab

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isRootTab) {
                FloatingBottomNavBar(
                    currentScreen = currentMainScreen,
                    onNavigate = { screen ->
                        currentMainScreen = screen
                        currentDestination = AppDestination.MainTab(screen)
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedContent(
                targetState = currentDestination,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "destination_transition"
            ) { destination ->
                when (destination) {
                    is AppDestination.MainTab -> {
                        when (destination.screen) {
                            NavScreen.HOME -> HomeScreen(
                                userProfile = userProfile,
                                allRecipes = allRecipes,
                                onRecipeClick = { recipeId ->
                                    viewModel.recordRecipeView(recipeId)
                                    currentDestination = AppDestination.RecipeDetail(recipeId)
                                },
                                onNavigateToToolkit = { tabIndex ->
                                    currentDestination = AppDestination.Toolkit(tabIndex)
                                },
                                onNavigateToSettings = {
                                    currentDestination = AppDestination.Settings
                                },
                                onNavigateToExplore = { cat ->
                                    if (cat != null) viewModel.setSelectedCategory(cat)
                                    currentMainScreen = NavScreen.EXPLORE
                                    currentDestination = AppDestination.MainTab(NavScreen.EXPLORE)
                                },
                                onToggleFavorite = { id, currentVal ->
                                    viewModel.toggleFavorite(id, currentVal)
                                },
                                onToggleSaveForLater = { id, currentVal ->
                                    viewModel.toggleSavedForLater(id, currentVal)
                                }
                            )
                            NavScreen.EXPLORE -> ExploreScreen(
                                recipes = filteredRecipes,
                                searchQuery = searchQuery,
                                selectedCategory = selectedCategory,
                                selectedDifficulty = selectedDifficulty,
                                selectedBrewMethod = selectedBrewMethod,
                                onSearchChange = { viewModel.setSearchQuery(it) },
                                onCategorySelect = { viewModel.setSelectedCategory(it) },
                                onDifficultySelect = { viewModel.setSelectedDifficulty(it) },
                                onBrewMethodSelect = { viewModel.setSelectedBrewMethod(it) },
                                onRecipeClick = { recipeId ->
                                    viewModel.recordRecipeView(recipeId)
                                    currentDestination = AppDestination.RecipeDetail(recipeId)
                                },
                                onToggleFavorite = { id, currentVal ->
                                    viewModel.toggleFavorite(id, currentVal)
                                }
                            )
                            NavScreen.CREATE -> StudioScreen(
                                initialRecipe = null,
                                onSaveRecipe = { newRecipe ->
                                    viewModel.saveRecipe(newRecipe)
                                    currentMainScreen = NavScreen.HOME
                                    currentDestination = AppDestination.MainTab(NavScreen.HOME)
                                },
                                onNavigateBack = {
                                    currentMainScreen = NavScreen.HOME
                                    currentDestination = AppDestination.MainTab(NavScreen.HOME)
                                }
                            )
                            NavScreen.SAVED -> NotesAndSavedScreen(
                                savedRecipes = savedRecipes,
                                favoriteRecipes = favoriteRecipes,
                                notes = baristaNotes,
                                reminders = reminders,
                                onRecipeClick = { recipeId ->
                                    viewModel.recordRecipeView(recipeId)
                                    currentDestination = AppDestination.RecipeDetail(recipeId)
                                },
                                onToggleFavorite = { id, currentVal ->
                                    viewModel.toggleFavorite(id, currentVal)
                                },
                                onToggleSaveForLater = { id, currentVal ->
                                    viewModel.toggleSavedForLater(id, currentVal)
                                },
                                onSaveNote = { viewModel.saveNote(it) },
                                onToggleNotePinned = { id, currentVal -> viewModel.toggleNotePinned(id, currentVal) },
                                onDeleteNote = { viewModel.deleteNote(it) },
                                onSaveReminder = { viewModel.saveReminder(it) },
                                onToggleReminder = { id, currentVal -> viewModel.toggleReminder(id, currentVal) },
                                onDeleteReminder = { viewModel.deleteReminder(it) }
                            )
                            NavScreen.PROFILE -> ProfileScreen(
                                userProfile = userProfile,
                                allRecipes = allRecipes,
                                extractionLogs = extractionLogs,
                                notesCount = baristaNotes.size,
                                onUpdateProfile = { viewModel.updateUserProfile(it) },
                                onNavigateToSettings = { currentDestination = AppDestination.Settings }
                            )
                        }
                    }
                    is AppDestination.RecipeDetail -> {
                        val recipe = allRecipes.firstOrNull { it.id == destination.recipeId }
                        if (recipe != null) {
                            RecipeDetailScreen(
                                recipe = recipe,
                                measurementUnit = measurementUnit,
                                onBackClick = {
                                    currentDestination = AppDestination.MainTab(currentMainScreen)
                                },
                                onToggleFavorite = { id, currentVal ->
                                    viewModel.toggleFavorite(id, currentVal)
                                },
                                onToggleSaveForLater = { id, currentVal ->
                                    viewModel.toggleSavedForLater(id, currentVal)
                                },
                                onLogBrewClick = { r ->
                                    currentDestination = AppDestination.Toolkit(5) // jump to journal tab
                                },
                                onEditRecipeClick = { r ->
                                    currentDestination = AppDestination.Studio(r.id)
                                }
                            )
                        } else {
                            currentDestination = AppDestination.MainTab(currentMainScreen)
                        }
                    }
                    is AppDestination.Toolkit -> {
                        ToolkitScreen(
                            initialTab = destination.tabIndex,
                            onBackClick = {
                                currentDestination = AppDestination.MainTab(currentMainScreen)
                            },
                            espressoSeconds = espressoSeconds,
                            isEspressoRunning = isEspressoRunning,
                            targetSeconds = targetSeconds,
                            targetYield = targetYield,
                            espressoDose = espressoDose,
                            onStartEspressoTimer = { viewModel.startEspressoTimer() },
                            onPauseEspressoTimer = { viewModel.pauseEspressoTimer() },
                            onResetEspressoTimer = { viewModel.resetEspressoTimer() },
                            onSetTargetSeconds = { viewModel.setEspressoTargetSeconds(it) },
                            onSetTargetYield = { viewModel.setEspressoTargetYield(it) },
                            onSetEspressoDose = { viewModel.setEspressoDose(it) },
                            extractionLogs = extractionLogs,
                            onAddExtractionLog = { viewModel.addExtractionLog(it) },
                            onDeleteExtractionLog = { viewModel.deleteExtractionLog(it) }
                        )
                    }
                    is AppDestination.Studio -> {
                        val recipeToEdit = allRecipes.firstOrNull { it.id == destination.editRecipeId }
                        StudioScreen(
                            initialRecipe = recipeToEdit,
                            onSaveRecipe = { editedRecipe ->
                                viewModel.saveRecipe(editedRecipe)
                                currentDestination = AppDestination.MainTab(currentMainScreen)
                            },
                            onNavigateBack = {
                                currentDestination = AppDestination.MainTab(currentMainScreen)
                            }
                        )
                    }
                    AppDestination.Settings -> {
                            SettingsScreen(
                                currentTheme = currentTheme,
                                themeMode = themeMode,
                                measurementUnit = measurementUnit,
                                hapticsEnabled = hapticsEnabled,
                                soundEnabled = soundEnabled,
                                defaultRatio = defaultRatio,
                                compactMode = compactMode,
                                onBackClick = {
                                    currentDestination = AppDestination.MainTab(currentMainScreen)
                                },
                                onThemeChange = { viewModel.setBaristaTheme(it) },
                                onModeChange = { viewModel.setThemeMode(it) },
                                onUnitChange = { viewModel.setMeasurementUnit(it) },
                                onHapticsToggle = { viewModel.setHapticsEnabled(it) },
                                onSoundToggle = { viewModel.setSoundEnabled(it) },
                                onDefaultRatioChange = { viewModel.setDefaultRatio(it) },
                                onCompactModeToggle = { viewModel.setCompactMode(it) },
                                onExportPdf = { viewModel.exportPdfInvoice(context) },
                                onExportCsv = { viewModel.exportCsvInvoice(context) },
                                onExportTxt = { viewModel.exportTxtInvoice(context) },
                                onExportData = { viewModel.exportDataJson() },
                                onImportData = { viewModel.importDataJson(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}
