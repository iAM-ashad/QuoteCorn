package com.app.quotely

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.app.quotely.domain.model.Quote
import com.app.quotely.domain.model.Tag
import com.app.quotely.domain.repository.QuoteRepository
import com.app.quotely.ui.detail.QuoteDetailScreen
import com.app.quotely.ui.detail.QuoteDetailViewModel
import com.app.quotely.ui.editor.CreateQuoteScreen
import com.app.quotely.ui.editor.CreateQuoteViewModel
import com.app.quotely.ui.gallery.GalleryScreen
import com.app.quotely.ui.gallery.GalleryViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed interface Screen {
    data object Gallery : Screen
    data object CreateQuote : Screen
    data class QuoteDetail(val quote: Quote) : Screen
}

/**
 * In-memory repository for KMP preview & target runtime demonstration when Room DB driver is uninitialized.
 */
class InMemoryQuoteRepository : QuoteRepository {
    private val quotesFlow = MutableStateFlow<List<Quote>>(emptyList())
    private val tagsFlow = MutableStateFlow<List<Tag>>(emptyList())

    override fun getQuotes(): Flow<List<Quote>> = quotesFlow.asStateFlow()

    override suspend fun getQuoteById(id: String): Quote? = quotesFlow.value.find { it.id == id }

    override fun searchQuotes(query: String): Flow<List<Quote>> = quotesFlow.asStateFlow()

    override fun getQuotesByTag(tagId: String): Flow<List<Quote>> = quotesFlow.asStateFlow()

    override suspend fun saveQuote(quote: Quote): Result<Unit> {
        quotesFlow.update { current ->
            val index = current.indexOfFirst { it.id == quote.id }
            if (index >= 0) {
                current.toMutableList().also { it[index] = quote }
            } else {
                listOf(quote) + current
            }
        }
        return Result.success(Unit)
    }

    override suspend fun deleteQuote(id: String): Result<Unit> {
        quotesFlow.update { current -> current.filterNot { it.id == id } }
        return Result.success(Unit)
    }

    override fun getTags(): Flow<List<Tag>> = tagsFlow.asStateFlow()

    override suspend fun saveTag(tag: Tag): Result<Unit> {
        tagsFlow.update { current ->
            if (current.any { it.id == tag.id }) current else current + tag
        }
        return Result.success(Unit)
    }

    override suspend fun deleteTag(tag: Tag): Result<Unit> {
        tagsFlow.update { current -> current.filterNot { it.id == tag.id } }
        return Result.success(Unit)
    }
}

/**
 * Top-level Quotely Compose Entrypoint managing navigation between screens.
 */
@Composable
fun QuotelyApp(
    repository: QuoteRepository = remember { InMemoryQuoteRepository() }
) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Gallery) }
    val galleryViewModel = remember(repository) { GalleryViewModel(repository) }
    val createQuoteViewModel = remember(repository) { CreateQuoteViewModel(repository) }
    val quoteDetailViewModel = remember(repository) { QuoteDetailViewModel(repository) }

    val galleryState by galleryViewModel.uiState.collectAsState()
    val createQuoteState by createQuoteViewModel.uiState.collectAsState()
    val detailState by quoteDetailViewModel.uiState.collectAsState()

    when (val screen = currentScreen) {
        Screen.Gallery -> {
            GalleryScreen(
                uiState = galleryState,
                onSearchQueryChange = galleryViewModel::onSearchQueryChange,
                onTagSelect = galleryViewModel::onTagSelect,
                onQuoteClick = { selectedQuote ->
                    quoteDetailViewModel.setQuote(selectedQuote)
                    currentScreen = Screen.QuoteDetail(selectedQuote)
                },
                onDeleteQuote = galleryViewModel::deleteQuote,
                onCreateQuoteClick = { currentScreen = Screen.CreateQuote },
                onClearUserMessage = galleryViewModel::clearUserMessage
            )
        }

        Screen.CreateQuote -> {
            QuotelyBackHandler(enabled = true) {
                currentScreen = Screen.Gallery
            }
            CreateQuoteScreen(
                uiState = createQuoteState,
                onQuoteTextChange = createQuoteViewModel::onQuoteTextChange,
                onAuthorTextChange = createQuoteViewModel::onAuthorTextChange,
                onSourceTextChange = createQuoteViewModel::onSourceTextChange,
                onTagToggle = createQuoteViewModel::toggleTagSelection,
                onThemeSelect = createQuoteViewModel::onThemePresetSelect,
                onSaveClick = {
                    createQuoteViewModel.saveQuote()
                    galleryViewModel.showUserMessage("Quote captured successfully to your sanctuary.")
                    currentScreen = Screen.Gallery
                },
                onBackClick = { currentScreen = Screen.Gallery }
            )
        }

        is Screen.QuoteDetail -> {
            QuotelyBackHandler(enabled = true) {
                currentScreen = Screen.Gallery
            }
            QuoteDetailScreen(
                uiState = detailState,
                onToggleControls = quoteDetailViewModel::toggleControlsVisibility,
                onThemeSelect = quoteDetailViewModel::onThemePresetSelect,
                onExportClick = quoteDetailViewModel::openExportModal,
                onSelectAspectRatio = quoteDetailViewModel::onSelectAspectRatio,
                onSaveImageClick = quoteDetailViewModel::saveExportedImage,
                onDeleteClick = {
                    quoteDetailViewModel.deleteQuote {
                        currentScreen = Screen.Gallery
                    }
                },
                onBackClick = { currentScreen = Screen.Gallery },
                onClearExportMessage = quoteDetailViewModel::clearExportMessage,
                onDismissExportModal = quoteDetailViewModel::dismissExportModal
            )
        }
    }
}
