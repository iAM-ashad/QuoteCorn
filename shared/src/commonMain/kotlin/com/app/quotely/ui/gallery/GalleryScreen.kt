package com.app.quotely.ui.gallery

import androidx.compose.foundation.background
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quotely.domain.model.Quote
import com.app.quotely.ui.theme.LocalQuotelyTypography
import com.app.quotely.ui.theme.QuotelyTheme
import com.app.quotely.ui.theme.ThemePreset
import com.app.quotely.ui.theme.WarmGold

/**
 * Pure stateless composable for the Masonry Gallery Feed with delete confirmation dialog.
 */
@Composable
fun GalleryScreen(
    uiState: GalleryUiState,
    onSearchQueryChange: (String) -> Unit,
    onTagSelect: (String?) -> Unit,
    onQuoteClick: (Quote) -> Unit,
    onDeleteQuote: (String) -> Unit,
    onCreateQuoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalQuotelyTypography.current
    var quoteToDeleteId by remember { mutableStateOf<String?>(null) }

    // Delete Confirmation Dialog
    quoteToDeleteId?.let { id ->
        AlertDialog(
            onDismissRequest = { quoteToDeleteId = null },
            title = { Text("Delete Quote", style = typography.uiButton) },
            text = {
                Text(
                    "Are you sure you want to delete this quote? This action cannot be undone.",
                    style = typography.uiLabel
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteQuote(id)
                        quoteToDeleteId = null
                    }
                ) {
                    Text("DELETE", color = Color(0xFFFFB4AB), style = typography.uiButton)
                }
            },
            dismissButton = {
                TextButton(onClick = { quoteToDeleteId = null }) {
                    Text("CANCEL", color = Color.White.copy(alpha = 0.7f), style = typography.uiButton)
                }
            },
            containerColor = Color(0xFF201F1F),
            titleContentColor = Color.White,
            textContentColor = Color.White.copy(alpha = 0.8f),
            shape = RoundedCornerShape(0.dp)
        )
    }

    QuotelyTheme(preset = ThemePreset.AurelianMonolith) {
        Scaffold(
            floatingActionButton = {
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

                // Editorial Header Bar (Clean Positioning)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "QUOTELY",
                            style = typography.quoteDisplayMobile.copy(fontSize = 26.sp),
                            color = Color.White
                        )
                        Text(
                            text = "AURELIAN MONOLITH GALLERY",
                            style = typography.uiLabel.copy(fontSize = 9.sp, letterSpacing = 0.15.sp),
                            color = WarmGold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Search Bar Input
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text("Search quotes, authors, or sources...", style = typography.uiLabel) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
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
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "NO QUOTES FOUND",
                                    style = typography.uiButton.copy(fontSize = 14.sp),
                                    color = Color.White.copy(alpha = 0.5f)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Tap + to capture your first thought.",
                                    style = typography.uiLabel,
                                    color = Color.White.copy(alpha = 0.3f)
                                )
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
