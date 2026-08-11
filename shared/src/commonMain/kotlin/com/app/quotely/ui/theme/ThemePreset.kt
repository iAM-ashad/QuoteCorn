package com.app.quotely.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em

/**
 * Represents a visual theme preset with custom color schemes, curated typography specs, and atmospheric backgrounds.
 */
@Immutable
data class ThemePreset(
    val id: String,
    val name: String,
    val description: String,
    val colorScheme: ColorScheme,
    val quoteFontFamily: FontFamily = PlayfairDisplayFontFamily,
    val quoteFontWeight: FontWeight = FontWeight.SemiBold,
    val quoteFontStyle: FontStyle = FontStyle.Normal,
    val bodyFontFamily: FontFamily = InterFontFamily,
    val bodyFontWeight: FontWeight = FontWeight.Medium,
    val bodyLetterSpacing: TextUnit = 0.15.em,
    val backgroundColor: Color = colorScheme.background,
    val surfaceColor: Color = colorScheme.surface,
    val primaryAccent: Color = colorScheme.primary,
    val textColor: Color = colorScheme.onSurface,
    val attributionColor: Color = colorScheme.onSurfaceVariant,
    val isDark: Boolean = true
) {
    val backgroundGradient: Brush
        get() = Brush.verticalGradient(
            colors = listOf(
                backgroundColor,
                surfaceColor,
                backgroundColor
            )
        )

    companion object {
        // 1. Creator's Choice — The High-Fashion Monolith
        val CreatorsChoice = ThemePreset(
            id = "creators_choice",
            name = "Creator's Choice",
            description = "The High-Fashion Monolith. Playfair Display 600 SemiBold on solid pitch black with golden accents.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF000000),
                surface = Color(0xFF000000),
                primary = Color(0xFFD4AF37),
                onBackground = Color(0xFFD4AF37),
                onSurface = Color(0xFFD4AF37)
            ),
            quoteFontFamily = PlayfairDisplayFontFamily,
            quoteFontWeight = FontWeight.SemiBold,
            quoteFontStyle = FontStyle.Normal,
            bodyFontFamily = InterFontFamily,
            bodyFontWeight = FontWeight.Medium,
            bodyLetterSpacing = 0.15.em,
            backgroundColor = Color(0xFF000000),
            surfaceColor = Color(0xFF000000),
            primaryAccent = Color(0xFFD4AF37),
            textColor = Color(0xFFD4AF37),
            attributionColor = Color(0xFFB89628),
            isDark = true
        )

        // 2. Aurelian Monolith — The Imperial Crown
        val AurelianMonolith = ThemePreset(
            id = "aurelian_monolith",
            name = "Aurelian Monolith",
            description = "The Imperial Crown. Cormorant Garamond 600 SemiBold Italic leather manuscript with Plus Jakarta Sans.",
            colorScheme = AurelianDarkColorScheme,
            quoteFontFamily = CormorantGaramondFontFamily,
            quoteFontWeight = FontWeight.SemiBold,
            quoteFontStyle = FontStyle.Italic,
            bodyFontFamily = PlusJakartaSansFontFamily,
            bodyFontWeight = FontWeight.Medium,
            bodyLetterSpacing = 0.1.em,
            backgroundColor = Color(0xFF0F0E0E),
            surfaceColor = Color(0xFF261F13),
            primaryAccent = WarmGold,
            textColor = OnObsidianText,
            attributionColor = WarmMutedSand,
            isDark = true
        )

        // 3. Midnight Obsidian — The Cybernetic Roman
        val MidnightObsidian = ThemePreset(
            id = "midnight_obsidian",
            name = "Midnight Obsidian",
            description = "The Cybernetic Roman. Cinzel 500 Medium all-caps Roman stone inscriptions with Space Mono.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF000000),
                surface = Color(0xFF0A1C29),
                primary = Color(0xFF70C0F0),
                onBackground = Color(0xFFE5E9F0),
                onSurface = Color(0xFFE5E9F0)
            ),
            quoteFontFamily = CinzelFontFamily,
            quoteFontWeight = FontWeight.Medium,
            quoteFontStyle = FontStyle.Normal,
            bodyFontFamily = SpaceMonoFontFamily,
            bodyFontWeight = FontWeight.Normal,
            bodyLetterSpacing = 0.1.em,
            backgroundColor = Color(0xFF000000),
            surfaceColor = Color(0xFF0A1C29),
            primaryAccent = Color(0xFF70C0F0),
            textColor = Color(0xFFE5E9F0),
            attributionColor = Color(0xFF9AA7B7),
            isDark = true
        )

        // 4. Royal Emerald — The Aristocratic Court
        val RoyalEmerald = ThemePreset(
            id = "royal_emerald",
            name = "Royal Emerald",
            description = "The Aristocratic Court. Bodoni Moda 600 SemiBold Vogue decrees with EB Garamond Italic.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF05120D),
                surface = Color(0xFF153A2D),
                primary = Color(0xFFE2C46C),
                onBackground = Color(0xFFE8F1EC),
                onSurface = Color(0xFFE8F1EC)
            ),
            quoteFontFamily = BodoniModaFontFamily,
            quoteFontWeight = FontWeight.SemiBold,
            quoteFontStyle = FontStyle.Normal,
            bodyFontFamily = EBGaramondFontFamily,
            bodyFontWeight = FontWeight.Medium,
            bodyLetterSpacing = 0.05.em,
            backgroundColor = Color(0xFF05120D),
            surfaceColor = Color(0xFF153A2D),
            primaryAccent = Color(0xFFE2C46C),
            textColor = Color(0xFFE8F1EC),
            attributionColor = Color(0xFFA6C5B8),
            isDark = true
        )

        // 5. Nordic Twilight — The Minimalist Sanctum
        val NordicTwilight = ThemePreset(
            id = "nordic_twilight",
            name = "Nordic Twilight",
            description = "The Minimalist Sanctum. Lora 500 Medium Italic brushwork under soft lavender with Outfit.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF0F131C),
                surface = Color(0xFF222B3F),
                primary = Color(0xFFC8D3F5),
                onBackground = Color(0xFFE5E9F0),
                onSurface = Color(0xFFE5E9F0)
            ),
            quoteFontFamily = LoraFontFamily,
            quoteFontWeight = FontWeight.Medium,
            quoteFontStyle = FontStyle.Italic,
            bodyFontFamily = OutfitFontFamily,
            bodyFontWeight = FontWeight.Normal,
            bodyLetterSpacing = 0.1.em,
            backgroundColor = Color(0xFF0F131C),
            surfaceColor = Color(0xFF222B3F),
            primaryAccent = Color(0xFFC8D3F5),
            textColor = Color(0xFFE5E9F0),
            attributionColor = Color(0xFF889BB7),
            isDark = true
        )

        // 6. Bespoke Espresso — The Leather Library
        val BespokeEspresso = ThemePreset(
            id = "bespoke_espresso",
            name = "Bespoke Espresso",
            description = "The Leather Library. Fraunces 600 SemiBold organic curves with Inconsolata typewriter font.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF140E0C),
                surface = Color(0xFF33211B),
                primary = Color(0xFFD98A6C),
                onBackground = Color(0xFFF5ECE8),
                onSurface = Color(0xFFF5ECE8)
            ),
            quoteFontFamily = FrauncesFontFamily,
            quoteFontWeight = FontWeight.SemiBold,
            quoteFontStyle = FontStyle.Normal,
            bodyFontFamily = InconsolataFontFamily,
            bodyFontWeight = FontWeight.Medium,
            bodyLetterSpacing = 0.05.em,
            backgroundColor = Color(0xFF140E0C),
            surfaceColor = Color(0xFF33211B),
            primaryAccent = Color(0xFFD98A6C),
            textColor = Color(0xFFF5ECE8),
            attributionColor = Color(0xFFB8A29B),
            isDark = true
        )

        // 7. Crimson Dynasty — The Velvet Opera
        val CrimsonDynasty = ThemePreset(
            id = "crimson_dynasty",
            name = "Crimson Dynasty",
            description = "The Velvet Opera. Prata 400 Regular Didone teardrop serifs with Satoshi typography.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF14080F),
                surface = Color(0xFF391629),
                primary = Color(0xFFF2A6B4),
                onBackground = Color(0xFFF7ECF2),
                onSurface = Color(0xFFF7ECF2)
            ),
            quoteFontFamily = PrataFontFamily,
            quoteFontWeight = FontWeight.Normal,
            quoteFontStyle = FontStyle.Normal,
            bodyFontFamily = SatoshiFontFamily,
            bodyFontWeight = FontWeight.Medium,
            bodyLetterSpacing = 0.12.em,
            backgroundColor = Color(0xFF14080F),
            surfaceColor = Color(0xFF391629),
            primaryAccent = Color(0xFFF2A6B4),
            textColor = Color(0xFFF7ECF2),
            attributionColor = Color(0xFFBFA2B1),
            isDark = true
        )

        // 8. Serene Sanctuary — The Mindfulness Studio
        val SereneSanctuary = ThemePreset(
            id = "serene_sanctuary",
            name = "Serene Sanctuary",
            description = "The Mindfulness Studio. Instrument Serif 400 Regular Italic letterforms with DM Sans.",
            colorScheme = AurelianLightColorScheme.copy(
                background = Color(0xFFF0F4F8),
                surface = Color(0xFFD5E0EA),
                primary = Color(0xFF3F618B),
                onPrimary = Color.White,
                onBackground = Color(0xFF2C3437),
                onSurface = Color(0xFF2C3437)
            ),
            quoteFontFamily = InstrumentSerifFontFamily,
            quoteFontWeight = FontWeight.Normal,
            quoteFontStyle = FontStyle.Italic,
            bodyFontFamily = DMSansFontFamily,
            bodyFontWeight = FontWeight.Medium,
            bodyLetterSpacing = 0.05.em,
            backgroundColor = Color(0xFFF0F4F8),
            surfaceColor = Color(0xFFD5E0EA),
            primaryAccent = Color(0xFF3F618B),
            textColor = Color(0xFF2C3437),
            attributionColor = Color(0xFF596064),
            isDark = false
        )

        // 9. Editorial Parchment — The New York Monograph
        val EditorialParchment = ThemePreset(
            id = "editorial_parchment",
            name = "Editorial Parchment",
            description = "The New York Monograph. Newsreader 500 Medium fine literature serif with Source Sans 3.",
            colorScheme = AurelianLightColorScheme.copy(
                background = Color(0xFFF7F4EB),
                surface = Color(0xFFE3DDD0),
                primary = Color(0xFF735C00),
                onBackground = Color(0xFF1C1B1B),
                onSurface = Color(0xFF1C1B1B)
            ),
            quoteFontFamily = NewsreaderFontFamily,
            quoteFontWeight = FontWeight.Medium,
            quoteFontStyle = FontStyle.Normal,
            bodyFontFamily = SourceSans3FontFamily,
            bodyFontWeight = FontWeight.SemiBold,
            bodyLetterSpacing = 0.1.em,
            backgroundColor = Color(0xFFF7F4EB),
            surfaceColor = Color(0xFFE3DDD0),
            primaryAccent = Color(0xFF735C00),
            textColor = Color(0xFF1C1B1B),
            attributionColor = Color(0xFF4D4635),
            isDark = false
        )

        val Default: ThemePreset = CreatorsChoice

        val availablePresets: List<ThemePreset> = listOf(
            CreatorsChoice,
            AurelianMonolith,
            MidnightObsidian,
            RoyalEmerald,
            NordicTwilight,
            BespokeEspresso,
            CrimsonDynasty,
            SereneSanctuary,
            EditorialParchment
        )

        fun fromId(id: String): ThemePreset = availablePresets.find { it.id == id } ?: Default
    }
}

val LocalThemePreset = staticCompositionLocalOf { ThemePreset.Default }
