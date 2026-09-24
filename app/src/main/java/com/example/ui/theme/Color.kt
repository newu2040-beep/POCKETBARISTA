package com.example.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.example.model.BaristaTheme

object BaristaPalette {
    // 1. ESPRESSO
    val EspressoPrimaryLight = Color(0xFF9E5424)
    val EspressoPrimaryDark = Color(0xFFE89965)
    val EspressoBgLight = Color(0xFFFBF8F5)
    val EspressoBgDark = Color(0xFF140F0C)
    val EspressoSurfaceLight = Color(0xFFFFFFFF)
    val EspressoSurfaceDark = Color(0xFF1E1713)
    val EspressoSurfaceVariantLight = Color(0xFFF3ECE5)
    val EspressoSurfaceVariantDark = Color(0xFF2C221C)

    // 2. VANILLA CREAM
    val VanillaPrimaryLight = Color(0xFF8C6239)
    val VanillaPrimaryDark = Color(0xFFD6A97A)
    val VanillaBgLight = Color(0xFFFAF8F5)
    val VanillaBgDark = Color(0xFF171512)
    val VanillaSurfaceLight = Color(0xFFFFFFFF)
    val VanillaSurfaceDark = Color(0xFF23201C)
    val VanillaSurfaceVariantLight = Color(0xFFF4EFE6)
    val VanillaSurfaceVariantDark = Color(0xFF322E27)

    // 3. MATCHA
    val MatchaPrimaryLight = Color(0xFF2E6B43)
    val MatchaPrimaryDark = Color(0xFF67B280)
    val MatchaBgLight = Color(0xFFF5F9F6)
    val MatchaBgDark = Color(0xFF0F1711)
    val MatchaSurfaceLight = Color(0xFFFFFFFF)
    val MatchaSurfaceDark = Color(0xFF17231A)
    val MatchaSurfaceVariantLight = Color(0xFFEAF2EC)
    val MatchaSurfaceVariantDark = Color(0xFF233528)

    // 4. CARAMEL
    val CaramelPrimaryLight = Color(0xFFA85D08)
    val CaramelPrimaryDark = Color(0xFFF0A045)
    val CaramelBgLight = Color(0xFFFDF8F2)
    val CaramelBgDark = Color(0xFF171109)
    val CaramelSurfaceLight = Color(0xFFFFFFFF)
    val CaramelSurfaceDark = Color(0xFF241A0E)
    val CaramelSurfaceVariantLight = Color(0xFFF6ECE0)
    val CaramelSurfaceVariantDark = Color(0xFF362716)

    // 5. MOCHA
    val MochaPrimaryLight = Color(0xFF7A432C)
    val MochaPrimaryDark = Color(0xFFC78367)
    val MochaBgLight = Color(0xFFFAF6F4)
    val MochaBgDark = Color(0xFF16110E)
    val MochaSurfaceLight = Color(0xFFFFFFFF)
    val MochaSurfaceDark = Color(0xFF221A16)
    val MochaSurfaceVariantLight = Color(0xFFF2EAE5)
    val MochaSurfaceVariantDark = Color(0xFF332722)

    // 6. MIDNIGHT COFFEE
    val MidnightPrimaryLight = Color(0xFF9E651E)
    val MidnightPrimaryDark = Color(0xFFFFB24D)
    val MidnightBgLight = Color(0xFFF7F8F9)
    val MidnightBgDark = Color(0xFF0C0E11)
    val MidnightSurfaceLight = Color(0xFFFFFFFF)
    val MidnightSurfaceDark = Color(0xFF15181E)
    val MidnightSurfaceVariantLight = Color(0xFFEAECEF)
    val MidnightSurfaceVariantDark = Color(0xFF20252E)

    // 7. ROSE LATTE
    val RosePrimaryLight = Color(0xFFA14658)
    val RosePrimaryDark = Color(0xFFE58799)
    val RoseBgLight = Color(0xFFFCF6F7)
    val RoseBgDark = Color(0xFF180F13)
    val RoseSurfaceLight = Color(0xFFFFFFFF)
    val RoseSurfaceDark = Color(0xFF25171D)
    val RoseSurfaceVariantLight = Color(0xFFF8ECF0)
    val RoseSurfaceVariantDark = Color(0xFF38232B)

    // Common accent tokens
    val AmberGold = Color(0xFFD48B38)
    val CremaLight = Color(0xFFEEDCC7)
    val SuccessGreen = Color(0xFF388E3C)
    val StarGold = Color(0xFFFFB300)
    val DangerRed = Color(0xFFD32F2F)
}

fun getBaristaColorScheme(theme: BaristaTheme, isDark: Boolean): ColorScheme {
    return if (isDark) {
        when (theme) {
            BaristaTheme.ESPRESSO -> darkColorScheme(
                primary = BaristaPalette.EspressoPrimaryDark,
                onPrimary = Color(0xFF2C1304),
                primaryContainer = Color(0xFF4A250B),
                onPrimaryContainer = Color(0xFFFFDBC9),
                background = BaristaPalette.EspressoBgDark,
                onBackground = Color(0xFFEDE0D8),
                surface = BaristaPalette.EspressoSurfaceDark,
                onSurface = Color(0xFFEDE0D8),
                surfaceVariant = BaristaPalette.EspressoSurfaceVariantDark,
                onSurfaceVariant = Color(0xFFD5C4B9),
                secondary = Color(0xFFD8BBAF),
                onSecondary = Color(0xFF3C2920)
            )
            BaristaTheme.VANILLA_CREAM -> darkColorScheme(
                primary = BaristaPalette.VanillaPrimaryDark,
                onPrimary = Color(0xFF2F1D0A),
                primaryContainer = Color(0xFF4C341B),
                onPrimaryContainer = Color(0xFFFFE0BA),
                background = BaristaPalette.VanillaBgDark,
                onBackground = Color(0xFFEAE4DC),
                surface = BaristaPalette.VanillaSurfaceDark,
                onSurface = Color(0xFFEAE4DC),
                surfaceVariant = BaristaPalette.VanillaSurfaceVariantDark,
                onSurfaceVariant = Color(0xFFCDC5BA),
                secondary = Color(0xFFD2C5B6),
                onSecondary = Color(0xFF382F24)
            )
            BaristaTheme.MATCHA -> darkColorScheme(
                primary = BaristaPalette.MatchaPrimaryDark,
                onPrimary = Color(0xFF042D13),
                primaryContainer = Color(0xFF194928),
                onPrimaryContainer = Color(0xFFC7F2D3),
                background = BaristaPalette.MatchaBgDark,
                onBackground = Color(0xFFDFE9E1),
                surface = BaristaPalette.MatchaSurfaceDark,
                onSurface = Color(0xFFDFE9E1),
                surfaceVariant = BaristaPalette.MatchaSurfaceVariantDark,
                onSurfaceVariant = Color(0xFFBFCFBF),
                secondary = Color(0xFFB7CEBC),
                onSecondary = Color(0xFF243929)
            )
            BaristaTheme.CARAMEL -> darkColorScheme(
                primary = BaristaPalette.CaramelPrimaryDark,
                onPrimary = Color(0xFF341900),
                primaryContainer = Color(0xFF552E03),
                onPrimaryContainer = Color(0xFFFFDCBE),
                background = BaristaPalette.CaramelBgDark,
                onBackground = Color(0xFFEEE3D7),
                surface = BaristaPalette.CaramelSurfaceDark,
                onSurface = Color(0xFFEEE3D7),
                surfaceVariant = BaristaPalette.CaramelSurfaceVariantDark,
                onSurfaceVariant = Color(0xFFD6C4B4),
                secondary = Color(0xFFDEC3AA),
                onSecondary = Color(0xFF3E2C1B)
            )
            BaristaTheme.MOCHA -> darkColorScheme(
                primary = BaristaPalette.MochaPrimaryDark,
                onPrimary = Color(0xFF2E1106),
                primaryContainer = Color(0xFF4B2315),
                onPrimaryContainer = Color(0xFFFFDBCF),
                background = BaristaPalette.MochaBgDark,
                onBackground = Color(0xFFECE0DC),
                surface = BaristaPalette.MochaSurfaceDark,
                onSurface = Color(0xFFECE0DC),
                surfaceVariant = BaristaPalette.MochaSurfaceVariantDark,
                onSurfaceVariant = Color(0xFFD5C3BD),
                secondary = Color(0xFFD8BFB8),
                onSecondary = Color(0xFF3C2A24)
            )
            BaristaTheme.MIDNIGHT_COFFEE -> darkColorScheme(
                primary = BaristaPalette.MidnightPrimaryDark,
                onPrimary = Color(0xFF2E1A00),
                primaryContainer = Color(0xFF4E3003),
                onPrimaryContainer = Color(0xFFFFDDB4),
                background = BaristaPalette.MidnightBgDark,
                onBackground = Color(0xFFE2E5E9),
                surface = BaristaPalette.MidnightSurfaceDark,
                onSurface = Color(0xFFE2E5E9),
                surfaceVariant = BaristaPalette.MidnightSurfaceVariantDark,
                onSurfaceVariant = Color(0xFFC3C9D2),
                secondary = Color(0xFFBCC6D3),
                onSecondary = Color(0xFF28323E)
            )
            BaristaTheme.ROSE_LATTE -> darkColorScheme(
                primary = BaristaPalette.RosePrimaryDark,
                onPrimary = Color(0xFF3A0D18),
                primaryContainer = Color(0xFF5A1C2A),
                onPrimaryContainer = Color(0xFFFFD9DF),
                background = BaristaPalette.RoseBgDark,
                onBackground = Color(0xFFEDE0E3),
                surface = BaristaPalette.RoseSurfaceDark,
                onSurface = Color(0xFFEDE0E3),
                surfaceVariant = BaristaPalette.RoseSurfaceVariantDark,
                onSurfaceVariant = Color(0xFFD7C2C7),
                secondary = Color(0xFFDAC0C6),
                onSecondary = Color(0xFF3E282E)
            )
        }
    } else {
        when (theme) {
            BaristaTheme.ESPRESSO -> lightColorScheme(
                primary = BaristaPalette.EspressoPrimaryLight,
                onPrimary = Color.White,
                primaryContainer = Color(0xFFFFDCBE),
                onPrimaryContainer = Color(0xFF311300),
                background = BaristaPalette.EspressoBgLight,
                onBackground = Color(0xFF201A16),
                surface = BaristaPalette.EspressoSurfaceLight,
                onSurface = Color(0xFF201A16),
                surfaceVariant = BaristaPalette.EspressoSurfaceVariantLight,
                onSurfaceVariant = Color(0xFF51453E),
                secondary = Color(0xFF735A4F),
                onSecondary = Color.White
            )
            BaristaTheme.VANILLA_CREAM -> lightColorScheme(
                primary = BaristaPalette.VanillaPrimaryLight,
                onPrimary = Color.White,
                primaryContainer = Color(0xFFFFDFB9),
                onPrimaryContainer = Color(0xFF2C1C07),
                background = BaristaPalette.VanillaBgLight,
                onBackground = Color(0xFF201D19),
                surface = BaristaPalette.VanillaSurfaceLight,
                onSurface = Color(0xFF201D19),
                surfaceVariant = BaristaPalette.VanillaSurfaceVariantLight,
                onSurfaceVariant = Color(0xFF4F473E),
                secondary = Color(0xFF6E6051),
                onSecondary = Color.White
            )
            BaristaTheme.MATCHA -> lightColorScheme(
                primary = BaristaPalette.MatchaPrimaryLight,
                onPrimary = Color.White,
                primaryContainer = Color(0xFFC7F3D3),
                onPrimaryContainer = Color(0xFF00210E),
                background = BaristaPalette.MatchaBgLight,
                onBackground = Color(0xFF172019),
                surface = BaristaPalette.MatchaSurfaceLight,
                onSurface = Color(0xFF172019),
                surfaceVariant = BaristaPalette.MatchaSurfaceVariantLight,
                onSurfaceVariant = Color(0xFF414D43),
                secondary = Color(0xFF546A58),
                onSecondary = Color.White
            )
            BaristaTheme.CARAMEL -> lightColorScheme(
                primary = BaristaPalette.CaramelPrimaryLight,
                onPrimary = Color.White,
                primaryContainer = Color(0xFFFFDDBB),
                onPrimaryContainer = Color(0xFF331A00),
                background = BaristaPalette.CaramelBgLight,
                onBackground = Color(0xFF231A10),
                surface = BaristaPalette.CaramelSurfaceLight,
                onSurface = Color(0xFF231A10),
                surfaceVariant = BaristaPalette.CaramelSurfaceVariantLight,
                onSurfaceVariant = Color(0xFF544637),
                secondary = Color(0xFF755B42),
                onSecondary = Color.White
            )
            BaristaTheme.MOCHA -> lightColorScheme(
                primary = BaristaPalette.MochaPrimaryLight,
                onPrimary = Color.White,
                primaryContainer = Color(0xFFFFDBCF),
                onPrimaryContainer = Color(0xFF311206),
                background = BaristaPalette.MochaBgLight,
                onBackground = Color(0xFF211A17),
                surface = BaristaPalette.MochaSurfaceLight,
                onSurface = Color(0xFF211A17),
                surfaceVariant = BaristaPalette.MochaSurfaceVariantLight,
                onSurfaceVariant = Color(0xFF52443F),
                secondary = Color(0xFF745850),
                onSecondary = Color.White
            )
            BaristaTheme.MIDNIGHT_COFFEE -> lightColorScheme(
                primary = BaristaPalette.MidnightPrimaryLight,
                onPrimary = Color.White,
                primaryContainer = Color(0xFFFFDDB7),
                onPrimaryContainer = Color(0xFF301D03),
                background = BaristaPalette.MidnightBgLight,
                onBackground = Color(0xFF191C20),
                surface = BaristaPalette.MidnightSurfaceLight,
                onSurface = Color(0xFF191C20),
                surfaceVariant = BaristaPalette.MidnightSurfaceVariantLight,
                onSurfaceVariant = Color(0xFF43474E),
                secondary = Color(0xFF55606F),
                onSecondary = Color.White
            )
            BaristaTheme.ROSE_LATTE -> lightColorScheme(
                primary = BaristaPalette.RosePrimaryLight,
                onPrimary = Color.White,
                primaryContainer = Color(0xFFFFD9E0),
                onPrimaryContainer = Color(0xFF3B0715),
                background = BaristaPalette.RoseBgLight,
                onBackground = Color(0xFF22191C),
                surface = BaristaPalette.RoseSurfaceLight,
                onSurface = Color(0xFF22191C),
                surfaceVariant = BaristaPalette.RoseSurfaceVariantLight,
                onSurfaceVariant = Color(0xFF534347),
                secondary = Color(0xFF765860),
                onSecondary = Color.White
            )
        }
    }
}
