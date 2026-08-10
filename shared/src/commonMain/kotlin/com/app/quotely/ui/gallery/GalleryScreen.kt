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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.app.quotely.ui.components.DeleteConfirmationDialog
import com.app.quotely.ui.theme.LocalQuotelyTypography
import com.app.quotely.ui.theme.QuotelyTheme
import com.app.quotely.ui.theme.ThemePreset
import com.app.quotely.ui.theme.WarmGold

/**
 * Pure stateless composable for the Masonry Gallery Feed with aesthetic empty state.
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
        DeleteConfirmationDialog(
            onConfirmDelete = {
                onDeleteQuote(id)
                quoteToDeleteId = null
            },
            onDismiss = { quoteToDeleteId = null }
        )
    }

    QuotelyTheme(preset = ThemePreset.CreatorsChoice) {
        Scaffold(
            floatingActionButton = {
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

                // Centered QuoteCorn Editorial Header Bar
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "QuoteCorn",
                        style = typography.quoteDisplayMobile.copy(fontSize = 28.sp),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "CAPTURE WHAT MOVES YOU",
                        style = typography.uiLabel.copy(fontSize = 9.sp, letterSpacing = 0.15.sp),
                        color = WarmGold,
                        textAlign = TextAlign.Center
                    )
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
