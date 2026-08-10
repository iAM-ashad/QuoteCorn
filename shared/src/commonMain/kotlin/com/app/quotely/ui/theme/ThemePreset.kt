package com.app.quotely.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

/**
 * Represents a visual theme preset with custom color schemes and curated typography styles.
 */
@Immutable
data class ThemePreset(
    val id: String,
    val name: String,
    val description: String,
    val colorScheme: ColorScheme,
    val quoteFontFamily: FontFamily = PlayfairDisplayFontFamily,
    val bodyFontFamily: FontFamily = InterFontFamily,
    val backgroundColor: Color = colorScheme.background,
    val surfaceColor: Color = colorScheme.surface,
    val primaryAccent: Color = colorScheme.primary,
    val textColor: Color = colorScheme.onSurface,
    val attributionColor: Color = colorScheme.onSurfaceVariant,
    val isDark: Boolean = true
) {
    companion object {
        val AurelianMonolith = ThemePreset(
            id = "aurelian_monolith",
            name = "Aurelian Monolith",
            description = "Extreme Minimalism with an Editorial soul. Deep Obsidian voids, Warm Gold accents, and classical serif.",
            colorScheme = AurelianDarkColorScheme,
            quoteFontFamily = FontFamily.Serif,
            bodyFontFamily = FontFamily.SansSerif,
            backgroundColor = DeepObsidianBackground,
            surfaceColor = ObsidianSurface,
            primaryAccent = WarmGold,
            textColor = OnObsidianText,
            attributionColor = WarmMutedSand,
            isDark = true
        )

        val MidnightObsidian = ThemePreset(
            id = "midnight_obsidian",
            name = "Midnight Obsidian",
            description = "Pure black OLED void with platinum silver typography and cyan neon accents in crisp sans-serif.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF000000),
                surface = Color(0xFF0A0A0A),
                primary = Color(0xFF70C0F0),
                onBackground = Color(0xFFE5E9F0),
                onSurface = Color(0xFFE5E9F0)
            ),
            quoteFontFamily = FontFamily.SansSerif,
            bodyFontFamily = FontFamily.Monospace,
            backgroundColor = Color(0xFF000000),
            surfaceColor = Color(0xFF0A0A0A),
            primaryAccent = Color(0xFF70C0F0),
            textColor = Color(0xFFE5E9F0),
            attributionColor = Color(0xFF9AA7B7),
            isDark = true
        )

        val RoyalEmerald = ThemePreset(
            id = "royal_emerald",
            name = "Royal Emerald",
            description = "Deep imperial emerald void paired with antique gold accents and regal cursive calligraphy.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF0B1B15),
                surface = Color(0xFF132A22),
                primary = Color(0xFFE2C46C),
                onBackground = Color(0xFFE8F1EC),
                onSurface = Color(0xFFE8F1EC)
            ),
            quoteFontFamily = FontFamily.Cursive,
            bodyFontFamily = FontFamily.Serif,
            backgroundColor = Color(0xFF0B1B15),
            surfaceColor = Color(0xFF132A22),
            primaryAccent = Color(0xFFE2C46C),
            textColor = Color(0xFFE8F1EC),
            attributionColor = Color(0xFFA6C5B8),
            isDark = true
        )

        val NordicTwilight = ThemePreset(
            id = "nordic_twilight",
            name = "Nordic Twilight",
            description = "Cool moody navy slate with soft lavender accents and sleek modern sans-serif.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF141923),
                surface = Color(0xFF1E2534),
                primary = Color(0xFFC8D3F5),
                onBackground = Color(0xFFE5E9F0),
                onSurface = Color(0xFFE5E9F0)
            ),
            quoteFontFamily = FontFamily.SansSerif,
            bodyFontFamily = FontFamily.SansSerif,
            backgroundColor = Color(0xFF141923),
            surfaceColor = Color(0xFF1E2534),
            primaryAccent = Color(0xFFC8D3F5),
            textColor = Color(0xFFE5E9F0),
            attributionColor = Color(0xFF889BB7),
            isDark = true
        )

        val BespokeEspresso = ThemePreset(
            id = "bespoke_espresso",
            name = "Bespoke Espresso",
            description = "Rich dark roast coffee canvas with terracotta copper and typewriter monospaced font.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF1E1614),
                surface = Color(0xFF2C2220),
                primary = Color(0xFFD98A6C),
                onBackground = Color(0xFFF5ECE8),
                onSurface = Color(0xFFF5ECE8)
            ),
            quoteFontFamily = FontFamily.Monospace,
            bodyFontFamily = FontFamily.SansSerif,
            backgroundColor = Color(0xFF1E1614),
            surfaceColor = Color(0xFF2C2220),
            primaryAccent = Color(0xFFD98A6C),
            textColor = Color(0xFFF5ECE8),
            attributionColor = Color(0xFFB8A29B),
            isDark = true
        )

        val CrimsonDynasty = ThemePreset(
            id = "crimson_dynasty",
            name = "Crimson Dynasty",
            description = "Deep burgundy plum void with rose gold accents and romantic calligraphic script.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF1F0E17),
                surface = Color(0xFF2E1724),
                primary = Color(0xFFF2A6B4),
                onBackground = Color(0xFFF7ECF2),
                onSurface = Color(0xFFF7ECF2)
            ),
            quoteFontFamily = FontFamily.Cursive,
            bodyFontFamily = FontFamily.Serif,
            backgroundColor = Color(0xFF1F0E17),
            surfaceColor = Color(0xFF2E1724),
            primaryAccent = Color(0xFFF2A6B4),
            textColor = Color(0xFFF7ECF2),
            attributionColor = Color(0xFFBFA2B1),
            isDark = true
        )

        val SereneSanctuary = ThemePreset(
            id = "serene_sanctuary",
            name = "Serene Sanctuary",
            description = "Weightless pastel sanctuary with soothing cool tones and clean sans-serif.",
            colorScheme = AurelianLightColorScheme.copy(
                background = Color(0xFFF8F9FB),
                surface = Color(0xFFFFFFFF),
                primary = Color(0xFF3F618B),
                onPrimary = Color.White,
                onBackground = Color(0xFF2C3437),
                onSurface = Color(0xFF2C3437)
            ),
            quoteFontFamily = FontFamily.SansSerif,
            bodyFontFamily = FontFamily.SansSerif,
            backgroundColor = Color(0xFFF8F9FB),
            surfaceColor = Color(0xFFFFFFFF),
            primaryAccent = Color(0xFF3F618B),
            textColor = Color(0xFF2C3437),
            attributionColor = Color(0xFF596064),
            isDark = false
        )

        val EditorialParchment = ThemePreset(
            id = "editorial_parchment",
            name = "Editorial Parchment",
            description = "Classic newsprint and physical monograph paper feel with literary serif.",
            colorScheme = AurelianLightColorScheme.copy(
                background = Color(0xFFF5F2EB),
                surface = Color(0xFFEFECE4),
                primary = Color(0xFF735C00),
                onBackground = Color(0xFF1C1B1B),
                onSurface = Color(0xFF1C1B1B)
            ),
            quoteFontFamily = FontFamily.Serif,
            bodyFontFamily = FontFamily.SansSerif,
            backgroundColor = Color(0xFFF5F2EB),
            surfaceColor = Color(0xFFEFECE4),
            primaryAccent = Color(0xFF735C00),
            textColor = Color(0xFF1C1B1B),
            attributionColor = Color(0xFF4D4635),
            isDark = false
        )

        val Default: ThemePreset = AurelianMonolith

        val availablePresets: List<ThemePreset> = listOf(
            AurelianMonolith,
            MidnightObsidian,
            RoyalEmerald,
            NordicTwilight,
            BespokeEspresso,
            CrimsonDynasty,
            SereneSanctuary,
            EditorialParchment
        )
    }
}

val LocalThemePreset = staticCompositionLocalOf { ThemePreset.Default }
