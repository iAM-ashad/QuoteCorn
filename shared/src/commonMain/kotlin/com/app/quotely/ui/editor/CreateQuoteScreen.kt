package com.app.quotely.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quotely.ui.theme.LocalQuotelyTypography
import com.app.quotely.ui.theme.QuotelyTheme
import com.app.quotely.ui.theme.WarmGold

/**
 * Pure stateless composable for the Quote Capture Screen and Live Preview Editor.
 */
@Composable
fun CreateQuoteScreen(
    uiState: CreateQuoteUiState,
    onQuoteTextChange: (String) -> Unit,
    onAuthorTextChange: (String) -> Unit,
    onSourceTextChange: (String) -> Unit,
    onTagToggle: (String) -> Unit,
    onAlbumSelect: (String?) -> Unit = {},
    onThemeSelect: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalQuotelyTypography.current
    val selectedTags = uiState.availableTags.filter { uiState.selectedTagIds.contains(it.id) }

    QuotelyTheme(preset = uiState.activeThemePreset) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF131313))
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onBackClick) {
                        Text(
                            text = "CANCEL",
                            style = typography.uiButton.copy(fontSize = 12.sp),
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }

                    Text(
                        text = "NEW QUOTE",
                        style = typography.uiButton.copy(fontSize = 14.sp),
                        color = Color.White
                    )

                    Button(
                        onClick = onSaveClick,
                        enabled = uiState.quoteText.isNotBlank() && !uiState.isSaving,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WarmGold,
                            contentColor = Color(0xFF3C2F00),
                            disabledContainerColor = WarmGold.copy(alpha = 0.3f),
                            disabledContentColor = Color.White.copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        if (uiState.isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(2.dp),
                                color = Color(0xFF3C2F00),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "SAVE",
                                style = typography.uiButton.copy(fontSize = 12.sp)
                            )
                        }
                    }
                }
            },
            containerColor = Color(0xFF131313)
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Section 1: Live Interactive Preview Card
                LiveQuotePreviewCard(
                    quoteText = uiState.quoteText,
                    authorText = uiState.authorText,
                    sourceText = uiState.sourceText,
                    tags = selectedTags,
                    themePreset = uiState.activeThemePreset
                )

                // Section 2: Input Controls Sheet
                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Quote Text Input
                    OutlinedTextField(
                        value = uiState.quoteText,
                        onValueChange = onQuoteTextChange,
                        label = { Text("Quote Text", style = typography.uiLabel) },
                        placeholder = { Text("Type or paste your quote here...", style = typography.uiLabel) },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 6,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WarmGold,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                            focusedLabelColor = WarmGold,
                            unfocusedLabelColor = Color.White.copy(alpha = 0.6f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(0.dp)
                    )

                    // Author Input
                    OutlinedTextField(
                        value = uiState.authorText,
                        onValueChange = onAuthorTextChange,
                        label = { Text("Author / Speaker", style = typography.uiLabel) },
                        placeholder = { Text("e.g. Marcus Aurelius", style = typography.uiLabel) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WarmGold,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                            focusedLabelColor = WarmGold,
                            unfocusedLabelColor = Color.White.copy(alpha = 0.6f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(0.dp)
                    )

                    // Source Input
                    OutlinedTextField(
                        value = uiState.sourceText,
                        onValueChange = onSourceTextChange,
                        label = { Text("Source / Book Title (Optional)", style = typography.uiLabel) },
                        placeholder = { Text("e.g. Meditations, Book IV", style = typography.uiLabel) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WarmGold,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                            focusedLabelColor = WarmGold,
                            unfocusedLabelColor = Color.White.copy(alpha = 0.6f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(0.dp)
                    )

                    // Tag Selector Row
                    TagSelectorRow(
                        tags = uiState.availableTags,
                        selectedTagIds = uiState.selectedTagIds,
                        onTagToggle = onTagToggle,
                        modifier = Modifier.padding(top = 6.dp)
                    )

                    // Album Selector Row
                    if (uiState.availableAlbums.isNotEmpty()) {
                        com.app.quotely.ui.gallery.AlbumCarouselRow(
                            albums = uiState.availableAlbums,
                            selectedAlbumId = uiState.selectedAlbumId,
                            onAlbumSelect = onAlbumSelect,
                            onCreateAlbumClick = {},
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }

                    // Theme Selector Row
                    ThemeSelectorRow(
                        selectedThemePresetId = uiState.selectedThemePresetId,
                        onThemeSelect = onThemeSelect,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
