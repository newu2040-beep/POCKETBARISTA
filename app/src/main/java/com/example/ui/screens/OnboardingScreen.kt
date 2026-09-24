package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.MeasurementUnit
import com.example.model.UserProfile
import com.example.ui.components.BaristaCard
import com.example.ui.components.PillButton
import com.example.ui.components.TagChip

@Composable
fun OnboardingScreen(
    onComplete: (UserProfile) -> Unit
) {
    var stepIndex by remember { mutableIntStateOf(0) }
    var displayName by remember { mutableStateOf("Artisan Barista") }
    var preferredCoffee by remember { mutableStateOf("Flat White") }
    var experienceLevel by remember { mutableStateOf("Specialty Barista") }
    var measurementUnit by remember { mutableStateOf(MeasurementUnit.METRIC) }
    var gender by remember { mutableStateOf("Not Specified") }

    val experienceLevels = listOf("Home Enthusiast", "Specialty Barista", "Head Barista", "Roaster / Q-Grader")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .testTag("onboarding_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // TOP SKIP BUTTON
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (stepIndex < 2) {
                    TextButton(
                        onClick = {
                            onComplete(
                                UserProfile(
                                    displayName = displayName,
                                    preferredCoffee = preferredCoffee,
                                    experienceLevel = experienceLevel,
                                    measurementUnit = measurementUnit,
                                    gender = gender,
                                    isOnboarded = true
                                )
                            )
                        }
                    ) {
                        Text("Skip", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            // SLIDE CONTENT
            AnimatedContent(targetState = stepIndex, label = "onboarding_step") { targetStep ->
                when (targetStep) {
                    0 -> SlideOne()
                    1 -> SlideTwo()
                    2 -> SlideThree(
                        name = displayName,
                        onNameChange = { displayName = it },
                        coffee = preferredCoffee,
                        onCoffeeChange = { preferredCoffee = it },
                        experience = experienceLevel,
                        onExperienceChange = { experienceLevel = it },
                        unit = measurementUnit,
                        onUnitChange = { measurementUnit = it },
                        experienceLevels = experienceLevels
                    )
                }
            }

            // BOTTOM NAVIGATION & INDICATORS
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Page Dots
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (i in 0..2) {
                        Box(
                            modifier = Modifier
                                .size(if (stepIndex == i) 24.dp else 8.dp, 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (stepIndex == i) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                PillButton(
                    text = if (stepIndex == 2) "Start Brewing" else "Continue",
                    icon = if (stepIndex == 2) Icons.Filled.Coffee else Icons.AutoMirrored.Filled.ArrowForward,
                    onClick = {
                        if (stepIndex < 2) {
                            stepIndex += 1
                        } else {
                            onComplete(
                                UserProfile(
                                    displayName = displayName.ifBlank { "Artisan Barista" },
                                    preferredCoffee = preferredCoffee.ifBlank { "Flat White" },
                                    experienceLevel = experienceLevel,
                                    measurementUnit = measurementUnit,
                                    gender = gender,
                                    isOnboarded = true
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    testTag = "btn_onboarding_next"
                )
            }
        }
    }
}

@Composable
private fun SlideOne() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_coffee_hero),
                contentDescription = "Coffee Hero",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Brew with Precision",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Universal 30+ specialty café recipe library, millisecond espresso shot timers, and multi-stage pour-over bloom guidance.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}

@Composable
private fun SlideTwo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_latte_art_guide),
                contentDescription = "Latte Art",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Create & Calibrate",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Design signature recipes with interactive barista stickers, track dial-in extractions in your personal tasting journal, and store everything offline.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}

@Composable
private fun SlideThree(
    name: String,
    onNameChange: (String) -> Unit,
    coffee: String,
    onCoffeeChange: (String) -> Unit,
    experience: String,
    onExperienceChange: (String) -> Unit,
    unit: MeasurementUnit,
    onUnitChange: (MeasurementUnit) -> Unit,
    experienceLevels: List<String>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Your Barista Station",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Personalize your companion app for your brewing style.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        BaristaCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text("Barista Name / Alias") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary)
                )

                OutlinedTextField(
                    value = coffee,
                    onValueChange = onCoffeeChange,
                    label = { Text("Favorite Cup") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Experience Level", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    experienceLevels.take(2).forEach { level ->
                        TagChip(
                            text = level,
                            isSelected = experience == level,
                            onClick = { onExperienceChange(level) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    experienceLevels.drop(2).forEach { level ->
                        TagChip(
                            text = level,
                            isSelected = experience == level,
                            onClick = { onExperienceChange(level) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text("Measurement Unit", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TagChip(
                        text = "Metric (g, ml)",
                        isSelected = unit == MeasurementUnit.METRIC,
                        onClick = { onUnitChange(MeasurementUnit.METRIC) },
                        modifier = Modifier.weight(1f)
                    )
                    TagChip(
                        text = "Imperial (oz)",
                        isSelected = unit == MeasurementUnit.IMPERIAL,
                        onClick = { onUnitChange(MeasurementUnit.IMPERIAL) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
