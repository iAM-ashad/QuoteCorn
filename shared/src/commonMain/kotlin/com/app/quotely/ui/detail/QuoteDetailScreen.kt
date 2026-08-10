package com.app.quotely.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quotely.ui.components.ExportAspectRatio
import com.app.quotely.ui.components.ImageSaver
import com.app.quotely.ui.components.rememberImageSaver
import com.app.quotely.ui.theme.LocalQuotelyTypography
import com.app.quotely.ui.theme.QuotelyTheme
import com.app.quotely.ui.theme.WarmGold

/**
 * Pure stateless top-level composable for the Track-Stopper Fullscreen Focus Detail View,
 * equipped with multi-resolution aspect ratio selection and physical device gallery image saving.
 */
@Composable
fun QuoteDetailScreen(
    uiState: QuoteDetailUiState,
    onToggleControls: () -> Unit,
    onThemeSelect: (String) -> Unit,
    onExportClick: (TextMeasurer) -> Unit,
    onSelectAspectRatio: (ExportAspectRatio, TextMeasurer) -> Unit,
    onSaveImageClick: (ImageSaver) -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    onClearExportMessage: () -> Unit,
    onDismissExportModal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val quote = uiState.quote ?: return
    val typography = LocalQuotelyTypography.current
    val textMeasurer = rememberTextMeasurer()
    val imageSaver = rememberImageSaver()
    val snackbarHostState = remember { SnackbarHostState() }
    var showDeleteDialog by remember { mutableStateOf(false) }

    uiState.exportSuccessMessage?.let { msg ->
        LaunchedEffect(msg) {
            snackbarHostState.showSnackbar(msg)
            onClearExportMessage()
        }
    }

    // 1. Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Quote", style = typography.uiButton) },
            text = {
                Text(
                    "Are you sure you want to delete this quote from your collection? This action cannot be undone.",
                    style = typography.uiLabel
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteClick()
                    }
                ) {
                    Text("DELETE", color = Color(0xFFFFB4AB), style = typography.uiButton)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("CANCEL", color = Color.White.copy(alpha = 0.7f), style = typography.uiButton)
                }
            },
            containerColor = Color(0xFF201F1F),
            titleContentColor = Color.White,
            textContentColor = Color.White.copy(alpha = 0.8f),
            shape = RoundedCornerShape(0.dp)
        )
    }

    // 2. Multi-Resolution Image Export Modal
    if (uiState.showExportModal) {
        AlertDialog(
            onDismissRequest = onDismissExportModal,
            title = {
                Text(
                    text = "EXPORT QUOTE IMAGE",
                    style = typography.uiButton.copy(fontSize = 14.sp),
                    color = Color.White
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Choose Image Format:",
                        style = typography.uiLabel,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 8.dp)
                    )

                    // Resolution Chips Row (1:1, 9:16, 3:4, 16:9)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ExportAspectRatio.entries.forEach { format ->
                            val isSelected = uiState.selectedAspectRatio == format
                            val bg = if (isSelected) WarmGold else Color.Transparent
                            val textColor = if (isSelected) Color(0xFF131313) else Color.White
                            val border = if (isSelected) WarmGold else Color.White.copy(alpha = 0.3f)

                            Box(
                                modifier = Modifier
                                    .background(bg)
                                    .border(width = 1.dp, color = border, shape = RoundedCornerShape(0.dp))
                                    .clickable { onSelectAspectRatio(format, textMeasurer) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = format.label,
                                    style = typography.uiButton.copy(fontSize = 11.sp),
                                    color = textColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = uiState.selectedAspectRatio.description,
                        style = typography.uiLabel.copy(fontSize = 10.sp),
                        color = WarmGold,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 12.dp)
                    )

                    // Image Bitmap Render Preview Box
                    uiState.exportedBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Rendered Quote PNG",
                            modifier = Modifier
                                .size(240.dp)
                                .border(width = 1.dp, color = WarmGold, shape = RoundedCornerShape(0.dp))
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { onSaveImageClick(imageSaver) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WarmGold,
                        contentColor = Color(0xFF3C2F00)
                    ),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("SAVE IMAGE", style = typography.uiButton.copy(fontSize = 12.sp))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissExportModal) {
                    Text("CANCEL", style = typography.uiButton.copy(fontSize = 12.sp), color = Color.White.copy(alpha = 0.7f))
                }
            },
            containerColor = Color(0xFF131313),
            titleContentColor = Color.White,
            textContentColor = Color.White.copy(alpha = 0.9f),
            shape = RoundedCornerShape(0.dp)
        )
    }

    QuotelyTheme(preset = uiState.activeThemePreset) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = uiState.activeThemePreset.backgroundColor
        ) { innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Background Fullscreen Focus Canvas
                FocusQuoteCanvas(
                    quote = quote,
                    themePreset = uiState.activeThemePreset,
                    onToggleControls = onToggleControls
                )

                // Bottom Controls Toolbar
                AnimatedVisibility(
                    visible = uiState.isControlsVisible,
                    enter = fadeIn() + slideInVertically { it },
                    exit = fadeOut() + slideOutVertically { it },
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    DetailControlToolbar(
                        activeThemePreset = uiState.activeThemePreset,
                        isExporting = uiState.isExporting,
                        isDeleting = uiState.isDeleting,
                        onThemeSelect = onThemeSelect,
                        onExportClick = { onExportClick(textMeasurer) },
                        onDeleteClick = { showDeleteDialog = true },
                        onDismissPanel = onToggleControls
                    )
                }
            }
        }
    }
}
