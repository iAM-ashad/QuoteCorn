package com.app.quotely.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import com.app.quotely.domain.model.Quote
import com.app.quotely.ui.theme.ThemePreset

/**
 * Pure Kotlin Compose Multiplatform Image Export Engine for high-res PNG quote image rendering.
 */
class ImageExporter {

    /**
     * Renders a high-resolution bitmap with quote text, author attribution, and divider line.
     */
    fun renderQuotePngBitmap(
        quote: Quote,
        themePreset: ThemePreset,
        aspectRatio: ExportAspectRatio = ExportAspectRatio.SQUARE_1_1,
        textMeasurer: TextMeasurer
    ): ImageBitmap {
        val width = aspectRatio.width
        val height = aspectRatio.height
        val bitmap = ImageBitmap(width, height)
        val canvas = Canvas(bitmap)
        val drawScope = CanvasDrawScope()

        drawScope.draw(
            density = Density(2.0f, 1f),
            layoutDirection = LayoutDirection.Ltr,
            canvas = canvas,
            size = Size(width.toFloat(), height.toFloat())
        ) {
            // 1. Draw Theme Background Gradient
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        themePreset.backgroundColor,
                        themePreset.surfaceColor,
                        themePreset.backgroundColor
                    )
                ),
                size = size
            )

            // 2. Draw Outer Border (1px Charcoal Outline)
            drawRect(
                color = themePreset.colorScheme.outlineVariant,
                style = Stroke(width = 4f),
                size = size
            )

            // 3. Measure & Draw Quote Text
            val quoteFontSize = if (quote.text.length > 100) 36.sp else 46.sp
            val quoteConstraints = Constraints(maxWidth = (width - 160))

            val measuredQuote = textMeasurer.measure(
                text = AnnotatedString("“${quote.text}”"),
                style = TextStyle(
                    fontFamily = themePreset.quoteFontFamily,
                    fontWeight = themePreset.quoteFontWeight,
                    fontStyle = themePreset.quoteFontStyle,
                    fontSize = quoteFontSize,
                    color = themePreset.textColor,
                    textAlign = TextAlign.Center
                ),
                constraints = quoteConstraints
            )

            val quoteY = (height - measuredQuote.size.height) / 2f - 60f
            drawText(
                textLayoutResult = measuredQuote,
                topLeft = Offset(
                    x = (width - measuredQuote.size.width) / 2f,
                    y = quoteY
                )
            )

            // 4. Draw Accent Divider Line
            val dividerY = quoteY + measuredQuote.size.height + 40f
            val centerX = width / 2f
            drawLine(
                color = themePreset.primaryAccent,
                start = Offset(centerX - 60f, dividerY),
                end = Offset(centerX + 60f, dividerY),
                strokeWidth = 4f
            )

            // 5. Measure & Draw Author Attribution
            val measuredAuthor = textMeasurer.measure(
                text = AnnotatedString("— ${quote.author.uppercase()}"),
                style = TextStyle(
                    fontFamily = themePreset.bodyFontFamily,
                    fontWeight = themePreset.bodyFontWeight,
                    letterSpacing = themePreset.bodyLetterSpacing,
                    fontSize = 24.sp,
                    color = themePreset.attributionColor,
                    textAlign = TextAlign.Center
                )
            )

            val authorY = dividerY + 32f
            drawText(
                textLayoutResult = measuredAuthor,
                topLeft = Offset(
                    x = (width - measuredAuthor.size.width) / 2f,
                    y = authorY
                )
            )

            // 6. Draw Optional Source Subtitle
            quote.source?.let { src ->
                val measuredSource = textMeasurer.measure(
                    text = AnnotatedString(src),
                    style = TextStyle(
                        fontFamily = themePreset.bodyFontFamily,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic,
                        color = themePreset.attributionColor.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                )

                drawText(
                    textLayoutResult = measuredSource,
                    topLeft = Offset(
                        x = (width - measuredSource.size.width) / 2f,
                        y = authorY + measuredAuthor.size.height + 12f
                    )
                )
            }
        }

        return bitmap
    }
}

@Composable
fun rememberImageExporter(): ImageExporter {
    return remember { ImageExporter() }
}
