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
 * Supports 30 hand-curated editorial style pairings enforcing high-contrast, brand-protected rules.
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
        // --- 1. THE HIGH-FASHION MONOLITHS ---
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

        val MidnightObsidian = ThemePreset(
            id = "midnight_obsidian",
            name = "Midnight Obsidian",
            description = "The Cybernetic Roman. Cinzel 500 Medium all-caps Roman stone inscriptions with Space Mono.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF000000),
                surface = Color(0xFF0A1C2A),
                primary = Color(0xFF70C0F0),
                onBackground = Color(0xFFE2F3FF),
                onSurface = Color(0xFFE2F3FF)
            ),
            quoteFontFamily = CinzelFontFamily,
            quoteFontWeight = FontWeight.Medium,
            quoteFontStyle = FontStyle.Normal,
            bodyFontFamily = SpaceMonoFontFamily,
            bodyFontWeight = FontWeight.Normal,
            bodyLetterSpacing = 0.2.em,
            backgroundColor = Color(0xFF000000),
            surfaceColor = Color(0xFF0A1C2A),
            primaryAccent = Color(0xFF70C0F0),
            textColor = Color(0xFFE2F3FF),
            attributionColor = Color(0xFF86A5C0),
            isDark = true
        )

        val ImperialOnyx = ThemePreset(
            id = "imperial_onyx",
            name = "Imperial Onyx",
            description = "Deep dark onyx monolith with stark white serif typography.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF080808),
                surface = Color(0xFF141414),
                primary = Color(0xFFE5E5E5)
            ),
            quoteFontFamily = PlayfairDisplayFontFamily,
            quoteFontWeight = FontWeight.Bold,
            backgroundColor = Color(0xFF080808),
            surfaceColor = Color(0xFF141414),
            primaryAccent = Color(0xFFE5E5E5),
            textColor = Color(0xFFFFFFFF),
            attributionColor = Color(0xFFA0A0A0),
            isDark = true
        )

        val CyberneticRoman = ThemePreset(
            id = "cybernetic_roman",
            name = "Cybernetic Roman",
            description = "Futuristic roman inscriptions on deep space void background.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF050B14),
                surface = Color(0xFF0E1A2D),
                primary = Color(0xFF38BDF8)
            ),
            quoteFontFamily = CinzelFontFamily,
            quoteFontWeight = FontWeight.Bold,
            backgroundColor = Color(0xFF050B14),
            surfaceColor = Color(0xFF0E1A2D),
            primaryAccent = Color(0xFF38BDF8),
            textColor = Color(0xFFF0F9FF),
            attributionColor = Color(0xFF7DD3FC),
            isDark = true
        )

        val MonolithGold = ThemePreset(
            id = "monolith_gold",
            name = "Monolith Gold",
            description = "Ultra high-contrast metallic gold monolith.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF000000),
                surface = Color(0xFF1C190D),
                primary = Color(0xFFF59E0B)
            ),
            quoteFontFamily = CormorantGaramondFontFamily,
            quoteFontWeight = FontWeight.Bold,
            backgroundColor = Color(0xFF000000),
            surfaceColor = Color(0xFF1C190D),
            primaryAccent = Color(0xFFF59E0B),
            textColor = Color(0xFFFDE68A),
            attributionColor = Color(0xFFD97706),
            isDark = true
        )

        val ObsidianVelvet = ThemePreset(
            id = "obsidian_velvet",
            name = "Obsidian Velvet",
            description = "Deep velvet black surface with glowing warm accents.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF0A090B),
                surface = Color(0xFF1A161E),
                primary = Color(0xFFC084FC)
            ),
            quoteFontFamily = PlayfairDisplayFontFamily,
            quoteFontWeight = FontWeight.Medium,
            backgroundColor = Color(0xFF0A090B),
            surfaceColor = Color(0xFF1A161E),
            primaryAccent = Color(0xFFC084FC),
            textColor = Color(0xFFFAF5FF),
            attributionColor = Color(0xFFE9D5FF),
            isDark = true
        )

        val PitchParchment = ThemePreset(
            id = "pitch_parchment",
            name = "Pitch Parchment",
            description = "Inverted parchment on absolute zero black.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF000000),
                surface = Color(0xFF171717),
                primary = Color(0xFFD4D4D4)
            ),
            quoteFontFamily = NewsreaderFontFamily,
            quoteFontWeight = FontWeight.Medium,
            backgroundColor = Color(0xFF000000),
            surfaceColor = Color(0xFF171717),
            primaryAccent = Color(0xFFD4D4D4),
            textColor = Color(0xFFF5F5F5),
            attributionColor = Color(0xFFA3A3A3),
            isDark = true
        )

        // --- 2. ROYAL & ARISTOCRATIC COURTS ---
        val RoyalEmerald = ThemePreset(
            id = "royal_emerald",
            name = "Royal Emerald",
            description = "The Sovereign Court. Bodoni Moda 700 Bold deep emerald velvet with champagne gold.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF05130E),
                surface = Color(0xFF0E281F),
                primary = Color(0xFFE2C068),
                onBackground = Color(0xFFF2FBF6),
                onSurface = Color(0xFFF2FBF6)
            ),
            quoteFontFamily = BodoniModaFontFamily,
            quoteFontWeight = FontWeight.Bold,
            quoteFontStyle = FontStyle.Normal,
            bodyFontFamily = InterFontFamily,
            bodyFontWeight = FontWeight.SemiBold,
            bodyLetterSpacing = 0.12.em,
            backgroundColor = Color(0xFF05130E),
            surfaceColor = Color(0xFF0E281F),
            primaryAccent = Color(0xFFE2C068),
            textColor = Color(0xFFF2FBF6),
            attributionColor = Color(0xFFA3CBB8),
            isDark = true
        )

        val VelvetOpera = ThemePreset(
            id = "velvet_opera",
            name = "Velvet Opera",
            description = "Deep opera crimson velvet with warm gold typography.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF170509),
                surface = Color(0xFF2D0B13),
                primary = Color(0xFFF3C667)
            ),
            quoteFontFamily = BodoniModaFontFamily,
            quoteFontWeight = FontWeight.Bold,
            backgroundColor = Color(0xFF170509),
            surfaceColor = Color(0xFF2D0B13),
            primaryAccent = Color(0xFFF3C667),
            textColor = Color(0xFFFFF1F2),
            attributionColor = Color(0xFFFECDD3),
            isDark = true
        )

        val ImperialSapphire = ThemePreset(
            id = "imperial_sapphire",
            name = "Imperial Sapphire",
            description = "Noble deep sapphire blue with silver accents.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF081021),
                surface = Color(0xFF12203E),
                primary = Color(0xFF93C5FD)
            ),
            quoteFontFamily = PlayfairDisplayFontFamily,
            quoteFontWeight = FontWeight.Bold,
            backgroundColor = Color(0xFF081021),
            surfaceColor = Color(0xFF12203E),
            primaryAccent = Color(0xFF93C5FD),
            textColor = Color(0xFFF0F9FF),
            attributionColor = Color(0xFFBFDBFE),
            isDark = true
        )

        val ChampagneCourt = ThemePreset(
            id = "champagne_court",
            name = "Champagne Court",
            description = "Luminous champagne gold court background with dark serif text.",
            colorScheme = AurelianLightColorScheme.copy(
                background = Color(0xFFFDF8EE),
                surface = Color(0xFFF5EAD4),
                primary = Color(0xFF855D14)
            ),
            quoteFontFamily = BodoniModaFontFamily,
            quoteFontWeight = FontWeight.Bold,
            backgroundColor = Color(0xFFFDF8EE),
            surfaceColor = Color(0xFFF5EAD4),
            primaryAccent = Color(0xFF855D14),
            textColor = Color(0xFF291E0A),
            attributionColor = Color(0xFF5C4517),
            isDark = false
        )

        val RegalAmethyst = ThemePreset(
            id = "regal_amethyst",
            name = "Regal Amethyst",
            description = "Deep imperial purple velvet with warm golden glow.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF12081C),
                surface = Color(0xFF231236),
                primary = Color(0xFFE9D5FF)
            ),
            quoteFontFamily = PlayfairDisplayFontFamily,
            quoteFontWeight = FontWeight.Bold,
            backgroundColor = Color(0xFF12081C),
            surfaceColor = Color(0xFF231236),
            primaryAccent = Color(0xFFE9D5FF),
            textColor = Color(0xFFFAF5FF),
            attributionColor = Color(0xFFD8B4FE),
            isDark = true
        )

        val DynastyCrimson = ThemePreset(
            id = "dynasty_crimson",
            name = "Dynasty Crimson",
            description = "Imperious dynasty red with stark golden inscriptions.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF1A0606),
                surface = Color(0xFF330E0E),
                primary = Color(0xFFFCD34D)
            ),
            quoteFontFamily = CormorantGaramondFontFamily,
            quoteFontWeight = FontWeight.Bold,
            backgroundColor = Color(0xFF1A0606),
            surfaceColor = Color(0xFF330E0E),
            primaryAccent = Color(0xFFFCD34D),
            textColor = Color(0xFFFEF3C7),
            attributionColor = Color(0xFFA16207),
            isDark = true
        )

        val VenetianRuby = ThemePreset(
            id = "venetian_ruby",
            name = "Venetian Ruby",
            description = "Venetian glass ruby dark tone with silver literature fonts.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF1C0A10),
                surface = Color(0xFF361421),
                primary = Color(0xFFF472B6)
            ),
            quoteFontFamily = NewsreaderFontFamily,
            quoteFontWeight = FontWeight.SemiBold,
            backgroundColor = Color(0xFF1C0A10),
            surfaceColor = Color(0xFF361421),
            primaryAccent = Color(0xFFF472B6),
            textColor = Color(0xFFFDF2F8),
            attributionColor = Color(0xFFFBCFE8),
            isDark = true
        )

        // --- 3. QUIET LUXURY & NORDIC SANCTUMS ---
        val NordicTwilight = ThemePreset(
            id = "nordic_twilight",
            name = "Nordic Twilight",
            description = "Subtle slate grey twilight with soft white typography.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF121518),
                surface = Color(0xFF1E232A),
                primary = Color(0xFF94A3B8)
            ),
            quoteFontFamily = PlayfairDisplayFontFamily,
            quoteFontWeight = FontWeight.Medium,
            backgroundColor = Color(0xFF121518),
            surfaceColor = Color(0xFF1E232A),
            primaryAccent = Color(0xFF94A3B8),
            textColor = Color(0xFFF8FAFC),
            attributionColor = Color(0xFFCBD5E1),
            isDark = true
        )

        val SereneSanctuary = ThemePreset(
            id = "serene_sanctuary",
            name = "Serene Sanctuary",
            description = "Quiet luxury sage dark green with warm parchment text.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF0F1512),
                surface = Color(0xFF1A241F),
                primary = Color(0xFF86EFAC)
            ),
            quoteFontFamily = CormorantGaramondFontFamily,
            quoteFontWeight = FontWeight.Medium,
            backgroundColor = Color(0xFF0F1512),
            surfaceColor = Color(0xFF1A241F),
            primaryAccent = Color(0xFF86EFAC),
            textColor = Color(0xFFF0FDF4),
            attributionColor = Color(0xFFBBF7D0),
            isDark = true
        )

        val BespokeEspresso = ThemePreset(
            id = "bespoke_espresso",
            name = "Bespoke Espresso",
            description = "Rich roasted coffee dark brown with warm cream text.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF140F0D),
                surface = Color(0xFF261C19),
                primary = Color(0xFFFDE68A)
            ),
            quoteFontFamily = NewsreaderFontFamily,
            quoteFontWeight = FontWeight.Medium,
            backgroundColor = Color(0xFF140F0D),
            surfaceColor = Color(0xFF261C19),
            primaryAccent = Color(0xFFFDE68A),
            textColor = Color(0xFFFFFBEB),
            attributionColor = Color(0xFFFEF3C7),
            isDark = true
        )

        val EditorialParchment = ThemePreset(
            id = "editorial_parchment",
            name = "Editorial Parchment",
            description = "The New York Monograph. Newsreader 500 Medium fine literature serif on classic cream.",
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

        val FrostSanctum = ThemePreset(
            id = "frost_sanctum",
            name = "Frost Sanctum",
            description = "Pure arctic frost white surface with graphite literature text.",
            colorScheme = AurelianLightColorScheme.copy(
                background = Color(0xFFF8FAFC),
                surface = Color(0xFFE2E8F0),
                primary = Color(0xFF0F172A)
            ),
            quoteFontFamily = PlayfairDisplayFontFamily,
            quoteFontWeight = FontWeight.Medium,
            backgroundColor = Color(0xFFF8FAFC),
            surfaceColor = Color(0xFFE2E8F0),
            primaryAccent = Color(0xFF0F172A),
            textColor = Color(0xFF0F172A),
            attributionColor = Color(0xFF475569),
            isDark = false
        )

        val StudioMinimal = ThemePreset(
            id = "studio_minimal",
            name = "Studio Minimal",
            description = "Architectural studio stark white layout.",
            colorScheme = AurelianLightColorScheme.copy(
                background = Color(0xFFFFFFFF),
                surface = Color(0xFFF3F4F6),
                primary = Color(0xFF111827)
            ),
            quoteFontFamily = InterFontFamily,
            quoteFontWeight = FontWeight.Bold,
            backgroundColor = Color(0xFFFFFFFF),
            surfaceColor = Color(0xFFF3F4F6),
            primaryAccent = Color(0xFF111827),
            textColor = Color(0xFF111827),
            attributionColor = Color(0xFF6B7280),
            isDark = false
        )

        val KyotoMoss = ThemePreset(
            id = "kyoto_moss",
            name = "Kyoto Moss",
            description = "Peaceful Zen garden dark green with ivory text.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF0B120E),
                surface = Color(0xFF16241C),
                primary = Color(0xFFA7F3D0)
            ),
            quoteFontFamily = CormorantGaramondFontFamily,
            quoteFontWeight = FontWeight.Medium,
            backgroundColor = Color(0xFF0B120E),
            surfaceColor = Color(0xFF16241C),
            primaryAccent = Color(0xFFA7F3D0),
            textColor = Color(0xFFECFDF5),
            attributionColor = Color(0xFF6EE7B7),
            isDark = true
        )

        val CopenhagenSlate = ThemePreset(
            id = "copenhagen_slate",
            name = "Copenhagen Slate",
            description = "Danish design dark slate aesthetic.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF15181C),
                surface = Color(0xFF22272E),
                primary = Color(0xFF38BDF8)
            ),
            quoteFontFamily = PlusJakartaSansFontFamily,
            quoteFontWeight = FontWeight.SemiBold,
            backgroundColor = Color(0xFF15181C),
            surfaceColor = Color(0xFF22272E),
            primaryAccent = Color(0xFF38BDF8),
            textColor = Color(0xFFF1F5F9),
            attributionColor = Color(0xFF94A3B8),
            isDark = true
        )

        // --- 4. MODERN EDITORIAL & MONOGRAPH ---
        val AgedInk = ThemePreset(
            id = "aged_ink",
            name = "Aged Ink",
            description = "Aged fountain pen ink on warm vellum.",
            colorScheme = AurelianLightColorScheme.copy(
                background = Color(0xFFFAF6EE),
                surface = Color(0xFFEFE8D8),
                primary = Color(0xFF423216)
            ),
            quoteFontFamily = NewsreaderFontFamily,
            quoteFontWeight = FontWeight.Medium,
            backgroundColor = Color(0xFFFAF6EE),
            surfaceColor = Color(0xFFEFE8D8),
            primaryAccent = Color(0xFF423216),
            textColor = Color(0xFF2D220E),
            attributionColor = Color(0xFF5E4924),
            isDark = false
        )

        val IvoryColumn = ThemePreset(
            id = "ivory_column",
            name = "Ivory Column",
            description = "Classical marble column ivory background.",
            colorScheme = AurelianLightColorScheme.copy(
                background = Color(0xFFFFFFF5),
                surface = Color(0xFFF5F5E6),
                primary = Color(0xFF78350F)
            ),
            quoteFontFamily = BodoniModaFontFamily,
            quoteFontWeight = FontWeight.Bold,
            backgroundColor = Color(0xFFFFFFF5),
            surfaceColor = Color(0xFFF5F5E6),
            primaryAccent = Color(0xFF78350F),
            textColor = Color(0xFF451A03),
            attributionColor = Color(0xFF92400E),
            isDark = false
        )

        val SohoTimes = ThemePreset(
            id = "soho_times",
            name = "Soho Times",
            description = "Bold contemporary newspaper literature styling.",
            colorScheme = AurelianLightColorScheme.copy(
                background = Color(0xFFF3F4F6),
                surface = Color(0xFFE5E7EB),
                primary = Color(0xFF1F2937)
            ),
            quoteFontFamily = PlayfairDisplayFontFamily,
            quoteFontWeight = FontWeight.Bold,
            backgroundColor = Color(0xFFF3F4F6),
            surfaceColor = Color(0xFFE5E7EB),
            primaryAccent = Color(0xFF1F2937),
            textColor = Color(0xFF111827),
            attributionColor = Color(0xFF4B5563),
            isDark = false
        )

        val ParisReview = ThemePreset(
            id = "paris_review",
            name = "Paris Review",
            description = "Literary review journal aesthetic with refined serif typography.",
            colorScheme = AurelianLightColorScheme.copy(
                background = Color(0xFFFDFBF7),
                surface = Color(0xFFF4EFE6),
                primary = Color(0xFF991B1B)
            ),
            quoteFontFamily = CormorantGaramondFontFamily,
            quoteFontWeight = FontWeight.Bold,
            quoteFontStyle = FontStyle.Italic,
            backgroundColor = Color(0xFFFDFBF7),
            surfaceColor = Color(0xFFF4EFE6),
            primaryAccent = Color(0xFF991B1B),
            textColor = Color(0xFF1C1917),
            attributionColor = Color(0xFF78716C),
            isDark = false
        )

        val BodoniMidnight = ThemePreset(
            id = "bodoni_midnight",
            name = "Bodoni Midnight",
            description = "High-fashion Bodoni typography on midnight obsidian.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF030712),
                surface = Color(0xFF111827),
                primary = Color(0xFFF9FAFB)
            ),
            quoteFontFamily = BodoniModaFontFamily,
            quoteFontWeight = FontWeight.Bold,
            backgroundColor = Color(0xFF030712),
            surfaceColor = Color(0xFF111827),
            primaryAccent = Color(0xFFF9FAFB),
            textColor = Color(0xFFFFFFFF),
            attributionColor = Color(0xFF9CA3AF),
            isDark = true
        )

        val MonographCharcoal = ThemePreset(
            id = "monograph_charcoal",
            name = "Monograph Charcoal",
            description = "Architectural charcoal sketch monochrome theme.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF18181B),
                surface = Color(0xFF27272A),
                primary = Color(0xFFE4E4E7)
            ),
            quoteFontFamily = NewsreaderFontFamily,
            quoteFontWeight = FontWeight.Medium,
            backgroundColor = Color(0xFF18181B),
            surfaceColor = Color(0xFF27272A),
            primaryAccent = Color(0xFFE4E4E7),
            textColor = Color(0xFFFAFAFA),
            attributionColor = Color(0xFFA1A1AA),
            isDark = true
        )

        val GothicScript = ThemePreset(
            id = "gothic_script",
            name = "Gothic Script",
            description = "Dark gothic manuscript with sharp contrasting silver.",
            colorScheme = AurelianDarkColorScheme.copy(
                background = Color(0xFF09090B),
                surface = Color(0xFF18181B),
                primary = Color(0xFFA1A1AA)
            ),
            quoteFontFamily = CinzelFontFamily,
            quoteFontWeight = FontWeight.Bold,
            backgroundColor = Color(0xFF09090B),
            surfaceColor = Color(0xFF18181B),
            primaryAccent = Color(0xFFA1A1AA),
            textColor = Color(0xFFF4F4F5),
            attributionColor = Color(0xFF71717A),
            isDark = true
        )

        val Default: ThemePreset = CreatorsChoice

        val availablePresets: List<ThemePreset> = listOf(
            CreatorsChoice,
            AurelianMonolith,
            MidnightObsidian,
            ImperialOnyx,
            CyberneticRoman,
            MonolithGold,
            ObsidianVelvet,
            PitchParchment,
            RoyalEmerald,
            VelvetOpera,
            ImperialSapphire,
            ChampagneCourt,
            RegalAmethyst,
            DynastyCrimson,
            VenetianRuby,
            NordicTwilight,
            SereneSanctuary,
            BespokeEspresso,
            EditorialParchment,
            FrostSanctum,
            StudioMinimal,
            KyotoMoss,
            CopenhagenSlate,
            AgedInk,
            IvoryColumn,
            SohoTimes,
            ParisReview,
            BodoniMidnight,
            MonographCharcoal,
            GothicScript
        )

        fun fromId(id: String): ThemePreset = availablePresets.find { it.id == id } ?: Default
    }
}

val LocalThemePreset = staticCompositionLocalOf { ThemePreset.Default }
