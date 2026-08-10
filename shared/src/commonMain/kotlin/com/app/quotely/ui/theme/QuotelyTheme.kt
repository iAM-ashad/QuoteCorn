package com.app.quotely.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

/**
 * Enforces strictly sharp (0dp) corners per the Stitch Aurelian Monolith design system specifications.
 */
val QuotelyShapes = Shapes(
    extraSmall = RoundedCornerShape(0.dp),
    small = RoundedCornerShape(0.dp),
    medium = RoundedCornerShape(0.dp),
    large = RoundedCornerShape(0.dp),
    extraLarge = RoundedCornerShape(0.dp)
)

/**
 * Top-level Compose Multiplatform Theme wrapper for Quotely.
 *
 * @param preset The active [ThemePreset] token collection. Defaults to [ThemePreset.Default] (Aurelian Monolith).
 * @param darkTheme Override flag for dark mode. Defaults to [ThemePreset.isDark].
 * @param typography Extended typography set for quote displays and UI elements.
 * @param content The composable tree.
 */
@Composable
fun QuotelyTheme(
    preset: ThemePreset = ThemePreset.Default,
    darkTheme: Boolean = preset.isDark,
    typography: QuotelyTypography = QuotelyTypography(),
    content: @Composable () -> Unit
) {
    val activeColorScheme = if (darkTheme) {
        preset.colorScheme
    } else {
        if (preset.isDark) AurelianLightColorScheme else preset.colorScheme
    }

    CompositionLocalProvider(
        LocalThemePreset provides preset,
        LocalQuotelyTypography provides typography
    ) {
        MaterialTheme(
            colorScheme = activeColorScheme,
            typography = MaterialQuotelyTypography,
            shapes = QuotelyShapes,
            content = content
        )
    }
}
