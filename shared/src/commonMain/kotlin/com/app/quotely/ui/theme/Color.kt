package com.app.quotely.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Stitch Token: Aurelian Monolith (Dark Obsidian & Warm Gold)
val WarmGold = Color(0xFFF2CA50)
val DarkGoldText = Color(0xFF3C2F00)
val PrimaryGoldContainer = Color(0xFFD4AF37)
val OnPrimaryGoldContainer = Color(0xFF554300)
val InversePrimaryGold = Color(0xFF735C00)

val OffWhiteSecondary = Color(0xFFC6C6C7)
val OnSecondaryDark = Color(0xFF2F3131)
val SecondaryContainerGray = Color(0xFF454747)
val OnSecondaryContainerGray = Color(0xFFB4B5B5)

val SoftPeriwinkle = Color(0xFFBFCDFF)
val OnTertiaryDarkBlue = Color(0xFF082B72)
val TertiaryContainerBlue = Color(0xFF97B0FF)
val OnTertiaryContainerBlue = Color(0xFF254188)

val DeepObsidianBackground = Color(0xFF131313)
val OnObsidianText = Color(0xFFE5E2E1)
val ObsidianSurface = Color(0xFF131313)
val CharcoalSurfaceVariant = Color(0xFF353534)
val WarmMutedSand = Color(0xFFD0C5AF)

val GoldenSandOutline = Color(0xFF99907C)
val DarkGoldenBronzeOutline = Color(0xFF4D4635)

val ErrorCoral = Color(0xFFFFB4AB)
val OnErrorMaroon = Color(0xFF690005)
val ErrorContainerRed = Color(0xFF93000A)
val OnErrorContainerPink = Color(0xFFFFDAD6)

val InverseSurfaceWhite = Color(0xFFE5E2E1)
val InverseOnSurfaceDark = Color(0xFF313030)
val SurfaceTintGold = Color(0xFFE9C349)

// Extended Surface Container Hierarchy (Stitch Design Tokens)
val SurfaceContainerLowest = Color(0xFF0E0E0E) // Pure Void / Card background
val SurfaceContainerLow = Color(0xFF1C1B1B)
val SurfaceContainerMedium = Color(0xFF201F1F)
val SurfaceContainerHigh = Color(0xFF2A2A2A)
val SurfaceContainerHighest = Color(0xFF353534)
val SurfaceBrightElevated = Color(0xFF3A3939)
val SurfaceDimObsidian = Color(0xFF131313)

// Fixed Variant Tokens
val PrimaryFixed = Color(0xFFFFE088)
val PrimaryFixedDim = Color(0xFFE9C349)
val OnPrimaryFixed = Color(0xFF241A00)
val OnPrimaryFixedVariant = Color(0xFF574500)

val SecondaryFixed = Color(0xFFE2E2E2)
val SecondaryFixedDim = Color(0xFFC6C6C7)
val OnSecondaryFixed = Color(0xFF1A1C1C)
val OnSecondaryFixedVariant = Color(0xFF454747)

val TertiaryFixed = Color(0xFFDBE1FF)
val TertiaryFixedDim = Color(0xFFB4C5FF)
val OnTertiaryFixed = Color(0xFF00174B)
val OnTertiaryFixedVariant = Color(0xFF27438A)

/**
 * Material 3 Dark Color Scheme mapped directly from Stitch "Aurelian Monolith" design tokens.
 */
val AurelianDarkColorScheme: ColorScheme = darkColorScheme(
    primary = WarmGold,
    onPrimary = DarkGoldText,
    primaryContainer = PrimaryGoldContainer,
    onPrimaryContainer = OnPrimaryGoldContainer,
    inversePrimary = InversePrimaryGold,
    secondary = OffWhiteSecondary,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerGray,
    onSecondaryContainer = OnSecondaryContainerGray,
    tertiary = SoftPeriwinkle,
    onTertiary = OnTertiaryDarkBlue,
    tertiaryContainer = TertiaryContainerBlue,
    onTertiaryContainer = OnTertiaryContainerBlue,
    background = DeepObsidianBackground,
    onBackground = OnObsidianText,
    surface = ObsidianSurface,
    onSurface = OnObsidianText,
    surfaceVariant = CharcoalSurfaceVariant,
    onSurfaceVariant = WarmMutedSand,
    surfaceTint = SurfaceTintGold,
    inverseSurface = InverseSurfaceWhite,
    inverseOnSurface = InverseOnSurfaceDark,
    error = ErrorCoral,
    onError = OnErrorMaroon,
    errorContainer = ErrorContainerRed,
    onErrorContainer = OnErrorContainerPink,
    outline = GoldenSandOutline,
    outlineVariant = DarkGoldenBronzeOutline,
    scrim = Color.Black
)

/**
 * Optional Material 3 Light Color Scheme fallback.
 */
val AurelianLightColorScheme: ColorScheme = lightColorScheme(
    primary = PrimaryGoldContainer,
    onPrimary = Color.White,
    primaryContainer = PrimaryFixed,
    onPrimaryContainer = OnPrimaryFixed,
    secondary = SecondaryContainerGray,
    onSecondary = Color.White,
    secondaryContainer = SecondaryFixed,
    onSecondaryContainer = OnSecondaryFixed,
    tertiary = OnTertiaryContainerBlue,
    onTertiary = Color.White,
    tertiaryContainer = TertiaryFixed,
    onTertiaryContainer = OnTertiaryFixed,
    background = Color(0xFFFBF9F5),
    onBackground = Color(0xFF1C1B1B),
    surface = Color(0xFFFBF9F5),
    onSurface = Color(0xFF1C1B1B),
    surfaceVariant = Color(0xFFE8E2D5),
    onSurfaceVariant = Color(0xFF4D4635),
    outline = GoldenSandOutline,
    outlineVariant = Color(0xFFC7C0B0),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)
