package com.app.quotely.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

// Font Hierarchy Rules from Stitch: Playfair Display (Serif) & Inter (SansSerif)
val PlayfairDisplayFontFamily = FontFamily.Serif
val InterFontFamily = FontFamily.SansSerif

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
        letterSpacing = 0.1.em
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

/**
 * Standard Material 3 Typography mapping.
 */
val MaterialQuotelyTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = PlayfairDisplayFontFamily,
        fontSize = 48.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 57.6.sp,
        letterSpacing = (-0.02).em
    ),
    displayMedium = TextStyle(
        fontFamily = PlayfairDisplayFontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 38.4.sp
    ),
    displaySmall = TextStyle(
        fontFamily = PlayfairDisplayFontFamily,
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 34.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = PlayfairDisplayFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 32.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = PlayfairDisplayFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 36.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
        letterSpacing = 0.05.em
    ),
    labelMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = 0.1.em
    ),
    labelSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    )
)
