package com.app.quotely.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quotely.domain.model.Quote
import com.app.quotely.domain.model.Tag
import com.app.quotely.ocr.OcrResult
import com.app.quotely.ui.theme.WarmGold

@Composable
fun OcrVerificationSheet(
    ocrResult: OcrResult,
    availableTags: List<Tag>,
    onDismiss: () -> Unit,
    onSaveQuote: (Quote) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember(ocrResult) { mutableStateOf(ocrResult.cleanedQuoteText) }
    var author by remember(ocrResult) { mutableStateOf(ocrResult.suggestedAuthor ?: "") }
    var source by remember(ocrResult) { mutableStateOf(ocrResult.suggestedSource ?: "") }
    var selectedTagIds by remember { mutableStateOf(setOf(availableTags.firstOrNull()?.id ?: "philosophy")) }
    var selectedThemeId by remember { mutableStateOf("creators_choice") }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, WarmGold, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        color = Color(0xFF131313),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Drag Handle Pill
            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(4.dp)
                    .background(Color(0xFF333333), RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Quotation Monogram Header
            Text(
                text = "“ ”",
                color = WarmGold,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "VERIFY CAPTURED QUOTE",
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )

            Text(
                text = "Review & refine extracted text from camera or screenshot OCR",
                color = Color(0xFFA0A0A0),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
            )

            // Quote Text Input
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Quote Body", color = WarmGold) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = WarmGold,
                    unfocusedBorderColor = Color(0xFF333333),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Author Input
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author", color = WarmGold) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = WarmGold,
                    unfocusedBorderColor = Color(0xFF333333),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Source Input
            OutlinedTextField(
                value = source,
                onValueChange = { source = it },
                label = { Text("Source (Book / Podcast / Article)", color = WarmGold) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = WarmGold,
                    unfocusedBorderColor = Color(0xFF333333),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tag Selector Row
            TagSelectorRow(
                tags = availableTags,
                selectedTagIds = selectedTagIds,
                onTagToggle = { tagId ->
                    selectedTagIds = if (selectedTagIds.contains(tagId)) setOf(tagId) else setOf(tagId)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF444444))
                ) {
                    Text("CANCEL", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = {
                        com.app.quotely.data.telemetry.WacTelemetryTracker.logAction(com.app.quotely.data.telemetry.WacTelemetryTracker.ACTION_CAPTURE_OCR)
                        val newQuote = Quote(
                            id = "quote_" + kotlin.random.Random.nextLong(100000, 999999),
                            text = text.trim(),
                            author = author.trim().ifBlank { "Anonymous" },
                            source = source.trim().ifBlank { null },
                            tagIds = selectedTagIds.toList(),
                            themePresetId = selectedThemeId,
                            createdAt = 1700000000000L
                        )
                        onSaveQuote(newQuote)
                    },
                    enabled = text.isNotBlank(),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WarmGold,
                        contentColor = Color.Black
                    )
                ) {
                    Text("SAVE TO SANCTUARY", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
