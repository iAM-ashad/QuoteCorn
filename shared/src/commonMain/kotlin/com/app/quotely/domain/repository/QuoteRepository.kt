package com.app.quotely.domain.repository

import com.app.quotely.domain.model.Quote
import com.app.quotely.domain.model.Tag
import kotlinx.coroutines.flow.Flow

/**
 * Domain repository interface for Quote and Tag operations.
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
}
