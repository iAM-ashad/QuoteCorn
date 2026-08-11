package com.app.quotely.domain.repository

import com.app.quotely.domain.model.Album
import com.app.quotely.domain.model.Quote
import com.app.quotely.domain.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Domain repository interface for Quote, Tag, and Album operations.
 */
interface QuoteRepository {
    fun getQuotes(): Flow<List<Quote>>
    suspend fun getQuoteById(id: String): Quote?
    fun searchQuotes(query: String): Flow<List<Quote>>
    fun getQuotesByTag(tagId: String): Flow<List<Quote>>
    suspend fun saveQuote(quote: Quote): Result<Unit>
    suspend fun deleteQuote(id: String): Result<Unit>

    fun getTags(): Flow<List<Tag>>
    suspend fun saveTag(tag: Tag): Result<Unit>
    suspend fun deleteTag(tag: Tag): Result<Unit>

    fun getAlbums(): Flow<List<Album>> = emptyFlow()
    fun getQuotesForAlbum(albumId: String): Flow<List<Quote>> = emptyFlow()
    suspend fun saveAlbum(album: Album): Result<Unit> = Result.success(Unit)
    suspend fun addQuoteToAlbum(quoteId: String, albumId: String): Result<Unit> = Result.success(Unit)
    suspend fun removeQuoteFromAlbum(quoteId: String, albumId: String): Result<Unit> = Result.success(Unit)
    suspend fun seedStarterAlbumsIfEmpty(): Result<Unit> = Result.success(Unit)
}
