package com.app.quotely.data.repository

import com.app.quotely.data.database.AlbumDao
import com.app.quotely.data.database.AlbumEntity
import com.app.quotely.data.database.QuoteAlbumCrossRef
import com.app.quotely.data.database.QuoteDao
import com.app.quotely.data.database.QuoteEntity
import com.app.quotely.data.database.TagDao
import com.app.quotely.data.database.TagEntity
import com.app.quotely.domain.model.Album
import com.app.quotely.domain.model.Quote
import com.app.quotely.domain.model.Tag
import com.app.quotely.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

/**
 * Repository implementation mapping Room entities <-> pure domain models seamlessly.
 */
class QuoteRepositoryImpl(
    private val quoteDao: QuoteDao,
    private val tagDao: TagDao,
    private val albumDao: AlbumDao? = null
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

    override fun getAlbums(): Flow<List<Album>> {
        return albumDao?.getAllAlbums()?.map { entities ->
            entities.map { it.toDomainModel() }
        } ?: emptyFlow()
    }

    override fun getQuotesForAlbum(albumId: String): Flow<List<Quote>> {
        return albumDao?.getQuotesForAlbum(albumId)?.map { entities ->
            entities.map { it.toDomainModel() }
        } ?: emptyFlow()
    }

    override suspend fun saveAlbum(album: Album): Result<Unit> {
        return runCatching {
            albumDao?.insertAlbum(album.toEntity())
        }
    }

    override suspend fun addQuoteToAlbum(quoteId: String, albumId: String): Result<Unit> {
        return runCatching {
            albumDao?.addQuoteToAlbum(QuoteAlbumCrossRef(quoteId = quoteId, albumId = albumId))
        }
    }

    override suspend fun removeQuoteFromAlbum(quoteId: String, albumId: String): Result<Unit> {
        return runCatching {
            albumDao?.removeQuoteFromAlbum(quoteId = quoteId, albumId = albumId)
        }
    }

    override suspend fun seedStarterAlbumsIfEmpty(): Result<Unit> {
        return runCatching {
            albumDao?.let { dao ->
                if (dao.getAlbumCount() == 0) {
                    val now = 1700000000000L
                    val starterAlbums = listOf(
                        AlbumEntity(
                            id = "album_ideas_changed_me",
                            name = "Ideas That Changed Me",
                            description = "Key insights and life-altering perspectives",
                            coverThemeId = "creators_choice",
                            createdAt = now,
                            updatedAt = now
                        ),
                        AlbumEntity(
                            id = "album_stoic_models",
                            name = "Stoic Mental Models",
                            description = "Ancient wisdom for modern resilience",
                            coverThemeId = "aurelian_monolith",
                            createdAt = now,
                            updatedAt = now
                        ),
                        AlbumEntity(
                            id = "album_cinematic_dialogue",
                            name = "Cinematic Dialogue",
                            description = "Unforgettable quotes from film & stage",
                            coverThemeId = "midnight_obsidian",
                            createdAt = now,
                            updatedAt = now
                        ),
                        AlbumEntity(
                            id = "album_ambition_leadership",
                            name = "Ambition & Leadership",
                            description = "Principles for building and leading",
                            coverThemeId = "royal_emerald",
                            createdAt = now,
                            updatedAt = now
                        )
                    )
                    dao.insertAlbums(starterAlbums)
                }
            }
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

fun AlbumEntity.toDomainModel(): Album = Album(
    id = id,
    name = name,
    description = description,
    coverThemeId = coverThemeId,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Album.toEntity(): AlbumEntity = AlbumEntity(
    id = id,
    name = name,
    description = description,
    coverThemeId = coverThemeId,
    createdAt = createdAt,
    updatedAt = updatedAt
)
