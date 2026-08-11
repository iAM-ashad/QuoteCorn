package com.app.quotely.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums ORDER BY createdAt DESC")
    fun getAllAlbums(): Flow<List<AlbumEntity>>

    @Query("SELECT * FROM albums WHERE id = :id")
    suspend fun getAlbumById(id: String): AlbumEntity?

    @Query("SELECT quotes.* FROM quotes INNER JOIN quote_album_cross_ref ON quotes.id = quote_album_cross_ref.quoteId WHERE quote_album_cross_ref.albumId = :albumId ORDER BY quotes.createdAt DESC")
    fun getQuotesForAlbum(albumId: String): Flow<List<QuoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: AlbumEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuoteToAlbum(crossRef: QuoteAlbumCrossRef)

    @Query("DELETE FROM quote_album_cross_ref WHERE quoteId = :quoteId AND albumId = :albumId")
    suspend fun removeQuoteFromAlbum(quoteId: String, albumId: String)

    @Query("SELECT COUNT(*) FROM albums")
    suspend fun getAlbumCount(): Int
}
