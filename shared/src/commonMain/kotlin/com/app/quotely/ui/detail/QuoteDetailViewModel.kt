package com.app.quotely.ui.detail

import androidx.compose.ui.text.TextMeasurer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.quotely.domain.model.Quote
import com.app.quotely.domain.repository.QuoteRepository
import com.app.quotely.ui.components.ExportAspectRatio
import com.app.quotely.ui.components.ImageExporter
import com.app.quotely.ui.components.ImageSaver
import com.app.quotely.ui.theme.ThemePreset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuoteDetailViewModel(
    private val repository: QuoteRepository,
    private val imageExporter: ImageExporter = ImageExporter()
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuoteDetailUiState())
    val uiState: StateFlow<QuoteDetailUiState> = _uiState.asStateFlow()

    init {
        loadAlbums()
    }

    private fun loadAlbums() {
        viewModelScope.launch {
            repository.getAlbums().collect { albums ->
                _uiState.update { it.copy(availableAlbums = albums) }
            }
        }
    }

    fun setQuote(quote: Quote) {
        val preset = ThemePreset.availablePresets.find { it.id == quote.themePresetId }
            ?: ThemePreset.Default
        _uiState.update {
            it.copy(
                quote = quote,
                activeThemePreset = preset,
                isControlsVisible = true,
                showExportModal = false,
                exportedBitmap = null
            )
        }
    }

    fun toggleControlsVisibility() {
        _uiState.update { it.copy(isControlsVisible = !it.isControlsVisible) }
    }

    fun onThemePresetSelect(presetId: String) {
        val preset = ThemePreset.availablePresets.find { it.id == presetId }
            ?: ThemePreset.Default
        _uiState.update { it.copy(activeThemePreset = preset) }
    }

    fun toggleQuoteAlbum(albumId: String, isAssigned: Boolean) {
        val quoteId = _uiState.value.quote?.id ?: return
        viewModelScope.launch {
            if (isAssigned) {
                repository.addQuoteToAlbum(quoteId, albumId)
                _uiState.update { it.copy(assignedAlbumIds = it.assignedAlbumIds + albumId) }
            } else {
                repository.removeQuoteFromAlbum(quoteId, albumId)
                _uiState.update { it.copy(assignedAlbumIds = it.assignedAlbumIds - albumId) }
            }
        }
    }

    fun deleteQuote(onDeleted: () -> Unit) {
        val quoteId = _uiState.value.quote?.id ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isDeleting = true) }
            repository.deleteQuote(quoteId)
            _uiState.update { it.copy(isDeleting = false) }
            onDeleted()
        }
    }

    fun openExportModal(textMeasurer: TextMeasurer) {
        val quote = _uiState.value.quote ?: return
        val themePreset = _uiState.value.activeThemePreset
        val aspectRatio = _uiState.value.selectedAspectRatio

        viewModelScope.launch {
            _uiState.update { it.copy(isExporting = true) }
            val bitmap = imageExporter.renderQuotePngBitmap(quote, themePreset, aspectRatio, textMeasurer)
            _uiState.update {
                it.copy(
                    isExporting = false,
                    showExportModal = true,
                    exportedBitmap = bitmap
                )
            }
        }
    }

    fun onSelectAspectRatio(aspectRatio: ExportAspectRatio, textMeasurer: TextMeasurer) {
        val quote = _uiState.value.quote ?: return
        val themePreset = _uiState.value.activeThemePreset

        _uiState.update { it.copy(selectedAspectRatio = aspectRatio) }

        viewModelScope.launch {
            val bitmap = imageExporter.renderQuotePngBitmap(quote, themePreset, aspectRatio, textMeasurer)
            _uiState.update { it.copy(exportedBitmap = bitmap) }
        }
    }

    fun saveExportedImage(imageSaver: ImageSaver) {
        val bitmap = _uiState.value.exportedBitmap ?: return
        val format = _uiState.value.selectedAspectRatio
        val quoteId = _uiState.value.quote?.id ?: "Quote"
        val fileName = "Quotely_${quoteId}_${format.label.replace(":", "_")}"

        viewModelScope.launch {
            val isSuccess = imageSaver.saveBitmapToGallery(bitmap, fileName)
            val message = if (isSuccess) {
                "Saved ${format.label} (${format.width}x${format.height}) quote image to your device gallery!"
            } else {
                "Failed to save image to gallery. Please check storage permissions."
            }

            _uiState.update {
                it.copy(
                    showExportModal = false,
                    exportedBitmap = null,
                    exportSuccessMessage = message
                )
            }
        }
    }

    fun dismissExportModal() {
        _uiState.update {
            it.copy(
                showExportModal = false,
                exportedBitmap = null
            )
        }
    }

    fun clearExportMessage() {
        _uiState.update { it.copy(exportSuccessMessage = null) }
    }
}
