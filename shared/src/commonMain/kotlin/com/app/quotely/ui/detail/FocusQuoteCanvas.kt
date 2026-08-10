package com.app.quotely.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quotely.domain.model.Quote
import com.app.quotely.ui.theme.LocalQuotelyTypography
import com.app.quotely.ui.theme.ThemePreset

/**
 * Fullscreen distraction-free canvas displaying high-fidelity editorial quote typography.
 */
@Composable
fun FocusQuoteCanvas(
    quote: Quote,
    themePreset: ThemePreset,
    onToggleControls: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalQuotelyTypography.current
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(themePreset.backgroundGradient)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onToggleControls
            )
            .padding(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Quote Text
            Text(
                text = "“${quote.text}”",
                style = (if (quote.text.length > 120) typography.quoteBody.copy(fontSize = 22.sp, lineHeight = 32.sp)
                else typography.quoteDisplayMobile.copy(fontSize = 32.sp, lineHeight = 44.sp)).copy(
                    fontWeight = themePreset.quoteFontWeight,
                    fontStyle = themePreset.quoteFontStyle
                ),
                color = themePreset.textColor,
                fontFamily = themePreset.quoteFontFamily,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Signature Accent Divider
            HorizontalDivider(
                modifier = Modifier.width(48.dp),
                thickness = 1.dp,
                color = themePreset.primaryAccent
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Attribution & Source
            Text(
                text = quote.author.uppercase(),
                style = typography.attribution.copy(
                    fontSize = 14.sp,
                    fontWeight = themePreset.bodyFontWeight,
                    letterSpacing = themePreset.bodyLetterSpacing
                ),
                color = themePreset.attributionColor,
                fontFamily = themePreset.bodyFontFamily,
                textAlign = TextAlign.Center
            )

            quote.source?.let { src ->
                Text(
                    text = "— $src",
                    style = typography.uiLabel.copy(fontSize = 11.sp, fontStyle = FontStyle.Italic),
                    color = themePreset.attributionColor.copy(alpha = 0.7f),
                    fontFamily = themePreset.bodyFontFamily,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
