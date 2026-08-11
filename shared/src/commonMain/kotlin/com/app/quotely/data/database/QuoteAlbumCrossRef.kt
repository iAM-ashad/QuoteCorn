package com.app.quotely.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "quote_album_cross_ref",
    primaryKeys = ["quoteId", "albumId"],
    foreignKeys = [
        ForeignKey(
            entity = QuoteEntity::class,
            parentColumns = ["id"],
            childColumns = ["quoteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AlbumEntity::class,
            parentColumns = ["id"],
            childColumns = ["albumId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["quoteId"]),
        Index(value = ["albumId"])
    ]
)
data class QuoteAlbumCrossRef(
    val quoteId: String,
    val albumId: String
)
