package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RecipeEntity
import com.example.model.RecipeIngredient
import com.example.model.RecipeStep
import com.example.model.StickerItem
import com.example.ui.components.BaristaCard
import com.example.ui.components.PillButton
import com.example.ui.components.TagChip
import kotlin.math.roundToInt

@Composable
fun StudioScreen(
    initialRecipe: RecipeEntity? = null,
    onSaveRecipe: (RecipeEntity) -> Unit,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf(initialRecipe?.title ?: "") }
    var subtitle by remember { mutableStateOf(initialRecipe?.subtitle ?: "") }
    var category by remember { mutableStateOf(initialRecipe?.category ?: "Espresso & Classics") }
    var brewMethod by remember { mutableStateOf(initialRecipe?.brewMethod ?: "Espresso") }
    var difficulty by remember { mutableStateOf(initialRecipe?.difficulty ?: "Beginner") }
    var ratio by remember { mutableStateOf(initialRecipe?.recommendedRatio ?: "1:2") }
    var grindSize by remember { mutableStateOf(initialRecipe?.grindSize ?: "Fine") }
    var waterTemp by remember { mutableStateOf(initialRecipe?.waterTemperature ?: "93°C") }
    var prepTime by remember { mutableStateOf((initialRecipe?.prepTimeMinutes ?: 3).toString()) }
    var brewTime by remember { mutableStateOf((initialRecipe?.brewTimeSeconds ?: 30).toString()) }
    var description by remember { mutableStateOf(initialRecipe?.description ?: "") }
    var houseTip by remember { mutableStateOf(initialRecipe?.houseTip ?: "") }

    val ingredients = remember {
        mutableStateListOf<RecipeIngredient>().apply {
            if (initialRecipe != null) {
                addAll(initialRecipe.parseIngredients())
            } else {
                add(RecipeIngredient("Espresso Roast Coffee", 18.0f, "g", "Fine grind"))
                add(RecipeIngredient("Filtered Hot Water", 36.0f, "ml", "93°C"))
            }
        }
    }

    val steps = remember {
        mutableStateListOf<RecipeStep>().apply {
            if (initialRecipe != null) {
                addAll(initialRecipe.parseInstructions())
            } else {
                add(RecipeStep(1, "Grind & Distribute", "Grind 18g finely into portafilter. WDT and tamp level.", 20))
                add(RecipeStep(2, "Lock & Extract", "Lock grouphead and extract 36g liquid espresso in 28-30 seconds.", 30))
            }
        }
    }

    val stickers = remember {
        mutableStateListOf<StickerItem>().apply {
            if (initialRecipe != null) {
                addAll(initialRecipe.parseStickers())
            }
        }
    }

    val availableStickers = listOf(
        "☕" to "Latte Cup",
        "🫘" to "Coffee Bean",
        "✨" to "Steam Swirl",
        "⭐" to "Gold Star",
        "🌿" to "Mint Leaf",
        "🍯" to "Honey Drizzle"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .testTag("studio_screen"),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TOP TITLE & SAVE BUTTON
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (initialRecipe != null) "Edit Recipe" else "Custom Studio",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Craft and publish your signature drinks",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                PillButton(
                    text = "Save",
                    icon = Icons.Filled.Check,
                    onClick = {
                        val finalRecipe = RecipeEntity(
                            id = initialRecipe?.id ?: "custom_${System.currentTimeMillis()}",
                            title = title.ifBlank { "Signature Café Creation" },
                            subtitle = subtitle.ifBlank { "Handcrafted artisan coffee" },
                            category = category,
                            brewMethod = brewMethod,
                            description = description.ifBlank { "Specialty custom beverage crafted in RECIPEPOCKET Studio." },
                            difficulty = difficulty,
                            grindSize = grindSize,
                            recommendedRatio = ratio,
                            waterTemperature = waterTemp,
                            prepTimeMinutes = prepTime.toIntOrNull() ?: 3,
                            brewTimeSeconds = brewTime.toIntOrNull() ?: 30,
                            houseTip = houseTip,
                            equipmentJson = initialRecipe?.equipmentJson ?: "[\"Espresso Machine\", \"Precision Grinder\"]",
                            ingredientsJson = RecipeEntity.buildIngredientsJson(ingredients),
                            instructionsJson = RecipeEntity.buildInstructionsJson(steps),
                            stickersJson = RecipeEntity.buildStickersJson(stickers),
                            isCustom = true,
                            isFavorite = initialRecipe?.isFavorite ?: false,
                            isSavedForLater = initialRecipe?.isSavedForLater ?: false
                        )
                        onSaveRecipe(finalRecipe)
                        onNavigateBack()
                    },
                    testTag = "btn_save_custom_recipe"
                )
            }
        }

        // INTERACTIVE STICKER STUDIO BANNER
        item {
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Interactive Card Canvas & Stickers",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Drag, scale and decorate your custom recipe card with barista stickers.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Canvas Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primaryContainer,
                                        MaterialTheme.colorScheme.surfaceVariant
                                    )
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = title.ifBlank { "Signature Recipe" },
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "$brewMethod • $ratio • $difficulty",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }

                        // Placed stickers
                        for (idx in stickers.indices) {
                            val sticker = stickers[idx]
                            StickerView(
                                sticker = sticker,
                                onUpdate = { updated ->
                                    stickers[idx] = updated
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Sticker selector row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Add Sticker:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            availableStickers.forEach { (emoji, _) ->
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clickable {
                                            stickers.add(
                                                StickerItem(
                                                    id = "stk_${System.currentTimeMillis()}_${stickers.size}",
                                                    type = emoji,
                                                    xPercent = 0.2f + ((stickers.size % 4) * 0.15f),
                                                    yPercent = 0.3f,
                                                    scale = 1.0f,
                                                    rotationDeg = 0f
                                                )
                                            )
                                        }
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(emoji, fontSize = 18.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // BASIC RECIPE DETAILS
        item {
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Recipe Details",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Recipe Title") },
                        placeholder = { Text("e.g. Cardamom Honey Cortado") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary)
                    )

                    OutlinedTextField(
                        value = subtitle,
                        onValueChange = { subtitle = it },
                        label = { Text("Subtitle / Tagline") },
                        placeholder = { Text("e.g. Spiced espresso with microfoam") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = brewMethod,
                            onValueChange = { brewMethod = it },
                            label = { Text("Method") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = category,
                            onValueChange = { category = it },
                            label = { Text("Category") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = ratio,
                            onValueChange = { ratio = it },
                            label = { Text("Ratio (e.g. 1:2)") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = grindSize,
                            onValueChange = { grindSize = it },
                            label = { Text("Grind Size") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = waterTemp,
                            onValueChange = { waterTemp = it },
                            label = { Text("Water Temp") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = prepTime,
                            onValueChange = { prepTime = it },
                            label = { Text("Prep (min)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description & Profile") },
                        placeholder = { Text("Tasting notes, aroma profile and origin story...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )

                    OutlinedTextField(
                        value = houseTip,
                        onValueChange = { houseTip = it },
                        label = { Text("Barista House Tip") },
                        placeholder = { Text("Special technique or extraction advice...") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // INGREDIENTS BUILDER
        item {
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Ingredients (${ingredients.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = {
                                ingredients.add(RecipeIngredient("New Ingredient", 20f, "g", ""))
                            }
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Add Ingredient", tint = MaterialTheme.colorScheme.primary)
                        }
                    }

                    for (idx in ingredients.indices) {
                        val ing = ingredients[idx]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = ing.name,
                                onValueChange = { ingredients[idx] = ing.copy(name = it) },
                                label = { Text("Item") },
                                modifier = Modifier.weight(2f)
                            )
                            OutlinedTextField(
                                value = ing.amount.toString(),
                                onValueChange = {
                                    val a = it.toFloatOrNull() ?: ing.amount
                                    ingredients[idx] = ing.copy(amount = a)
                                },
                                label = { Text("Amount") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = ing.unit,
                                onValueChange = { ingredients[idx] = ing.copy(unit = it) },
                                label = { Text("Unit") },
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { if (ingredients.size > 1) ingredients.removeAt(idx) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = "Remove", tint = MaterialTheme.colorScheme.outlineVariant)
                            }
                        }
                    }
                }
            }
        }

        // INSTRUCTIONS BUILDER
        item {
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Instructions & Timers (${steps.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = {
                                steps.add(
                                    RecipeStep(
                                        stepNumber = steps.size + 1,
                                        title = "Step ${steps.size + 1}",
                                        instruction = "Describe step execution...",
                                        durationSeconds = 30
                                    )
                                )
                            }
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Add Step", tint = MaterialTheme.colorScheme.primary)
                        }
                    }

                    for (idx in steps.indices) {
                        val st = steps[idx]
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                .padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Step ${idx + 1}", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                IconButton(
                                    onClick = { if (steps.size > 1) steps.removeAt(idx) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Remove", tint = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.size(16.dp))
                                }
                            }
                            OutlinedTextField(
                                value = st.title,
                                onValueChange = { steps[idx] = st.copy(title = it) },
                                label = { Text("Title") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = st.instruction,
                                onValueChange = { steps[idx] = st.copy(instruction = it) },
                                label = { Text("Instruction") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = st.durationSeconds.toString(),
                                onValueChange = {
                                    val d = it.toIntOrNull() ?: st.durationSeconds
                                    steps[idx] = st.copy(durationSeconds = d)
                                },
                                label = { Text("Timer Duration (seconds)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StickerView(
    sticker: StickerItem,
    onUpdate: (StickerItem) -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(sticker.xPercent * 240) }
    var offsetY by remember { mutableFloatStateOf(sticker.yPercent * 100) }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .scale(sticker.scale)
            .rotate(sticker.rotationDeg)
            .pointerInput(sticker.id) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX = (offsetX + dragAmount.x).coerceIn(0f, 260f)
                    offsetY = (offsetY + dragAmount.y).coerceIn(0f, 110f)
                    onUpdate(
                        sticker.copy(
                            xPercent = offsetX / 240f,
                            yPercent = offsetY / 100f
                        )
                    )
                }
            }
    ) {
        Surface(
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.9f),
            shadowElevation = 4.dp
        ) {
            Text(
                text = sticker.type,
                fontSize = 24.sp,
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}
