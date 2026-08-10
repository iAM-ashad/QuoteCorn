package com.app.quotely.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

// Editorial Quote Display Font Families
val PlayfairDisplayFontFamily = FontFamily.Serif
val CormorantGaramondFontFamily = FontFamily.Serif
val CinzelFontFamily = FontFamily.Serif
val BodoniModaFontFamily = FontFamily.Serif
val LoraFontFamily = FontFamily.Serif
val FrauncesFontFamily = FontFamily.Serif
val PrataFontFamily = FontFamily.Serif
val InstrumentSerifFontFamily = FontFamily.Serif
val NewsreaderFontFamily = FontFamily.Serif

// Editorial Attribution Body Font Families
val InterFontFamily = FontFamily.SansSerif
val PlusJakartaSansFontFamily = FontFamily.SansSerif
val SpaceMonoFontFamily = FontFamily.Monospace
val EBGaramondFontFamily = FontFamily.Serif
val OutfitFontFamily = FontFamily.SansSerif
val InconsolataFontFamily = FontFamily.Monospace
val SatoshiFontFamily = FontFamily.SansSerif
val DMSansFontFamily = FontFamily.SansSerif
val SourceSans3FontFamily = FontFamily.SansSerif

/**
 * Material 3 standard typography bridge for Quotely UI components.
 */
val MaterialQuotelyTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = PlayfairDisplayFontFamily,
        fontSize = 48.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    )
)

/**
 * Extended Typography System for Quotely editorial quotes and UI structure.
 */
@Immutable
data class QuotelyTypography(
    val quoteDisplay: TextStyle = TextStyle(
        fontFamily = PlayfairDisplayFontFamily,
        fontSize = 48.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 57.6.sp,
        letterSpacing = (-0.02).em
    ),
    val quoteDisplayMobile: TextStyle = TextStyle(
        fontFamily = PlayfairDisplayFontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 38.4.sp
    ),
    val quoteBody: TextStyle = TextStyle(
        fontFamily = PlayfairDisplayFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 36.sp
    ),
    val attribution: TextStyle = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = 0.15.em
    ),
    val uiLabel: TextStyle = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    ),
    val uiButton: TextStyle = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
        letterSpacing = 0.05.em
    )
)

val LocalQuotelyTypography = staticCompositionLocalOf { QuotelyTypography() }
