package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.R
import com.example.model.ExtractionLogEntity
import com.example.ui.components.BaristaCard
import com.example.ui.components.PillButton
import com.example.ui.components.StarRatingBar
import com.example.ui.components.TagChip
import com.example.ui.theme.BaristaPalette
import com.example.workers.ReminderWorker
import kotlinx.coroutines.delay
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun ToolkitScreen(
    initialTab: Int = 0,
    onBackClick: () -> Unit,
    // Espresso timer from ViewModel
    espressoSeconds: Float,
    isEspressoRunning: Boolean,
    targetSeconds: Float,
    targetYield: Float,
    espressoDose: Float,
    onStartEspressoTimer: () -> Unit,
    onPauseEspressoTimer: () -> Unit,
    onResetEspressoTimer: () -> Unit,
    onSetTargetSeconds: (Float) -> Unit,
    onSetTargetYield: (Float) -> Unit,
    onSetEspressoDose: (Float) -> Unit,
    // Extraction Logs
    extractionLogs: List<ExtractionLogEntity>,
    onAddExtractionLog: (ExtractionLogEntity) -> Unit,
    onDeleteExtractionLog: (Long) -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(initialTab) }
    val tabTitles = listOf(
        "Shot Timer",
        "Ratio Calc",
        "Pour-Over",
        "Cold Brew",
        "Converter",
        "Journal",
        "Guides"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .testTag("toolkit_screen")
    ) {
        // TOP BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .testTag("btn_back_toolkit")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Barista Toolkit",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Dial-in, calibrate and track extractions",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // SCROLLABLE TABS
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tabTitles.indices.toList()) { index ->
                TagChip(
                    text = tabTitles[index],
                    isSelected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier.testTag("tab_${tabTitles[index].replace(" ", "_")}")
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // TAB CONTENT
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTabIndex) {
                0 -> EspressoTimerTab(
                    seconds = espressoSeconds,
                    isRunning = isEspressoRunning,
                    targetSeconds = targetSeconds,
                    targetYield = targetYield,
                    dose = espressoDose,
                    onStart = onStartEspressoTimer,
                    onPause = onPauseEspressoTimer,
                    onReset = onResetEspressoTimer,
                    onTargetSecondsChange = onSetTargetSeconds,
                    onTargetYieldChange = onSetTargetYield,
                    onDoseChange = onSetEspressoDose,
                    onSaveToJournal = {
                        val log = ExtractionLogEntity(
                            coffeeBean = "Specialty Espresso Blend",
                            roaster = "Artisan Roasters",
                            doseGrams = espressoDose,
                            yieldGrams = targetYield,
                            brewTimeSeconds = espressoSeconds,
                            grindSetting = "Fine #14",
                            ratioText = "1:${String.format(Locale.US, "%.2f", if (espressoDose > 0) targetYield / espressoDose else 2.0f)}",
                            rating = 5,
                            tastingNotes = "Chocolate fudge, sweet red cherry, velvety crema."
                        )
                        onAddExtractionLog(log)
                    }
                )
                1 -> RatioCalculatorTab()
                2 -> PourOverTimerTab()
                3 -> ColdBrewTrackerTab()
                4 -> UnitConverterTab()
                5 -> ExtractionJournalTab(
                    logs = extractionLogs,
                    onAddLog = onAddExtractionLog,
                    onDeleteLog = onDeleteExtractionLog
                )
                6 -> BaristaGuidesTab()
            }
        }
    }
}

// =========================================================================
// 1. ESPRESSO SHOT TIMER TAB
// =========================================================================
@Composable
fun EspressoTimerTab(
    seconds: Float,
    isRunning: Boolean,
    targetSeconds: Float,
    targetYield: Float,
    dose: Float,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    onTargetSecondsChange: (Float) -> Unit,
    onTargetYieldChange: (Float) -> Unit,
    onDoseChange: (Float) -> Unit,
    onSaveToJournal: () -> Unit
) {
    var savedSuccess by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("espresso_timer_tab"),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // CIRCULAR STOPWATCH DISPLAY
            Box(
                modifier = Modifier
                    .size(230.dp)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                val primaryColor = MaterialTheme.colorScheme.primary
                val outlineColor = MaterialTheme.colorScheme.surfaceVariant
                val progress = (seconds / targetSeconds).coerceIn(0f, 1f)

                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Background Ring
                    drawCircle(
                        color = outlineColor,
                        style = Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round)
                    )
                    // Progress Arc
                    drawArc(
                        color = primaryColor,
                        startAngle = -90f,
                        sweepAngle = progress * 360f,
                        useCenter = false,
                        style = Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = String.format(Locale.US, "%.1f", seconds),
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontSize = 54.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "SEC",
                        style = MaterialTheme.typography.labelSmall,
                        letterSpacing = 2.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Target: ${targetSeconds.toInt()}s",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // START / PAUSE / RESET CONTROLS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onReset,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .testTag("btn_reset_espresso")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Reset Timer",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                PillButton(
                    text = if (isRunning) "Stop Extraction" else "Start Shot",
                    icon = if (isRunning) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                    onClick = { if (isRunning) onPause() else onStart() },
                    modifier = Modifier
                        .width(180.dp)
                        .height(54.dp),
                    testTag = "btn_start_espresso"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // DIAL-IN TARGETS & RATIO CARD
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Extraction Dial-In Parameters",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        val currentRatio = if (dose > 0) targetYield / dose else 2.0f
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "Ratio 1:${String.format(Locale.US, "%.2f", currentRatio)}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Target Seconds Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Target Extraction Time", style = MaterialTheme.typography.bodyMedium)
                        Text("${targetSeconds.toInt()} seconds", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = targetSeconds,
                        onValueChange = onTargetSecondsChange,
                        valueRange = 15f..45f,
                        steps = 29,
                        colors = SliderDefaults.colors(thumbColor = MaterialTheme.colorScheme.primary, activeTrackColor = MaterialTheme.colorScheme.primary)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Dose Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Dose (Coffee Grounds)", style = MaterialTheme.typography.bodyMedium)
                        Text("${String.format(Locale.US, "%.1f", dose)} g", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = dose,
                        onValueChange = onDoseChange,
                        valueRange = 14f..24f,
                        steps = 19,
                        colors = SliderDefaults.colors(thumbColor = MaterialTheme.colorScheme.primary, activeTrackColor = MaterialTheme.colorScheme.primary)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Yield Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Target Liquid Yield", style = MaterialTheme.typography.bodyMedium)
                        Text("${String.format(Locale.US, "%.1f", targetYield)} g", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = targetYield,
                        onValueChange = onTargetYieldChange,
                        valueRange = 25f..60f,
                        steps = 34,
                        colors = SliderDefaults.colors(thumbColor = MaterialTheme.colorScheme.primary, activeTrackColor = MaterialTheme.colorScheme.primary)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Log Button
            if (seconds > 3f) {
                PillButton(
                    text = if (savedSuccess) "Logged to Journal!" else "Save Shot to Extraction Journal",
                    icon = if (savedSuccess) Icons.Filled.Check else Icons.Filled.MenuBook,
                    onClick = {
                        onSaveToJournal()
                        savedSuccess = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "btn_quick_save_shot"
                )
            }
        }
    }
}

// =========================================================================
// 2. BREW RATIO CALCULATOR TAB
// =========================================================================
@Composable
fun RatioCalculatorTab() {
    var coffeeDoseText by remember { mutableStateOf("15.0") }
    var waterYieldText by remember { mutableStateOf("240.0") }
    var ratioVal by remember { mutableFloatStateOf(16.0f) }

    fun updateFromDose(dose: Float) {
        val newWater = dose * ratioVal
        waterYieldText = String.format(Locale.US, "%.1f", newWater)
    }

    fun updateFromWater(water: Float) {
        val newDose = if (ratioVal > 0) water / ratioVal else 15f
        coffeeDoseText = String.format(Locale.US, "%.1f", newDose)
    }

    fun applyPreset(ratio: Float) {
        ratioVal = ratio
        val dose = coffeeDoseText.toFloatOrNull() ?: 15f
        updateFromDose(dose)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("ratio_calculator_tab"),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 100.dp)
    ) {
        item {
            Text(
                text = "Precision Brew Ratio Calculator",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Convert doses, water volumes and brew ratios instantly.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Presets row
            Text(
                text = "Specialty Presets",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item { TagChip(text = "Espresso 1:2", isSelected = ratioVal == 2f, onClick = { applyPreset(2f) }) }
                item { TagChip(text = "AeroPress 1:13", isSelected = ratioVal == 13f, onClick = { applyPreset(13f) }) }
                item { TagChip(text = "French Press 1:12", isSelected = ratioVal == 12f, onClick = { applyPreset(12f) }) }
                item { TagChip(text = "Chemex 1:15", isSelected = ratioVal == 15f, onClick = { applyPreset(15f) }) }
                item { TagChip(text = "V60 1:16", isSelected = ratioVal == 16f, onClick = { applyPreset(16f) }) }
                item { TagChip(text = "Cold Brew 1:8", isSelected = ratioVal == 8f, onClick = { applyPreset(8f) }) }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Current Ratio Slider
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Brew Ratio", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            Text(
                                text = "1 : ${String.format(Locale.US, "%.1f", ratioVal)}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Slider(
                        value = ratioVal,
                        onValueChange = {
                            ratioVal = it
                            val dose = coffeeDoseText.toFloatOrNull() ?: 15f
                            updateFromDose(dose)
                        },
                        valueRange = 2f..20f,
                        steps = 35,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Inputs: Dose & Water
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Coffee Dose
                BaristaCard(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Coffee Dose (g)",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = coffeeDoseText,
                            onValueChange = {
                                coffeeDoseText = it
                                val d = it.toFloatOrNull()
                                if (d != null) updateFromDose(d)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            textStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                            ),
                            singleLine = true,
                            modifier = Modifier.testTag("input_coffee_dose")
                        )
                    }
                }

                // Water Yield
                BaristaCard(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Water Yield (g/ml)",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = waterYieldText,
                            onValueChange = {
                                waterYieldText = it
                                val w = it.toFloatOrNull()
                                if (w != null) updateFromWater(w)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            textStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                            ),
                            singleLine = true,
                            modifier = Modifier.testTag("input_water_yield")
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Extraction Guide snippet
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                border = null
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Extraction Guidance",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = when {
                            ratioVal <= 3f -> "Concentrated extraction for espresso, ristretto or moka pot. Requires very fine grind and high pressure."
                            ratioVal in 4f..10f -> "Immersion concentrate style for cold brew or iced bases. Requires coarse grind."
                            ratioVal in 11f..14f -> "Rich full-bodied filter brew (AeroPress or French Press). Pronounced body and cocoa notes."
                            ratioVal in 15f..17f -> "Golden cup specialty pour-over standard (V60, Chemex, Kalita). Maximizes delicate floral and citric clarity."
                            else -> "Delicate light-bodied extraction. Watch for over-extraction or bitterness."
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// =========================================================================
// 3. POUR-OVER STAGE TIMER TAB
// =========================================================================
data class PourOverStage(val name: String, val durationSec: Int, val targetWater: String, val note: String)

@Composable
fun PourOverTimerTab() {
    val stages = listOf(
        PourOverStage("Bloom & Degas", 45, "50g", "Gentle spiral pour, agitate gently to wet all grounds."),
        PourOverStage("First Pour", 45, "150g", "Pour in continuous slow clockwise circles avoiding the paper."),
        PourOverStage("Second Pour", 45, "250g", "Final pour up to target weight, swirl once for flat bed."),
        PourOverStage("Drawdown", 45, "Drain", "Allow gravity drawdown to finish. Target bed should be flat.")
    )

    var currentStageIndex by remember { mutableIntStateOf(0) }
    var stageSecondsRemaining by remember { mutableIntStateOf(stages[0].durationSec) }
    var isRunning by remember { mutableStateOf(false) }
    var totalElapsedSeconds by remember { mutableIntStateOf(0) }

    LaunchedEffect(isRunning, currentStageIndex) {
        if (isRunning) {
            while (isRunning && stageSecondsRemaining > 0) {
                delay(1000)
                stageSecondsRemaining -= 1
                totalElapsedSeconds += 1
            }
            if (stageSecondsRemaining == 0) {
                if (currentStageIndex < stages.size - 1) {
                    currentStageIndex += 1
                    stageSecondsRemaining = stages[currentStageIndex].durationSec
                } else {
                    isRunning = false
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("pourover_timer_tab"),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 100.dp)
    ) {
        item {
            Text(
                text = "Pour-Over Multi-Stage Timer",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Guided 4-stage bloom and pulse-pour extraction.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Stage Active Card
            val currentStage = stages[currentStageIndex]
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "STAGE ${currentStageIndex + 1} OF ${stages.size}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentStage.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "${stageSecondsRemaining}s",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontSize = 48.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Text(
                            text = "Target Water: ${currentStage.targetWater}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = currentStage.note,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LinearProgressIndicator(
                        progress = {
                            val totalStage = currentStage.durationSec.toFloat()
                            ((totalStage - stageSecondsRemaining) / totalStage).coerceIn(0f, 1f)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Timer controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        isRunning = false
                        currentStageIndex = 0
                        stageSecondsRemaining = stages[0].durationSec
                        totalElapsedSeconds = 0
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Reset", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Spacer(modifier = Modifier.width(16.dp))

                PillButton(
                    text = if (isRunning) "Pause Stage" else "Start Brewing",
                    icon = if (isRunning) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                    onClick = { isRunning = !isRunning },
                    modifier = Modifier.width(180.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Stages list
            Text(
                text = "All Extraction Stages",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            stages.forEachIndexed { idx, stage ->
                val isCompleted = idx < currentStageIndex
                val isCurrent = idx == currentStageIndex
                BaristaCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = if (isCurrent) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        isCompleted -> BaristaPalette.SuccessGreen
                                        isCurrent -> MaterialTheme.colorScheme.primary
                                        else -> MaterialTheme.colorScheme.surfaceVariant
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isCompleted) {
                                Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            } else {
                                Text("${idx + 1}", style = MaterialTheme.typography.labelSmall, color = if (isCurrent) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stage.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                            Text(stage.note, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                        }
                        Text("${stage.durationSec}s", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

// =========================================================================
// 4. COLD BREW TRACKER TAB
// =========================================================================
@Composable
fun ColdBrewTrackerTab() {
    val context = LocalContext.current
    var steepHours by remember { mutableIntStateOf(16) }
    var isScheduled by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("coldbrew_timer_tab"),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 100.dp)
    ) {
        item {
            Text(
                text = "Cold Brew Steeping Tracker",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Schedule background immersion notifications with WorkManager.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocalDrink,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "$steepHours Hours Steeping",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Room Temp (20°C) or Refrigerator (4°C)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(8, 12, 16, 20, 24).forEach { hours ->
                            TagChip(
                                text = "${hours}h",
                                isSelected = steepHours == hours,
                                onClick = {
                                    steepHours = hours
                                    isScheduled = false
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    PillButton(
                        text = if (isScheduled) "Steeping Scheduled in Background!" else "Schedule Cold Brew Reminder",
                        icon = if (isScheduled) Icons.Filled.Check else Icons.Filled.Notifications,
                        onClick = {
                            val request = OneTimeWorkRequestBuilder<ReminderWorker>()
                                .setInitialDelay(steepHours.toLong(), TimeUnit.HOURS)
                                .setInputData(
                                    workDataOf(
                                        "title" to "Cold Brew is Ready!",
                                        "description" to "Your $steepHours-hour cold brew has completed immersion. Time to filter and bottle!",
                                        "reminderId" to 2001
                                    )
                                )
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                            isScheduled = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Ratio & grind advice
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Cold Brew Dial-In Notes",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Ratio: 1:8 for rich concentrate (dilute 1:1 with milk or water).\n• Grind: Extra coarse (raw sea salt size) to reduce sediment and bitterness.\n• Storage: Filter twice through paper or mesh. Keep airtight refrigerated for up to 14 days.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}

// =========================================================================
// 5. UNIT CONVERTER TAB
// =========================================================================
@Composable
fun UnitConverterTab() {
    var gramsInput by remember { mutableStateOf("18.0") }
    val grams = gramsInput.toFloatOrNull() ?: 0f
    val ounces = grams * 0.035274f

    var mlInput by remember { mutableStateOf("250.0") }
    val ml = mlInput.toFloatOrNull() ?: 0f
    val flOz = ml * 0.033814f

    var celsiusInput by remember { mutableStateOf("93.0") }
    val celsius = celsiusInput.toFloatOrNull() ?: 0f
    val fahrenheit = (celsius * 9f / 5f) + 32f

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("converter_tab"),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 100.dp)
    ) {
        item {
            Text(
                text = "Barista Unit & Temperature Converter",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Seamless conversions for international recipes.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Grams <-> Ounces
            BaristaCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Weight: Grams (g) ⇄ Ounces (oz)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = gramsInput,
                            onValueChange = { gramsInput = it },
                            label = { Text("Grams (g)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.weight(1f)
                        )
                        Text("=", style = MaterialTheme.typography.titleLarge)
                        Surface(shape = RoundedCornerShape(12.dp), color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${String.format(Locale.US, "%.2f", ounces)} oz",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ML <-> FL OZ
            BaristaCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Volume: Milliliters (ml) ⇄ Fluid Ounces (fl oz)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = mlInput,
                            onValueChange = { mlInput = it },
                            label = { Text("Milliliters (ml)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.weight(1f)
                        )
                        Text("=", style = MaterialTheme.typography.titleLarge)
                        Surface(shape = RoundedCornerShape(12.dp), color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${String.format(Locale.US, "%.2f", flOz)} fl oz",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Temperature C <-> F
            BaristaCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Water Temperature: Celsius (°C) ⇄ Fahrenheit (°F)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = celsiusInput,
                            onValueChange = { celsiusInput = it },
                            label = { Text("Celsius (°C)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.weight(1f)
                        )
                        Text("=", style = MaterialTheme.typography.titleLarge)
                        Surface(shape = RoundedCornerShape(12.dp), color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${String.format(Locale.US, "%.1f", fahrenheit)} °F",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// =========================================================================
// 6. EXTRACTION JOURNAL TAB
// =========================================================================
@Composable
fun ExtractionJournalTab(
    logs: List<ExtractionLogEntity>,
    onAddLog: (ExtractionLogEntity) -> Unit,
    onDeleteLog: (Long) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("journal_tab"),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Extraction Journal", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text("${logs.size} dial-in logs recorded", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                PillButton(
                    text = "Add Log",
                    icon = Icons.Filled.Add,
                    onClick = { showAddDialog = true },
                    testTag = "btn_add_journal_log"
                )
            }
        }

        if (logs.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.MenuBook, contentDescription = null, tint = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.size(56.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("No Extraction Logs Yet", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("Record your coffee doses, yields and sensory notes.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        } else {
            items(logs, key = { it.id }) { log ->
                BaristaCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(log.coffeeBean, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text(log.roaster + if (log.roastDate.isNotBlank()) " • Roasted: ${log.roastDate}" else "", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            IconButton(
                                onClick = { onDeleteLog(log.id) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.size(18.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Stats pills
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                                Text("${log.doseGrams}g in / ${log.yieldGrams}g out", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                            Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                                Text("${log.brewTimeSeconds}s", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                            Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.primaryContainer) {
                                Text(log.grindSetting, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                        }

                        if (log.tastingNotes.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Sensory: ${log.tastingNotes}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        StarRatingBar(rating = log.rating, starSize = 16.dp)
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddExtractionLogDialog(
            onDismiss = { showAddDialog = false },
            onSave = {
                onAddLog(it)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AddExtractionLogDialog(
    onDismiss: () -> Unit,
    onSave: (ExtractionLogEntity) -> Unit
) {
    var coffeeBean by remember { mutableStateOf("") }
    var roaster by remember { mutableStateOf("") }
    var dose by remember { mutableStateOf("18.0") }
    var yield by remember { mutableStateOf("36.0") }
    var time by remember { mutableStateOf("28.0") }
    var grind by remember { mutableStateOf("Fine #12") }
    var rating by remember { mutableIntStateOf(5) }
    var tastingNotes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log New Extraction") },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = coffeeBean,
                        onValueChange = { coffeeBean = it },
                        label = { Text("Coffee Bean / Origin") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    OutlinedTextField(
                        value = roaster,
                        onValueChange = { roaster = it },
                        label = { Text("Roaster") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = dose,
                            onValueChange = { dose = it },
                            label = { Text("Dose (g)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = yield,
                            onValueChange = { yield = it },
                            label = { Text("Yield (g)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = time,
                            onValueChange = { time = it },
                            label = { Text("Time (s)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = grind,
                            onValueChange = { grind = it },
                            label = { Text("Grind") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                item {
                    OutlinedTextField(
                        value = tastingNotes,
                        onValueChange = { tastingNotes = it },
                        label = { Text("Tasting Notes") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    Text("Rating", style = MaterialTheme.typography.labelMedium)
                    StarRatingBar(rating = rating, onRatingChanged = { rating = it })
                }
            }
        },
        confirmButton = {
            PillButton(
                text = "Save Log",
                onClick = {
                    onSave(
                        ExtractionLogEntity(
                            coffeeBean = coffeeBean.ifBlank { "Specialty Roast" },
                            roaster = roaster.ifBlank { "Artisan Roasters" },
                            doseGrams = dose.toFloatOrNull() ?: 18f,
                            yieldGrams = yield.toFloatOrNull() ?: 36f,
                            brewTimeSeconds = time.toFloatOrNull() ?: 28f,
                            grindSetting = grind.ifBlank { "Medium" },
                            rating = rating,
                            tastingNotes = tastingNotes
                        )
                    )
                }
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

// =========================================================================
// 7. BARISTA GUIDES TAB
// =========================================================================
@Composable
fun BaristaGuidesTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("barista_guides_tab"),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Barista Knowledge & Calibration",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Master milk steaming, grind sizes, latte art and equipment care.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Milk Steaming Guide
        item {
            BaristaCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Milk Steaming & Microfoam Science", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "1. Air Stretching: Keep steam wand tip 2-3mm below surface until milk reaches body temp (37°C / 100°F). Listen for paper-tearing 'tchk-tchk' sounds.\n2. The Vortex: Submerge tip slightly deeper and tilt pitcher 15° to spin microfoam into silky wet paint texture.\n3. Stop Temp: Shut off steam valve at 58°C (136°F); temperature rises to 65°C (150°F).\n4. Plant Milk Tips: Oat milk steams best at 55°C; avoid overheating to prevent curdling.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Latte Art Illustrated Guide
        item {
            BaristaCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Latte Art Step-by-Step Patterns", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.img_latte_art_guide),
                        contentDescription = "Latte Art Guide",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(14.dp))
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "• Heart: Pour base to 60% full, drop spout close to center, push forward and strike through.\n• Tulip: Pour 3-5 distinct milk stacks consecutively, lifting spout between stacks.\n• Rosetta: Wiggle pitcher side to side while drifting backwards, finish with steady center strike-through.\n• Swan: Pour base rosetta wing, draw milk neck along wing side, flick heart for the head.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Grind Size Reference
        item {
            BaristaCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Grind Size Micron Spectrum", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Extra Fine (50-100 µm): Turkish Coffee (flour-like powder)\n• Fine (150-250 µm): Espresso, Moka Pot (fine table salt)\n• Medium-Fine (300-500 µm): AeroPress, V60 single cup (sand)\n• Medium (500-750 µm): Chemex, Kalita Wave, Drip\n• Coarse (800-1100 µm): French Press, Cupping\n• Extra Coarse (1200+ µm): Cold Brew (rock salt)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Equipment Maintenance Checklist
        item {
            BaristaCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Daily & Weekly Maintenance Checklist", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "✓ Daily: Purge and wipe steam wand immediately after each drink.\n✓ Daily: Grouphead backflush with water after closing.\n✓ Weekly: Chemical backflush with Cafiza espresso detergent.\n✓ Weekly: Soak portafilters and shower screens in hot detergent bath.\n✓ Monthly: Grinder burr chamber vacuum and burr alignment check.\n✓ Quarterly: Boiler descaling with citric acid formulation.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}
