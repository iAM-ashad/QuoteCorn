package com.app.quotely

import com.app.quotely.data.database.AlbumEntity
import com.app.quotely.data.database.QuoteAlbumCrossRef
import com.app.quotely.domain.model.Album
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AlbumDaoTest {

    @Test
    fun testAlbumEntityMapping() {
        val entity = AlbumEntity(
            id = "album_1",
            name = "Stoic Mental Models",
            description = "Ancient wisdom for modern resilience",
            coverThemeId = "aurelian_monolith",
            createdAt = 1700000000000L,
            updatedAt = 1700000000000L
        )

        assertEquals("album_1", entity.id)
        assertEquals("Stoic Mental Models", entity.name)
        assertEquals("Ancient wisdom for modern resilience", entity.description)
        assertEquals("aurelian_monolith", entity.coverThemeId)
    }

    @Test
    fun testQuoteAlbumCrossRefMapping() {
        val crossRef = QuoteAlbumCrossRef(
            quoteId = "quote_101",
            albumId = "album_ideas_changed_me"
        )

        assertEquals("quote_101", crossRef.quoteId)
        assertEquals("album_ideas_changed_me", crossRef.albumId)
    }

    @Test
    fun testRepositoryAlbumOperations() = runBlocking {
        val repo = InMemoryQuoteRepository()

        val albums = repo.getAlbums().first()
        assertTrue(albums.isNotEmpty())
        assertEquals(4, albums.size)

        val firstAlbum = albums.find { it.id == "album_ideas_changed_me" }
        assertNotNull(firstAlbum)
        assertEquals("Ideas That Changed Me", firstAlbum.name)

        val newAlbum = Album(
            id = "album_custom",
            name = "My Custom Album",
            description = "Personal collection",
            coverThemeId = "royal_emerald",
            createdAt = 1700000000000L,
            updatedAt = 1700000000000L
        )

        val saveResult = repo.saveAlbum(newAlbum)
        assertTrue(saveResult.isSuccess)

        val updatedAlbums = repo.getAlbums().first()
        assertEquals(5, updatedAlbums.size)
        assertTrue(updatedAlbums.any { it.id == "album_custom" })
    }
}
