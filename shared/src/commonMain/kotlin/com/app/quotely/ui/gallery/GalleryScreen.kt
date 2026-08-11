package com.app.quotely.ui.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.window.Dialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.app.quotely.domain.model.Quote
import com.app.quotely.ui.components.DeleteConfirmationDialog
import com.app.quotely.ui.theme.LocalQuotelyTypography
import com.app.quotely.ui.theme.QuotelyTheme
import com.app.quotely.ui.theme.ThemePreset
import com.app.quotely.ui.theme.WarmGold

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.app.quotely.ui.components.QuotelySnackbar
import kotlinx.coroutines.launch

/**
 * Pure stateless composable for the Masonry Gallery Feed with aesthetic empty state.
 */
@Composable
fun GalleryScreen(
    uiState: GalleryUiState,
    onSearchQueryChange: (String) -> Unit,
    onTagSelect: (String?) -> Unit,
    onAlbumSelect: (String?) -> Unit = {},
    onCreateAlbum: (com.app.quotely.domain.model.Album) -> Unit = {},
    onQuoteClick: (Quote) -> Unit,
    onDeleteQuote: (String) -> Unit,
    onCreateQuoteClick: () -> Unit,
    onSaveQuote: (Quote) -> Unit = {},
    onClearUserMessage: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val typography = LocalQuotelyTypography.current
    var quoteToDeleteId by remember { mutableStateOf<String?>(null) }
    var showCreateAlbumDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    uiState.userMessage?.let { msg ->
        LaunchedEffect(msg) {
            snackbarHostState.showSnackbar(msg)
            onClearUserMessage()
        }
    }

    // Delete Confirmation Dialog
    quoteToDeleteId?.let { id ->
        DeleteConfirmationDialog(
            onConfirmDelete = {
                onDeleteQuote(id)
                quoteToDeleteId = null
            },
            onDismiss = { quoteToDeleteId = null }
        )
    }

    if (showCreateAlbumDialog) {
        CreateAlbumDialog(
            onDismiss = { showCreateAlbumDialog = false },
            onCreateAlbum = { album ->
                onCreateAlbum(album)
                showCreateAlbumDialog = false
            }
        )
    }

    val coroutineScope = rememberCoroutineScope()
    var pendingOcrResult by remember { mutableStateOf<com.app.quotely.ocr.OcrResult?>(null) }
    var isOcrScanning by remember { mutableStateOf(false) }

    // OCR Verification Sheet Modal
    pendingOcrResult?.let { ocrResult ->
        com.app.quotely.ui.editor.OcrVerificationSheet(
            ocrResult = ocrResult,
            availableTags = uiState.tags,
            onDismiss = { pendingOcrResult = null },
            onSaveQuote = { newQuote ->
                onSaveQuote(newQuote)
                pendingOcrResult = null
            }
        )
    }

    var showOcrSourcePicker by remember { mutableStateOf(false) }

    // OCR Image Source Picker Dialog Modal
    if (showOcrSourcePicker) {
        Dialog(onDismissRequest = { showOcrSourcePicker = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, WarmGold, RoundedCornerShape(12.dp)),
                color = Color(0xFF131313),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("“ ”", color = WarmGold, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "SCAN QUOTE OCR",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Select image source to extract quote text",
                        color = Color(0xFFA0A0A0),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                    )
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                    Spacer(modifier = Modifier.height(16.dp))

                    // Option 1: Gallery / Screenshot
                    Button(
                        onClick = {
                            showOcrSourcePicker = false
                            val scanned = "We suffer more in imagination than in reality. — Seneca, Letters from a Stoic"
                            if (com.app.quotely.domain.util.OcrTextParser.isValidQuoteText(scanned)) {
                                pendingOcrResult = com.app.quotely.domain.util.OcrTextParser.parse(scanned)
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("No relevant quote text found in image.")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF222222), contentColor = Color.White),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text("🖼️ SELECT FROM GALLERY / SCREENSHOT", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Option 2: Camera Capture
                    Button(
                        onClick = {
                            showOcrSourcePicker = false
                            val scanned = "He who fears death will never do anything worthy of a man who is alive. — Seneca, Moral Letters"
                            if (com.app.quotely.domain.util.OcrTextParser.isValidQuoteText(scanned)) {
                                pendingOcrResult = com.app.quotely.domain.util.OcrTextParser.parse(scanned)
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("No relevant quote text found in photo.")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF222222), contentColor = Color.White),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text("📸 CAPTURE WITH CAMERA", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Option 3: Complex / Garbage Image simulation
                    OutlinedButton(
                        onClick = {
                            showOcrSourcePicker = false
                            val garbageText = "1080p Bluray"
                            if (com.app.quotely.domain.util.OcrTextParser.isValidQuoteText(garbageText)) {
                                pendingOcrResult = com.app.quotely.domain.util.OcrTextParser.parse(garbageText)
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("No relevant quote text found in image.")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White.copy(alpha = 0.6f)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF444444)),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text("⚠️ TEST UNREADABLE SCREENSHOT", fontSize = 10.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = { showOcrSourcePicker = false }) {
                        Text("CANCEL", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp)
                    }
                }
            }
        }
    }

    QuotelyTheme(preset = ThemePreset.CreatorsChoice) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    QuotelySnackbar(snackbarData = data)
                }
            },
            floatingActionButton = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FloatingActionButton(
                        onClick = { showOcrSourcePicker = true },
                        containerColor = Color(0xFF222222),
                        contentColor = WarmGold,
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Text(
                            text = "📷 OCR",
                            style = typography.uiButton.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                    if (uiState.quotes.isNotEmpty()) {
                        FloatingActionButton(
                            onClick = onCreateQuoteClick,
                            containerColor = WarmGold,
                            contentColor = Color(0xFF3C2F00),
                            shape = RoundedCornerShape(0.dp)
                        ) {
                            Text(
                                text = "+",
                                style = typography.quoteDisplayMobile.copy(fontSize = 28.sp),
                                textAlign = TextAlign.Center
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
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Bold Monolithic QuoteCorn Editorial Brand Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Golden Monogram Icon
                    Text(
                        text = "“ ”",
                        style = typography.quoteDisplay.copy(fontSize = 36.sp),
                        color = WarmGold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    // Majestic Bold Brand Title
                    Text(
                        text = "QUOTECORN",
                        style = typography.quoteDisplay.copy(
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.12.em
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Editorial Flanked Tagline
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.width(32.dp),
                            thickness = 1.dp,
                            color = WarmGold.copy(alpha = 0.5f)
                        )
                        Text(
                            text = "CAPTURE WHAT MOVES YOU",
                            style = typography.uiButton.copy(
                                fontSize = 10.sp,
                                letterSpacing = 0.2.em,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = WarmGold,
                            textAlign = TextAlign.Center
                        )
                        HorizontalDivider(
                            modifier = Modifier.width(32.dp),
                            thickness = 1.dp,
                            color = WarmGold.copy(alpha = 0.5f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Search Bar Input with Clear Search Icon Button
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text("Search quotes, authors, or sources...", style = typography.uiLabel) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = {
                        Text(
                            text = "⌕",
                            style = typography.quoteDisplayMobile.copy(fontSize = 20.sp),
                            color = WarmGold
                        )
                    },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            Text(
                                text = "✕",
                                style = typography.uiLabel.copy(fontSize = 12.sp),
                                color = WarmGold,
                                modifier = Modifier
                                    .clickable { onSearchQueryChange("") }
                                    .padding(8.dp)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WarmGold,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                        focusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
                        unfocusedPlaceholderColor = Color.White.copy(alpha = 0.4f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(0.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Album Carousel Row
                AlbumCarouselRow(
                    albums = uiState.albums,
                    selectedAlbumId = uiState.selectedAlbumId,
                    onAlbumSelect = onAlbumSelect,
                    onCreateAlbumClick = { showCreateAlbumDialog = true }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Tag Filter Row
                TagFilterRow(
                    tags = uiState.tags,
                    selectedTagId = uiState.selectedTagId,
                    onTagSelect = onTagSelect
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Content Area
                when {
                    uiState.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = WarmGold)
                        }
                    }

                    uiState.quotes.isEmpty() -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(24.dp)
                            ) {
                                Text(
                                    text = "“ ”",
                                    style = typography.quoteDisplayMobile.copy(fontSize = 54.sp),
                                    color = WarmGold
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "YOUR SANCTUARY IS EMPTY",
                                    style = typography.quoteDisplayMobile.copy(fontSize = 20.sp),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Capture timeless wisdom, excerpts, and thoughts to build your personal sanctuary.",
                                    style = typography.uiLabel.copy(fontSize = 12.sp),
                                    color = Color.White.copy(alpha = 0.6f),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Button(
                                    onClick = onCreateQuoteClick,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = WarmGold,
                                        contentColor = Color(0xFF3C2F00)
                                    ),
                                    shape = RoundedCornerShape(0.dp),
                                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                                ) {
                                    Text(
                                        text = "+ CREATE YOUR FIRST QUOTE",
                                        style = typography.uiButton.copy(fontSize = 12.sp)
                                    )
                                }
                            }
                        }
                    }

                    else -> {
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalItemSpacing = 12.dp,
                            contentPadding = PaddingValues(bottom = 80.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            items(uiState.quotes, key = { it.id }) { quote ->
                                QuoteGridCard(
                                    quote = quote,
                                    tags = uiState.tags,
                                    onClick = { onQuoteClick(quote) },
                                    onDeleteClick = { quoteToDeleteId = quote.id }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
