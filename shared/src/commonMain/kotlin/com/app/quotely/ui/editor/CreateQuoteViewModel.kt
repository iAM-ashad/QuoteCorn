package com.app.quotely.ui.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.quotely.domain.model.Quote
import com.app.quotely.domain.model.Tag
import com.app.quotely.domain.repository.QuoteRepository
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
    }

    private fun loadTags() {
        viewModelScope.launch {
            repository.getTags().collect { tags ->
                if (tags.isEmpty()) {
                    val defaultTags = listOf(
                        Tag("1", "Philosophy", "#D4AF37"),
                        Tag("2", "Mindfulness", "#BFCDFF"),
                        Tag("3", "Literature", "#C6C6C7"),
                        Tag("4", "Stoicism", "#E5E2E1")
                    )
                    defaultTags.forEach { repository.saveTag(it) }
                    _uiState.update { it.copy(availableTags = defaultTags) }
                } else {
                    _uiState.update { it.copy(availableTags = tags) }
                }
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

    fun onThemePresetSelect(presetId: String) {
        _uiState.update { it.copy(selectedThemePresetId = presetId) }
    }

    fun saveQuote() {
        val state = _uiState.value
        if (state.quoteText.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            val newQuote = Quote(
                id = "quote_" + (100000..999999).random(),
                text = state.quoteText.trim(),
                author = if (state.authorText.isBlank()) "Anonymous" else state.authorText.trim(),
                source = if (state.sourceText.isBlank()) null else state.sourceText.trim(),
                tagIds = state.selectedTagIds.toList(),
                themePresetId = state.selectedThemePresetId,
                createdAt = 1700000000000L
            )
            val result = repository.saveQuote(newQuote)
            if (result.isSuccess) {
                _uiState.update { it.copy(isSaving = false, isSaveSuccess = true) }
            } else {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = result.exceptionOrNull()?.message ?: "Failed to save quote"
                    )
                }
            }
        }
    }

    fun resetSaveSuccess() {
        _uiState.update { it.copy(isSaveSuccess = false) }
    }
}
