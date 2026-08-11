package com.app.quotely.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.quotely.domain.model.Album
import com.app.quotely.domain.model.DefaultTags
import com.app.quotely.domain.model.Quote
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
    private var hasCheckedInitialSeed = false

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            repository.seedStarterAlbumsIfEmpty()
            _uiState.update { it.copy(isLoading = true) }
            combine(
                repository.getQuotes(),
                repository.getTags(),
                repository.getAlbums()
            ) { quotes, tags, albums ->
                if (!hasCheckedInitialSeed && quotes.isEmpty() && tags.isEmpty()) {
                    hasCheckedInitialSeed = true
                    seedSampleQuotes()
                } else {
                    hasCheckedInitialSeed = true
                    allQuotesCache = quotes
                    _uiState.update { state ->
                        state.copy(
                            tags = if (tags.isEmpty()) DefaultTags.list else tags,
                            albums = albums,
                            isLoading = false
                        )
                    }
                    updateFilteredQuotes()
                }
            }.collect {}
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { state ->
            state.copy(searchQuery = query)
        }
        updateFilteredQuotes()
    }

    fun onTagSelect(tagId: String?) {
        _uiState.update { state ->
            val newSelectedTagId = if (state.selectedTagId == tagId) null else tagId
            state.copy(selectedTagId = newSelectedTagId)
        }
        updateFilteredQuotes()
    }

    fun onAlbumSelect(albumId: String?) {
        val currentSelected = _uiState.value.selectedAlbumId
        val targetAlbumId = if (currentSelected == albumId) null else albumId
        _uiState.update { state ->
            state.copy(selectedAlbumId = targetAlbumId)
        }
        updateFilteredQuotes()
    }

    private fun updateFilteredQuotes() {
        val state = _uiState.value
        val albumId = state.selectedAlbumId
        if (albumId != null) {
            viewModelScope.launch {
                repository.getQuotesForAlbum(albumId).collect { albumQuotes ->
                    _uiState.update { current ->
                        current.copy(quotes = filterQuotes(albumQuotes, current.searchQuery, current.selectedTagId))
                    }
                }
            }
        } else {
            _uiState.update { current ->
                current.copy(quotes = filterQuotes(allQuotesCache, current.searchQuery, current.selectedTagId))
            }
        }
    }

    fun createAlbum(album: Album) {
        viewModelScope.launch {
            repository.saveAlbum(album)
            showUserMessage("Thought album '${album.name}' created.")
        }
    }

    fun saveQuote(quote: Quote) {
        viewModelScope.launch {
            repository.saveQuote(quote)
            showUserMessage("Quote captured successfully to your sanctuary.")
        }
    }

    fun deleteQuote(quoteId: String) {
        viewModelScope.launch {
            repository.deleteQuote(quoteId)
            showUserMessage("Quote removed from your collection.")
        }
    }

    fun showUserMessage(message: String) {
        _uiState.update { it.copy(userMessage = message) }
    }

    fun clearUserMessage() {
        _uiState.update { it.copy(userMessage = null) }
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
        val sampleTags = DefaultTags.list
        sampleTags.forEach { repository.saveTag(it) }

        val sampleQuotes = listOf(
            Quote(
                id = "sample_1",
                text = "We suffer more often in imagination than in reality.",
                author = "Seneca",
                source = "Letters from a Stoic",
                tagIds = listOf("1", "4"),
                themePresetId = "creators_choice",
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
                tagIds = listOf("3", "5"),
                themePresetId = "editorial_parchment",
                createdAt = 1700000002000L
            ),
            Quote(
                id = "sample_4",
                text = "He who has a why to live can bear almost any how.",
                author = "Friedrich Nietzsche",
                source = "Twilight of the Idols",
                tagIds = listOf("1", "8"),
                themePresetId = "royal_emerald",
                createdAt = 1700000003000L
            ),
            Quote(
                id = "sample_5",
                text = "The wound is the place where the Light enters you.",
                author = "Rumi",
                source = "The Masnavi",
                tagIds = listOf("7", "9"),
                themePresetId = "serene_sanctuary",
                createdAt = 1700000004000L
            ),
            Quote(
                id = "sample_6",
                text = "When we are no longer able to change a situation, we are challenged to change ourselves.",
                author = "Viktor E. Frankl",
                source = "Man's Search for Meaning",
                tagIds = listOf("2", "5"),
                themePresetId = "nordic_twilight",
                createdAt = 1700000005000L
            ),
            Quote(
                id = "sample_7",
                text = "Be yourself; everyone else is already taken.",
                author = "Oscar Wilde",
                source = "Phrases and Philosophies",
                tagIds = listOf("3", "7"),
                themePresetId = "bespoke_espresso",
                createdAt = 1700000006000L
            ),
            Quote(
                id = "sample_8",
                text = "The only way to do great work is to love what you do.",
                author = "Steve Jobs",
                source = "Stanford Commencement",
                tagIds = listOf("6", "10"),
                themePresetId = "crimson_dynasty",
                createdAt = 1700000007000L
            )
        )
        sampleQuotes.forEach { repository.saveQuote(it) }
    }
}
