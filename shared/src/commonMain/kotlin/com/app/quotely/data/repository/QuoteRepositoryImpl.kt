package com.app.quotely.data.repository

import com.app.quotely.data.database.QuoteDao
import com.app.quotely.data.database.QuoteEntity
import com.app.quotely.data.database.TagDao
import com.app.quotely.data.database.TagEntity
import com.app.quotely.domain.model.Quote
import com.app.quotely.domain.model.Tag
import com.app.quotely.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository implementation mapping Room entities <-> pure domain models seamlessly.
 */
class QuoteRepositoryImpl(
    private val quoteDao: QuoteDao,
    private val tagDao: TagDao
) : QuoteRepository {

    override fun getQuotes(): Flow<List<Quote>> {
        return quoteDao.getAllQuotes().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getQuoteById(id: String): Quote? {
        return quoteDao.getQuoteById(id)?.toDomainModel()
    }

    override fun searchQuotes(query: String): Flow<List<Quote>> {
        return quoteDao.searchQuotes(query).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getQuotesByTag(tagId: String): Flow<List<Quote>> {
        return quoteDao.getQuotesByTag(tagId).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun saveQuote(quote: Quote): Result<Unit> {
        return runCatching {
            quoteDao.insertQuote(quote.toEntity())
        }
    }

    override suspend fun deleteQuote(id: String): Result<Unit> {
        return runCatching {
            quoteDao.deleteQuoteById(id)
        }
    }

    override fun getTags(): Flow<List<Tag>> {
        return tagDao.getAllTags().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun saveTag(tag: Tag): Result<Unit> {
        return runCatching {
            tagDao.insertTag(tag.toEntity())
        }
    }

    override suspend fun deleteTag(tag: Tag): Result<Unit> {
        return runCatching {
            tagDao.deleteTagById(tag.id)
        }
    }
}

// Mapper extensions between Room entities and Domain models

fun QuoteEntity.toDomainModel(): Quote = Quote(
    id = id,
    text = text,
    author = author,
    source = source,
    tagIds = tagIds,
    themePresetId = themePresetId,
    fontPresetId = fontPresetId,
    createdAt = createdAt
)

fun Quote.toEntity(): QuoteEntity = QuoteEntity(
    id = id,
    text = text,
    author = author,
    source = source,
    tagIds = tagIds,
    themePresetId = themePresetId,
    fontPresetId = fontPresetId,
    createdAt = createdAt
)

fun TagEntity.toDomainModel(): Tag = Tag(
    id = id,
    name = name,
    colorHex = colorHex
)

fun Tag.toEntity(): TagEntity = TagEntity(
    id = id,
    name = name,
    colorHex = colorHex
)
