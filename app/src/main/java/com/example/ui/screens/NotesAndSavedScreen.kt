package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BaristaNoteEntity
import com.example.model.BrewReminderEntity
import com.example.model.RecipeEntity
import com.example.ui.components.BaristaCard
import com.example.ui.components.PillButton
import com.example.ui.components.TagChip
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NotesAndSavedScreen(
    savedRecipes: List<RecipeEntity>,
    favoriteRecipes: List<RecipeEntity>,
    notes: List<BaristaNoteEntity>,
    reminders: List<BrewReminderEntity>,
    onRecipeClick: (String) -> Unit,
    onToggleFavorite: (String, Boolean) -> Unit,
    onToggleSaveForLater: (String, Boolean) -> Unit,
    onSaveNote: (BaristaNoteEntity) -> Unit,
    onToggleNotePinned: (Long, Boolean) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onSaveReminder: (BrewReminderEntity) -> Unit,
    onToggleReminder: (Long, Boolean) -> Unit,
    onDeleteReminder: (Long) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabNames = listOf("Saved", "Favorites", "Notes", "Reminders")

    var showAddNoteDialog by remember { mutableStateOf(false) }
    var showAddReminderDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .testTag("notes_and_saved_screen")
    ) {
        // TOP HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Saved & Notes",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Your curated bookmarks, observations and reminders",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (selectedTab == 2) {
                IconButton(
                    onClick = { showAddNoteDialog = true },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .testTag("btn_add_note")
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Note", tint = MaterialTheme.colorScheme.onPrimary)
                }
            } else if (selectedTab == 3) {
                IconButton(
                    onClick = { showAddReminderDialog = true },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .testTag("btn_add_reminder")
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Reminder", tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }

        // TABS ROW
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tabNames.indices.toList()) { index ->
                val count = when (index) {
                    0 -> savedRecipes.size
                    1 -> favoriteRecipes.size
                    2 -> notes.size
                    3 -> reminders.size
                    else -> 0
                }
                TagChip(
                    text = "${tabNames[index]} ($count)",
                    isSelected = selectedTab == index,
                    onClick = { selectedTab = index },
                    modifier = Modifier.testTag("saved_tab_${tabNames[index].lowercase()}")
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // CONTENT
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                0 -> RecipeListTab(
                    recipes = savedRecipes,
                    emptyMessage = "No saved recipes yet. Tap the bookmark icon on any recipe to save it here for quick access.",
                    onRecipeClick = onRecipeClick,
                    onToggleFavorite = onToggleFavorite
                )
                1 -> RecipeListTab(
                    recipes = favoriteRecipes,
                    emptyMessage = "No favorite recipes yet. Tap the heart icon on any recipe to add it to your favorites.",
                    onRecipeClick = onRecipeClick,
                    onToggleFavorite = onToggleFavorite
                )
                2 -> NotesTab(
                    notes = notes,
                    onTogglePinned = onToggleNotePinned,
                    onDelete = onDeleteNote
                )
                3 -> RemindersTab(
                    reminders = reminders,
                    onToggle = onToggleReminder,
                    onDelete = onDeleteReminder
                )
            }
        }
    }

    if (showAddNoteDialog) {
        AddNoteDialog(
            onDismiss = { showAddNoteDialog = false },
            onSave = {
                onSaveNote(it)
                showAddNoteDialog = false
            }
        )
    }

    if (showAddReminderDialog) {
        AddReminderDialog(
            onDismiss = { showAddReminderDialog = false },
            onSave = {
                onSaveReminder(it)
                showAddReminderDialog = false
            }
        )
    }
}

@Composable
private fun RecipeListTab(
    recipes: List<RecipeEntity>,
    emptyMessage: String,
    onRecipeClick: (String) -> Unit,
    onToggleFavorite: (String, Boolean) -> Unit
) {
    if (recipes.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Filled.Bookmark, contentDescription = null, tint = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.size(56.dp))
                Spacer(modifier = Modifier.height(14.dp))
                Text("Collection Empty", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Text(emptyMessage, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(recipes, key = { it.id }) { recipe ->
                RecipeFullCard(
                    recipe = recipe,
                    onClick = { onRecipeClick(recipe.id) },
                    onToggleFavorite = { onToggleFavorite(recipe.id, recipe.isFavorite) }
                )
            }
        }
    }
}

@Composable
private fun NotesTab(
    notes: List<BaristaNoteEntity>,
    onTogglePinned: (Long, Boolean) -> Unit,
    onDelete: (Long) -> Unit
) {
    if (notes.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Filled.Coffee, contentDescription = null, tint = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.size(56.dp))
                Spacer(modifier = Modifier.height(14.dp))
                Text("No Barista Notes", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Text("Tap + above to record cupping scores, water recipes, gear calibrations and flavor observations.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
        }
    } else {
        val sortedNotes = remember(notes) {
            notes.sortedByDescending { it.isPinned }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sortedNotes, key = { it.id }) { note ->
                BaristaCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Text(
                                        text = note.categoryTag,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = note.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Row {
                                IconButton(
                                    onClick = { onTogglePinned(note.id, note.isPinned) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = if (note.isPinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                                        contentDescription = "Pin",
                                        tint = if (note.isPinned) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                IconButton(
                                    onClick = { onDelete(note.id) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Delete",
                                        tint = MaterialTheme.colorScheme.outlineVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = note.content,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RemindersTab(
    reminders: List<BrewReminderEntity>,
    onToggle: (Long, Boolean) -> Unit,
    onDelete: (Long) -> Unit
) {
    if (reminders.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Filled.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.size(56.dp))
                Spacer(modifier = Modifier.height(14.dp))
                Text("No Active Reminders", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Text("Tap + above to schedule machine backflushing, bean degassing checks or cold brew filtration alerts.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(reminders, key = { it.id }) { reminder ->
                BaristaCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = reminder.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (reminder.description.isNotBlank()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = reminder.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = reminder.reminderType.replace("_", " "),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Switch(
                            checked = reminder.isEnabled,
                            onCheckedChange = { onToggle(reminder.id, reminder.isEnabled) },
                            colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        IconButton(
                            onClick = { onDelete(reminder.id) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.outlineVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,
    onSave: (BaristaNoteEntity) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Cupping") }

    val categories = listOf("Cupping", "Roasting", "Recipe Idea", "Gear Maintenance", "Water Recipe")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Barista Note") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Note Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Text("Category Tag", style = MaterialTheme.typography.labelMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(categories) { cat ->
                        TagChip(
                            text = cat,
                            isSelected = category == cat,
                            onClick = { category = cat }
                        )
                    }
                }

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Observations & Formula") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4
                )
            }
        },
        confirmButton = {
            PillButton(
                text = "Save Note",
                onClick = {
                    onSave(
                        BaristaNoteEntity(
                            title = title.ifBlank { "Untitled Note" },
                            content = content,
                            categoryTag = category
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

@Composable
fun AddReminderDialog(
    onDismiss: () -> Unit,
    onSave: (BrewReminderEntity) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("CLEAN_MACHINE") }

    val types = listOf(
        "CLEAN_MACHINE" to "Clean Machine",
        "COLD_BREW" to "Cold Brew Check",
        "BEAN_DEGAS" to "Bean Degas",
        "PRACTICE" to "Technique Practice"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Schedule Barista Reminder") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Reminder Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Text("Reminder Type", style = MaterialTheme.typography.labelMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(types) { (key, label) ->
                        TagChip(
                            text = label,
                            isSelected = type == key,
                            onClick = { type = key }
                        )
                    }
                }

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Instructions") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            PillButton(
                text = "Set Reminder",
                onClick = {
                    onSave(
                        BrewReminderEntity(
                            title = title.ifBlank { "Barista Reminder" },
                            description = description,
                            reminderType = type,
                            targetTimeMillis = System.currentTimeMillis() + 86400000L,
                            isEnabled = true
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
