package com.app.quotely.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quotely.domain.model.Tag
import com.app.quotely.ui.theme.LocalQuotelyTypography
import com.app.quotely.ui.theme.ThemePreset

/**
 * Aesthetic live preview card for quote styling, matching Stitch "Aurelian Monolith" specs.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LiveQuotePreviewCard(
    quoteText: String,
    authorText: String,
    sourceText: String,
    tags: List<Tag>,
    themePreset: ThemePreset,
    modifier: Modifier = Modifier
) {
    val typography = LocalQuotelyTypography.current
    val displayText = if (quoteText.isBlank()) "Your thought-provoking quote will appear here..." else quoteText
    val displayAuthor = if (authorText.isBlank()) "AUTHOR NAME" else authorText.uppercase()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(themePreset.backgroundGradient)
            .border(
                width = 1.dp,
                color = themePreset.colorScheme.outlineVariant,
                shape = RoundedCornerShape(0.dp)
            )
            .padding(32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            // Quote Text
            Text(
                text = "“$displayText”",
                style = (if (displayText.length > 100) typography.quoteBody else typography.quoteDisplayMobile).copy(
                    fontWeight = themePreset.quoteFontWeight,
                    fontStyle = themePreset.quoteFontStyle
                ),
                color = themePreset.textColor,
                fontFamily = themePreset.quoteFontFamily
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Stitch Signature Accent Divider
            HorizontalDivider(
                modifier = Modifier.width(32.dp),
                thickness = 1.dp,
                color = themePreset.primaryAccent
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Attribution & Source
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = displayAuthor,
                    style = typography.attribution.copy(
                        fontWeight = themePreset.bodyFontWeight,
                        letterSpacing = themePreset.bodyLetterSpacing
                    ),
                    color = themePreset.attributionColor,
                    fontFamily = themePreset.bodyFontFamily,
                    textAlign = TextAlign.End
                )

                if (sourceText.isNotBlank()) {
                    Text(
                        text = "— $sourceText",
                        style = typography.uiLabel.copy(fontStyle = FontStyle.Italic),
                        color = themePreset.attributionColor.copy(alpha = 0.8f),
                        fontFamily = themePreset.bodyFontFamily,
                        textAlign = TextAlign.End,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Tags Flow Row
            if (tags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tags.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = themePreset.textColor.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(0.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = tag.name.uppercase(),
                                style = typography.uiLabel.copy(fontSize = 10.sp),
                                color = themePreset.textColor.copy(alpha = 0.9f),
                                fontFamily = themePreset.bodyFontFamily
                            )
                        }
                    }
                }
            }
        }
    }
}
