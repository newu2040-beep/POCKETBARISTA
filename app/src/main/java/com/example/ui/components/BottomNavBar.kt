package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.ui.theme.LocalCompactMode

enum class NavScreen(
    val route: String,
    val title: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
) {
    HOME("home", "Home", Icons.Filled.Home, Icons.Outlined.Home),
    EXPLORE("explore", "Explore", Icons.Filled.Search, Icons.Outlined.Search),
    CREATE("create", "Studio", Icons.Filled.AddCircle, Icons.Filled.AddCircle),
    SAVED("saved", "Saved", Icons.Filled.Bookmark, Icons.Outlined.BookmarkBorder),
    PROFILE("profile", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
}

@Composable
fun FloatingBottomNavBar(
    currentScreen: NavScreen,
    onNavigate: (NavScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val isCompact = LocalCompactMode.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(
                horizontal = if (isCompact) 8.dp else 16.dp,
                vertical = if (isCompact) 4.dp else 10.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isCompact) 54.dp else 64.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(32.dp),
                    ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                ),
            shape = RoundedCornerShape(32.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
            tonalElevation = 8.dp,
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = if (isCompact) 4.dp else 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavScreen.entries.forEach { screen ->
                    val isSelected = currentScreen == screen
                    val isStudio = screen == NavScreen.CREATE

                    if (isStudio) {
                        // Elevated central Studio button
                        Box(
                            modifier = Modifier
                                .size(if (isCompact) 42.dp else 48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = { onNavigate(screen) }
                                )
                                .testTag("nav_item_create"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = screen.filledIcon,
                                contentDescription = screen.title,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(if (isCompact) 24.dp else 28.dp)
                            )
                        }
                    } else {
                        val iconColor by animateColorAsState(
                            targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            animationSpec = tween(200),
                            label = "nav_icon_color"
                        )
                        val pillBg by animateColorAsState(
                            targetValue = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else Color.Transparent,
                            animationSpec = tween(200),
                            label = "nav_pill_bg"
                        )

                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(pillBg)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = { onNavigate(screen) }
                                )
                                .padding(
                                    horizontal = if (isCompact) 8.dp else 14.dp,
                                    vertical = if (isCompact) 4.dp else 6.dp
                                )
                                .testTag("nav_item_${screen.route}"),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = if (isSelected) screen.filledIcon else screen.outlinedIcon,
                                contentDescription = screen.title,
                                tint = iconColor,
                                modifier = Modifier.size(if (isCompact) 20.dp else 22.dp)
                            )
                            Text(
                                text = screen.title,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = iconColor,
                                fontSize = if (isCompact) 9.sp else 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
