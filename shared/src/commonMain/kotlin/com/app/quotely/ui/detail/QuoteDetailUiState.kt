package com.app.quotely.ui.detail

import androidx.compose.ui.graphics.ImageBitmap
import com.app.quotely.domain.model.Quote
import com.app.quotely.ui.components.ExportAspectRatio
import com.app.quotely.ui.theme.ThemePreset

/**
 * Immutable UI State for the Track-Stopper Fullscreen Focus View & Multi-Format Image Export.
 */
data class QuoteDetailUiState(
    val quote: Quote? = null,
    val activeThemePreset: ThemePreset = ThemePreset.AurelianMonolith,
    val isControlsVisible: Boolean = true,
    val isDeleting: Boolean = false,
    val isExporting: Boolean = false,
    val showExportModal: Boolean = false,
    val selectedAspectRatio: ExportAspectRatio = ExportAspectRatio.SQUARE_1_1,
    val exportedBitmap: ImageBitmap? = null,
    val exportSuccessMessage: String? = null
)
