package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.RecipeEntity
import com.example.model.UserProfile
import com.example.ui.components.BaristaCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.LocalCompactMode
import java.util.Calendar

@Composable
fun HomeScreen(
    userProfile: UserProfile,
    allRecipes: List<RecipeEntity>,
    onRecipeClick: (String) -> Unit,
    onNavigateToToolkit: (Int) -> Unit, // tab index
    onNavigateToSettings: () -> Unit,
    onNavigateToExplore: (String?) -> Unit,
    onToggleFavorite: (String, Boolean) -> Unit,
    onToggleSaveForLater: (String, Boolean) -> Unit
) {
    val isCompact = LocalCompactMode.current
    val horizPadding = if (isCompact) 12.dp else 20.dp

    val greeting = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when (hour) {
            in 5..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }

    val featuredRecipe = remember(allRecipes) {
        allRecipes.firstOrNull { it.id == "flat_white" } ?: allRecipes.firstOrNull()
    }

    val topPicks = remember(allRecipes) {
        allRecipes.filter { it.isFavorite || it.viewCount > 0 }.take(6)
    }

    val manualBrews = remember(allRecipes) {
        allRecipes.filter { it.category == "Manual Brew" }.take(6)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // TOP APP BAR / GREETING
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = horizPadding, vertical = if (isCompact) 8.dp else 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(if (isCompact) 36.dp else 44.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.secondary
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userProfile.displayName.take(1).uppercase(),
                            style = if (isCompact) MaterialTheme.typography.titleSmall else MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(if (isCompact) 8.dp else 12.dp))
                    Column {
                        Text(
                            text = greeting,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = userProfile.displayName,
                            style = if (isCompact) MaterialTheme.typography.titleMedium else MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Row {
                    IconButton(
                        onClick = { onNavigateToToolkit(0) },
                        modifier = Modifier
                            .size(if (isCompact) 36.dp else 40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                            .testTag("btn_quick_timer")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Timer,
                            contentDescription = "Espresso Timer",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(if (isCompact) 18.dp else 20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(if (isCompact) 4.dp else 8.dp))
                    IconButton(
                        onClick = onNavigateToSettings,
                        modifier = Modifier
                            .size(if (isCompact) 36.dp else 40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                            .testTag("btn_settings")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(if (isCompact) 18.dp else 20.dp)
                        )
                    }
                }
            }
        }

        // HERO FEATURED RECIPE BANNER
        item {
            if (featuredRecipe != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizPadding, vertical = 6.dp)
                        .clip(RoundedCornerShape(if (isCompact) 18.dp else 26.dp))
                        .clickable { onRecipeClick(featuredRecipe.id) }
                        .testTag("hero_featured_card"),
                    shape = RoundedCornerShape(if (isCompact) 18.dp else 26.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth().height(if (isCompact) 155.dp else 210.dp)) {
                        // Hero background image
                        Image(
                            painter = painterResource(id = R.drawable.img_coffee_hero),
                            contentDescription = "Featured Brew",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        // Gradient Overlay for text contrast
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.45f),
                                            Color.Black.copy(alpha = 0.85f)
                                        )
                                    )
                                )
                        )

                        // Top Badges
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                            ) {
                                Text(
                                    text = "FEATURED EXTRACTION",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }

                            Surface(
                                shape = CircleShape,
                                color = Color.Black.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable { onToggleFavorite(featuredRecipe.id, featuredRecipe.isFavorite) }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = if (featuredRecipe.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Favorite",
                                        tint = if (featuredRecipe.isFavorite) Color(0xFFFF5252) else Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        // Bottom Info
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = featuredRecipe.title,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = featuredRecipe.subtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.85f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                BrewMetaTag(icon = Icons.Filled.AccessTime, text = "${featuredRecipe.prepTimeMinutes}m")
                                BrewMetaTag(icon = Icons.Filled.Scale, text = featuredRecipe.recommendedRatio)
                                BrewMetaTag(icon = Icons.Filled.Coffee, text = featuredRecipe.difficulty)
                            }
                        }
                    }
                }
            }
        }

        // QUICK TOOLKIT SHORTCUTS
        item {
            Spacer(modifier = Modifier.height(12.dp))
            SectionHeader(
                title = "Barista Toolkit",
                subtitle = "Precision calibration & brew timers",
                actionText = "Open All",
                onActionClick = { onNavigateToToolkit(0) }
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = horizPadding, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    QuickToolCard(
                        title = "Shot Timer",
                        subtitle = "Espresso",
                        icon = Icons.Filled.Timer,
                        onClick = { onNavigateToToolkit(0) },
                        testTag = "tool_card_shot_timer"
                    )
                }
                item {
                    QuickToolCard(
                        title = "Ratio Calc",
                        subtitle = "Coffee/Water",
                        icon = Icons.Filled.Scale,
                        onClick = { onNavigateToToolkit(1) },
                        testTag = "tool_card_ratio"
                    )
                }
                item {
                    QuickToolCard(
                        title = "Pour-Over",
                        subtitle = "Stage Timer",
                        icon = Icons.Filled.Coffee,
                        onClick = { onNavigateToToolkit(2) },
                        testTag = "tool_card_pourover"
                    )
                }
                item {
                    QuickToolCard(
                        title = "Cold Brew",
                        subtitle = "Steep Tracker",
                        icon = Icons.Filled.LocalDrink,
                        onClick = { onNavigateToToolkit(3) },
                        testTag = "tool_card_coldbrew"
                    )
                }
                item {
                    QuickToolCard(
                        title = "Journal",
                        subtitle = "Extraction Log",
                        icon = Icons.Filled.MenuBook,
                        onClick = { onNavigateToToolkit(5) },
                        testTag = "tool_card_journal"
                    )
                }
                item {
                    QuickToolCard(
                        title = "Guides",
                        subtitle = "Latte & Grind",
                        icon = Icons.Filled.Build,
                        onClick = { onNavigateToToolkit(6) },
                        testTag = "tool_card_guides"
                    )
                }
            }
        }

        // POPULAR SPECIALTY RECIPES
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = "Barista Essentials",
                subtitle = "Foundational espresso & milk craft",
                actionText = "See All",
                onActionClick = { onNavigateToExplore("Espresso & Classics") }
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = horizPadding, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(topPicks) { recipe ->
                    RecipeCompactCard(
                        recipe = recipe,
                        onClick = { onRecipeClick(recipe.id) },
                        onToggleFavorite = { onToggleFavorite(recipe.id, recipe.isFavorite) }
                    )
                }
            }
        }

        // MANUAL BREW HIGHLIGHTS
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = "Manual Pour-Over & Drip",
                subtitle = "Artisan clarity and single-origin brewing",
                actionText = "Explore",
                onActionClick = { onNavigateToExplore("Manual Brew") }
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = horizPadding, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(manualBrews) { recipe ->
                    RecipeCompactCard(
                        recipe = recipe,
                        onClick = { onRecipeClick(recipe.id) },
                        onToggleFavorite = { onToggleFavorite(recipe.id, recipe.isFavorite) }
                    )
                }
            }
        }

        // TAGLINE BANNER AT BOTTOM
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizPadding)
                    .clip(RoundedCornerShape(if (isCompact) 16.dp else 20.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(vertical = if (isCompact) 12.dp else 18.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "RECIPEPOCKET",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Brew. Create. Remember.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun BrewMetaTag(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black.copy(alpha = 0.55f))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.9f),
            modifier = Modifier.size(13.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun QuickToolCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    testTag: String
) {
    BaristaCard(
        modifier = Modifier
            .width(115.dp)
            .height(115.dp)
            .testTag(testTag),
        shape = RoundedCornerShape(20.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun RecipeCompactCard(
    recipe: RecipeEntity,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    BaristaCard(
        modifier = modifier
            .width(170.dp)
            .clickable(onClick = onClick)
            .testTag("recipe_card_${recipe.id}"),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                if (recipe.coverDrawableRes != null) {
                    Image(
                        painter = painterResource(id = recipe.coverDrawableRes),
                        contentDescription = recipe.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primaryContainer,
                                        MaterialTheme.colorScheme.surfaceVariant
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Coffee,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                // Favorite button
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.45f))
                ) {
                    Icon(
                        imageVector = if (recipe.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (recipe.isFavorite) Color(0xFFFF5252) else Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Method badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black.copy(alpha = 0.6f),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                ) {
                    Text(
                        text = recipe.brewMethod,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = recipe.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = recipe.recommendedRatio,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${recipe.prepTimeMinutes} min",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
