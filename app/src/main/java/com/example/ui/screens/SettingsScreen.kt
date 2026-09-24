package com.example.ui.screens

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.model.BaristaTheme
import com.example.model.MeasurementUnit
import com.example.model.ThemeMode
import com.example.ui.components.BaristaCard
import com.example.ui.components.PillButton
import com.example.ui.components.TagChip
import com.example.ui.theme.BaristaPalette
import com.example.ui.theme.CompactDimens
import com.example.ui.theme.LocalCompactMode
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    currentTheme: BaristaTheme,
    themeMode: ThemeMode,
    measurementUnit: MeasurementUnit,
    hapticsEnabled: Boolean,
    soundEnabled: Boolean,
    defaultRatio: String,
    compactMode: Boolean,
    onBackClick: () -> Unit,
    onThemeChange: (BaristaTheme) -> Unit,
    onModeChange: (ThemeMode) -> Unit,
    onUnitChange: (MeasurementUnit) -> Unit,
    onHapticsToggle: (Boolean) -> Unit,
    onSoundToggle: (Boolean) -> Unit,
    onDefaultRatioChange: (String) -> Unit,
    onCompactModeToggle: (Boolean) -> Unit,
    onExportPdf: () -> Unit,
    onExportCsv: () -> Unit,
    onExportTxt: () -> Unit,
    onExportData: suspend () -> String,
    onImportData: suspend (String) -> Boolean
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val isCompact = LocalCompactMode.current

    var showImportDialog by remember { mutableStateOf(false) }
    var importJsonText by remember { mutableStateOf("") }
    var showExportDialog by remember { mutableStateOf(false) }
    var exportedJsonText by remember { mutableStateOf("") }

    // Real-time Permission States
    var hasNotificationPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            } else true
        )
    }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    var hasMediaPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
            } else {
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            }
        )
    }

    // Permission Launcher
    val permissionsToRequest = buildList {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
            add(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        add(Manifest.permission.CAMERA)
    }.toTypedArray()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            hasNotificationPermission = results[Manifest.permission.POST_NOTIFICATIONS] ?: hasNotificationPermission
            hasMediaPermission = results[Manifest.permission.READ_MEDIA_IMAGES] ?: hasMediaPermission
        } else {
            hasMediaPermission = results[Manifest.permission.READ_EXTERNAL_STORAGE] ?: hasMediaPermission
        }
        hasCameraPermission = results[Manifest.permission.CAMERA] ?: hasCameraPermission

        Toast.makeText(context, "Permissions updated in real time", Toast.LENGTH_SHORT).show()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .testTag("settings_screen"),
        contentPadding = PaddingValues(
            start = CompactDimens.horizontalPadding(isCompact),
            end = CompactDimens.horizontalPadding(isCompact),
            top = 8.dp,
            bottom = 100.dp
        ),
        verticalArrangement = Arrangement.spacedBy(CompactDimens.itemSpacing(isCompact))
    ) {
        // TOP APP BAR
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(if (isCompact) 36.dp else 40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .testTag("btn_settings_back")
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Settings",
                        style = if (isCompact) MaterialTheme.typography.titleLarge else MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Preferences, Invoices & Station Config",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // INVOICES & REPORTS EXPORT CENTER
        item {
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(CompactDimens.cardCorner(isCompact))
            ) {
                Column(
                    modifier = Modifier.padding(CompactDimens.cardPadding(isCompact)),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Filled.Receipt,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                "Invoices & Extraction Reports",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Generate official brew invoices & share to any platform",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Text(
                        text = "Export your certified coffee extractions, dose/yield ratios, beans consumed, and custom recipes as official invoices or data spreadsheets.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )

                    // Export Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // PDF Invoice
                        PillButton(
                            text = "PDF Invoice",
                            icon = Icons.Filled.PictureAsPdf,
                            isPrimary = true,
                            onClick = onExportPdf,
                            modifier = Modifier.weight(1f),
                            testTag = "btn_export_pdf_invoice"
                        )

                        // CSV Spreadsheet
                        PillButton(
                            text = "CSV Data",
                            icon = Icons.Filled.TableChart,
                            isPrimary = false,
                            onClick = onExportCsv,
                            modifier = Modifier.weight(1f),
                            testTag = "btn_export_csv_invoice"
                        )

                        // TXT Monospace Receipt
                        PillButton(
                            text = "TXT Receipt",
                            icon = Icons.Filled.Description,
                            isPrimary = false,
                            onClick = onExportTxt,
                            modifier = Modifier.weight(1f),
                            testTag = "btn_export_txt_invoice"
                        )
                    }

                    // Share info note
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp)
                    ) {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Files can be shared instantly via WhatsApp, Email, Drive, AirDrop, or Print.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // PERMISSIONS & HARDWARE ACCESS CENTER
        item {
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(CompactDimens.cardCorner(isCompact))
            ) {
                Column(
                    modifier = Modifier.padding(CompactDimens.cardPadding(isCompact)),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Filled.Security,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                "Station Permissions & Hardware Access",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Real-time status for notifications, camera, media & files",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Status rows
                    PermissionStatusRow(
                        title = "Notifications",
                        subtitle = "Pour-over, cold brew, and maintenance alerts",
                        icon = Icons.Filled.Notifications,
                        isGranted = hasNotificationPermission
                    )

                    PermissionStatusRow(
                        title = "Camera Access",
                        subtitle = "Capture bean origin photos & latte art directly",
                        icon = Icons.Filled.CameraAlt,
                        isGranted = hasCameraPermission
                    )

                    PermissionStatusRow(
                        title = "Photo Gallery & Media",
                        subtitle = "Select custom photos for profile and creations",
                        icon = Icons.Filled.PhotoLibrary,
                        isGranted = hasMediaPermission
                    )

                    PermissionStatusRow(
                        title = "File Export & Invoices",
                        subtitle = "Save and share PDF, CSV, and TXT documents",
                        icon = Icons.Filled.Folder,
                        isGranted = true // FileProvider based
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    PillButton(
                        text = "Request All Permissions",
                        icon = Icons.Filled.Security,
                        isPrimary = !(hasNotificationPermission && hasCameraPermission && hasMediaPermission),
                        onClick = {
                            permissionLauncher.launch(permissionsToRequest)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "btn_request_all_permissions"
                    )
                }
            }
        }

        // COMPACT MODE & RESPONSIVE DISPLAY
        item {
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(CompactDimens.cardCorner(isCompact))
            ) {
                Column(
                    modifier = Modifier.padding(CompactDimens.cardPadding(isCompact)),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Filled.AspectRatio,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    "Compact Display Mode",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    "Resizes entire UI for small-display phones & prevents overflow",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Switch(
                            checked = compactMode,
                            onCheckedChange = onCompactModeToggle,
                            colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.testTag("switch_compact_mode")
                        )
                    }

                    if (compactMode || isCompact) {
                        Text(
                            text = "✓ Compact mode active: All screen edges, cards, paddings, and font sizes are dynamically scaled for your phone display.",
                            style = MaterialTheme.typography.labelSmall,
                            color = BaristaPalette.SuccessGreen
                        )
                    }
                }
            }
        }

        // BARISTA THEMES
        item {
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(CompactDimens.cardCorner(isCompact))
            ) {
                Column(
                    modifier = Modifier.padding(CompactDimens.cardPadding(isCompact)),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.ColorLens, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Barista Themes", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Text("Select a color palette inspired by specialty coffee culture.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        BaristaTheme.entries.forEach { theme ->
                            ThemeItem(
                                theme = theme,
                                isSelected = currentTheme == theme,
                                onClick = { onThemeChange(theme) }
                            )
                        }
                    }
                }
            }
        }

        // APPEARANCE & MODE
        item {
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(CompactDimens.cardCorner(isCompact))
            ) {
                Column(
                    modifier = Modifier.padding(CompactDimens.cardPadding(isCompact)),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Brightness4, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Appearance Mode", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ThemeMode.entries.forEach { mode ->
                            TagChip(
                                text = mode.label,
                                isSelected = themeMode == mode,
                                onClick = { onModeChange(mode) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // BREWING PREFERENCES
        item {
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(CompactDimens.cardCorner(isCompact))
            ) {
                Column(
                    modifier = Modifier.padding(CompactDimens.cardPadding(isCompact)),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Brewing Defaults", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                    // Measurement Units
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Measurement Units", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            Text("Units for recipes, calculators and logs", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            MeasurementUnit.entries.forEach { unit ->
                                TagChip(
                                    text = if (unit == MeasurementUnit.METRIC) "Metric" else "Imperial",
                                    isSelected = measurementUnit == unit,
                                    onClick = { onUnitChange(unit) }
                                )
                            }
                        }
                    }

                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                    // Default Ratio
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Default Pour-Over Ratio", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            Text("Standard coffee-to-water baseline", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf("1:15", "1:16", "1:17").forEach { r ->
                                TagChip(
                                    text = r,
                                    isSelected = defaultRatio == r,
                                    onClick = { onDefaultRatioChange(r) }
                                )
                            }
                        }
                    }
                }
            }
        }

        // FEEDBACK & ALERTS
        item {
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(CompactDimens.cardCorner(isCompact))
            ) {
                Column(
                    modifier = Modifier.padding(CompactDimens.cardPadding(isCompact)),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Haptics & Alerts", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Vibration, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("Haptic Feedback", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                                Text("Vibrate when timer milestones complete", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        Switch(
                            checked = hapticsEnabled,
                            onCheckedChange = onHapticsToggle,
                            colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary)
                        )
                    }

                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.VolumeUp, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("Timer Audio Alert", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                                Text("Play soft chime when extractions finish", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        Switch(
                            checked = soundEnabled,
                            onCheckedChange = onSoundToggle,
                            colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }
        }

        // LOCAL DATA BACKUP & RESTORE
        item {
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(CompactDimens.cardCorner(isCompact))
            ) {
                Column(
                    modifier = Modifier.padding(CompactDimens.cardPadding(isCompact)),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Local Backup & Restore", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(
                        text = "Export your custom recipes, extraction logs and notes to JSON text. Restore anytime without cloud accounts.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        PillButton(
                            text = "Export JSON",
                            icon = Icons.Filled.FileUpload,
                            isPrimary = true,
                            onClick = {
                                scope.launch {
                                    val json = onExportData()
                                    exportedJsonText = json
                                    showExportDialog = true
                                }
                            },
                            modifier = Modifier.weight(1f),
                            testTag = "btn_export_json"
                        )

                        PillButton(
                            text = "Import JSON",
                            icon = Icons.Filled.FileDownload,
                            isPrimary = false,
                            onClick = { showImportDialog = true },
                            modifier = Modifier.weight(1f),
                            testTag = "btn_import_json"
                        )
                    }
                }
            }
        }

        // PRIVACY & ABOUT
        item {
            BaristaCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(CompactDimens.cardCorner(isCompact))
            ) {
                Column(
                    modifier = Modifier.padding(CompactDimens.cardPadding(isCompact)),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Security, contentDescription = null, tint = BaristaPalette.SuccessGreen, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("100% Offline & Private", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = "RECIPEPOCKET operates entirely offline on your device using a local Room SQLite database. None of your brewing logs, recipes, profile details or photos leave your phone.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )

                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("RECIPEPOCKET", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                            Text("Brew. Create. Remember.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text("v1.0.0", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outlineVariant)
                    }
                }
            }
        }
    }

    // EXPORT DIALOG
    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = { Text("Backup Data Export") },
            text = {
                Column {
                    Text("Your recipes, extraction logs and notes have been generated into JSON format:", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = exportedJsonText,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        textStyle = MaterialTheme.typography.bodySmall
                    )
                }
            },
            confirmButton = {
                PillButton(
                    text = "Copy to Clipboard",
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("RECIPEPOCKET Backup", exportedJsonText)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "Copied backup JSON to clipboard!", Toast.LENGTH_SHORT).show()
                        showExportDialog = false
                    }
                )
            },
            dismissButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    // IMPORT DIALOG
    if (showImportDialog) {
        AlertDialog(
            onDismissRequest = { showImportDialog = false },
            title = { Text("Restore From JSON Backup") },
            text = {
                Column {
                    Text("Paste previously exported RECIPEPOCKET backup JSON string below:", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = importJsonText,
                        onValueChange = { importJsonText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        textStyle = MaterialTheme.typography.bodySmall,
                        placeholder = { Text("{\"version\":1,\"appName\":\"RECIPEPOCKET\",...}") }
                    )
                }
            },
            confirmButton = {
                PillButton(
                    text = "Restore",
                    onClick = {
                        scope.launch {
                            val success = onImportData(importJsonText)
                            if (success) {
                                Toast.makeText(context, "Data restored successfully!", Toast.LENGTH_SHORT).show()
                                showImportDialog = false
                            } else {
                                Toast.makeText(context, "Invalid backup format", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
            },
            dismissButton = {
                TextButton(onClick = { showImportDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun PermissionStatusRow(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isGranted: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (isGranted) BaristaPalette.SuccessGreen else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isGranted) BaristaPalette.SuccessGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f),
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    if (isGranted) Icons.Filled.CheckCircle else Icons.Filled.Warning,
                    contentDescription = null,
                    tint = if (isGranted) BaristaPalette.SuccessGreen else MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isGranted) "Granted" else "Required",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (isGranted) BaristaPalette.SuccessGreen else MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun ThemeItem(
    theme: BaristaTheme,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor by animateColorAsState(
        if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
        label = "theme_border"
    )

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        modifier = Modifier
            .fillMaxWidth()
            .border(if (isSelected) 1.5.dp else 0.dp, borderColor, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Color swatches preview
                val paletteColors = when (theme) {
                    BaristaTheme.ESPRESSO -> listOf(Color(0xFFC89567), Color(0xFF211510))
                    BaristaTheme.VANILLA_CREAM -> listOf(Color(0xFFE2C290), Color(0xFFFBF8F3))
                    BaristaTheme.MATCHA -> listOf(Color(0xFF5B8A61), Color(0xFF263D2A))
                    BaristaTheme.CARAMEL -> listOf(Color(0xFFDE9343), Color(0xFF2A1C0E))
                    BaristaTheme.MOCHA -> listOf(Color(0xFFB87D65), Color(0xFF1E1410))
                    BaristaTheme.MIDNIGHT_COFFEE -> listOf(Color(0xFFFF9E45), Color(0xFF0F0E0C))
                    BaristaTheme.ROSE_LATTE -> listOf(Color(0xFFD47A88), Color(0xFF281318))
                }

                Row(horizontalArrangement = Arrangement.spacedBy((-6).dp)) {
                    paletteColors.forEach { col ->
                        Surface(
                            shape = CircleShape,
                            color = col,
                            shadowElevation = 2.dp,
                            modifier = Modifier
                                .size(20.dp)
                                .border(1.5.dp, Color.White.copy(alpha = 0.4f), CircleShape)
                        ) {}
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(theme.displayName, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    Text(theme.subtitle, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            if (isSelected) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
