package com.app.quotely.ui.gallery

import com.app.quotely.domain.model.Album
import com.app.quotely.domain.model.Quote
import com.app.quotely.domain.model.Tag

/**
 * Immutable UI State for the Masonry Gallery Feed.
 */
data class GalleryUiState(
    val quotes: List<Quote> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val albums: List<Album> = emptyList(),
    val selectedTagId: String? = null,
    val selectedAlbumId: String? = null,
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val selectedQuoteForFocus: Quote? = null,
    val userMessage: String? = null
)
