package com.example.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalCompactMode = compositionLocalOf { false }

object CompactDimens {
    fun horizontalPadding(isCompact: Boolean): Dp = if (isCompact) 12.dp else 20.dp
    fun cardPadding(isCompact: Boolean): Dp = if (isCompact) 10.dp else 16.dp
    fun itemSpacing(isCompact: Boolean): Dp = if (isCompact) 8.dp else 14.dp
    fun heroHeight(isCompact: Boolean): Dp = if (isCompact) 125.dp else 165.dp
    fun cardCorner(isCompact: Boolean): Dp = if (isCompact) 16.dp else 22.dp
}
