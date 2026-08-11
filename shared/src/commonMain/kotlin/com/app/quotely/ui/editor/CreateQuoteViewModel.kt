package com.app.quotely.ui.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.quotely.domain.model.DefaultTags
import com.app.quotely.domain.model.Quote
import com.app.quotely.domain.model.Tag
import com.app.quotely.domain.repository.QuoteRepository
import com.app.quotely.ui.theme.ThemePreset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateQuoteViewModel(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateQuoteUiState())
    val uiState: StateFlow<CreateQuoteUiState> = _uiState.asStateFlow()

    init {
        loadTags()
        loadAlbums()
    }

    private fun loadTags() {
        viewModelScope.launch {
            repository.getTags().collect { tags ->
                if (tags.isEmpty()) {
                    val defaultTags = DefaultTags.list
                    defaultTags.forEach { repository.saveTag(it) }
                    _uiState.update { it.copy(availableTags = defaultTags) }
                } else {
                    _uiState.update { it.copy(availableTags = tags) }
                }
            }
        }
    }

    private fun loadAlbums() {
        viewModelScope.launch {
            repository.getAlbums().collect { albums ->
                _uiState.update { it.copy(availableAlbums = albums) }
            }
        }
    }

    fun onQuoteTextChange(text: String) {
        _uiState.update { it.copy(quoteText = text) }
    }

    fun onAuthorTextChange(author: String) {
        _uiState.update { it.copy(authorText = author) }
    }

    fun onSourceTextChange(source: String) {
        _uiState.update { it.copy(sourceText = source) }
    }

    fun toggleTagSelection(tagId: String) {
        _uiState.update { state ->
            val updated = if (state.selectedTagIds.contains(tagId)) {
                state.selectedTagIds - tagId
            } else {
                state.selectedTagIds + tagId
            }
            state.copy(selectedTagIds = updated)
        }
    }

    fun onAlbumSelect(albumId: String?) {
        _uiState.update { state ->
            val newSelected = if (state.selectedAlbumId == albumId) null else albumId
            state.copy(selectedAlbumId = newSelected)
        }
    }

    fun onThemePresetSelect(presetId: String) {
        _uiState.update {
            it.copy(selectedThemePresetId = presetId)
        }
    }

    fun saveQuote() {
        val current = _uiState.value
        if (current.quoteText.isBlank()) return

        val newQuote = Quote(
            id = "quote_${(100000..999999).random()}",
            text = current.quoteText.trim(),
            author = if (current.authorText.isBlank()) "Anonymous" else current.authorText.trim(),
            source = current.sourceText.trim().ifEmpty { null },
            tagIds = current.selectedTagIds.toList(),
            themePresetId = current.selectedThemePresetId,
            createdAt = 1700000000000L
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            repository.saveQuote(newQuote)
            current.selectedAlbumId?.let { albumId ->
                repository.addQuoteToAlbum(newQuote.id, albumId)
            }
            _uiState.update {
                it.copy(
                    isSaving = false,
                    isSaveSuccess = true,
                    quoteText = "",
                    authorText = "",
                    sourceText = "",
                    selectedTagIds = emptySet(),
                    selectedAlbumId = null
                )
            }
        }
    }
}
