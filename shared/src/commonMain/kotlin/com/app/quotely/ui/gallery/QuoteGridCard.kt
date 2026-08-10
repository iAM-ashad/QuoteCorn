package com.app.quotely.ui.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quotely.domain.model.Quote
import com.app.quotely.domain.model.Tag
import com.app.quotely.ui.theme.LocalQuotelyTypography
import com.app.quotely.ui.theme.ThemePreset

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuoteGridCard(
    quote: Quote,
    tags: List<Tag>,
    onClick: () -> Unit,
    onDeleteClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val themePreset = ThemePreset.availablePresets.find { it.id == quote.themePresetId }
        ?: ThemePreset.Default
    val typography = LocalQuotelyTypography.current
    val matchingTags = tags.filter { quote.tagIds.contains(it.id) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(themePreset.backgroundGradient)
            .border(
                width = 1.dp,
                color = themePreset.colorScheme.outlineVariant,
                shape = RoundedCornerShape(0.dp)
            )
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Quote Text
            Text(
                text = "“${quote.text}”",
                style = typography.quoteBody.copy(
                    fontSize = 18.sp,
                    lineHeight = 26.sp,
                    fontWeight = themePreset.quoteFontWeight,
                    fontStyle = themePreset.quoteFontStyle
                ),
                color = themePreset.textColor,
                fontFamily = themePreset.quoteFontFamily
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Signature Accent Divider
            HorizontalDivider(
                modifier = Modifier.width(24.dp),
                thickness = 1.dp,
                color = themePreset.primaryAccent
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Author & Source
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = quote.author.uppercase(),
                        style = typography.attribution.copy(
                            fontSize = 12.sp,
                            fontWeight = themePreset.bodyFontWeight,
                            letterSpacing = themePreset.bodyLetterSpacing
                        ),
                        color = themePreset.attributionColor,
                        fontFamily = themePreset.bodyFontFamily
                    )

                    quote.source?.let { src ->
                        Text(
                            text = src,
                            style = typography.uiLabel.copy(fontSize = 10.sp, fontStyle = FontStyle.Italic),
                            color = themePreset.attributionColor.copy(alpha = 0.7f),
                            fontFamily = themePreset.bodyFontFamily,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                onDeleteClick?.let { onDelete ->
                    Text(
                        text = "✕",
                        style = typography.uiLabel.copy(fontSize = 12.sp),
                        color = themePreset.attributionColor.copy(alpha = 0.5f),
                        modifier = Modifier
                            .clickable { onDelete() }
                            .padding(4.dp)
                    )
                }
            }

            // Tag Chips
            if (matchingTags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(14.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    matchingTags.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = themePreset.textColor.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(0.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = tag.name.uppercase(),
                                style = typography.uiLabel.copy(fontSize = 9.sp),
                                color = themePreset.textColor.copy(alpha = 0.8f),
                                fontFamily = themePreset.bodyFontFamily
                            )
                        }
                    }
                }
            }
        }
    }
}
