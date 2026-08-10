package com.app.quotely.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.quotely.domain.model.Quote
import com.app.quotely.domain.model.Tag
import com.app.quotely.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GalleryViewModel(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GalleryUiState())
    val uiState: StateFlow<GalleryUiState> = _uiState.asStateFlow()

    private var allQuotesCache: List<Quote> = emptyList()

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            combine(
                repository.getQuotes(),
                repository.getTags()
            ) { quotes, tags ->
                if (quotes.isEmpty()) {
                    seedSampleQuotes()
                } else {
                    allQuotesCache = quotes
                    _uiState.update { state ->
                        state.copy(
                            quotes = filterQuotes(quotes, state.searchQuery, state.selectedTagId),
                            tags = tags,
                            isLoading = false
                        )
                    }
                }
            }.collect {}
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { state ->
            state.copy(
                searchQuery = query,
                quotes = filterQuotes(allQuotesCache, query, state.selectedTagId)
            )
        }
    }

    fun onTagSelect(tagId: String?) {
        _uiState.update { state ->
            val newSelectedTagId = if (state.selectedTagId == tagId) null else tagId
            state.copy(
                selectedTagId = newSelectedTagId,
                quotes = filterQuotes(allQuotesCache, state.searchQuery, newSelectedTagId)
            )
        }
    }

    fun selectQuoteForFocus(quote: Quote?) {
        _uiState.update { it.copy(selectedQuoteForFocus = quote) }
    }

    fun deleteQuote(quoteId: String) {
        viewModelScope.launch {
            repository.deleteQuote(quoteId)
        }
    }

    private fun filterQuotes(quotes: List<Quote>, query: String, tagId: String?): List<Quote> {
        return quotes.filter { quote ->
            val matchesQuery = query.isBlank() ||
                quote.text.contains(query, ignoreCase = true) ||
                quote.author.contains(query, ignoreCase = true) ||
                (quote.source?.contains(query, ignoreCase = true) == true)
            val matchesTag = tagId == null || quote.tagIds.contains(tagId)
            matchesQuery && matchesTag
        }
    }

    private suspend fun seedSampleQuotes() {
        val sampleTags = listOf(
            Tag("1", "Philosophy", "#D4AF37"),
            Tag("2", "Mindfulness", "#BFCDFF"),
            Tag("3", "Literature", "#C6C6C7"),
            Tag("4", "Stoicism", "#E5E2E1")
        )
        sampleTags.forEach { repository.saveTag(it) }

        val sampleQuotes = listOf(
            Quote(
                id = "sample_1",
                text = "We suffer more often in imagination than in reality.",
                author = "Seneca",
                source = "Letters from a Stoic",
                tagIds = listOf("1", "4"),
                themePresetId = "aurelian_monolith",
                createdAt = 1700000000000L
            ),
            Quote(
                id = "sample_2",
                text = "The soul becomes dyed with the color of its thoughts.",
                author = "Marcus Aurelius",
                source = "Meditations",
                tagIds = listOf("1", "2"),
                themePresetId = "midnight_obsidian",
                createdAt = 1700000001000L
            ),
            Quote(
                id = "sample_3",
                text = "It is never too late to be what you might have been.",
                author = "George Eliot",
                source = "Selected Essays",
                tagIds = listOf("3"),
                themePresetId = "editorial_parchment",
                createdAt = 1700000002000L
            ),
            Quote(
                id = "sample_4",
                text = "He who has a why to live can bear almost any how.",
                author = "Friedrich Nietzsche",
                source = "Twilight of the Idols",
                tagIds = listOf("1"),
                themePresetId = "aurelian_monolith",
                createdAt = 1700000003000L
            )
        )
        sampleQuotes.forEach { repository.saveQuote(it) }
    }
}
