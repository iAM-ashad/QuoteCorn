package com.app.quotely.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String? = null,
    val coverThemeId: String = "creators_choice",
    val createdAt: Long,
    val updatedAt: Long
)
