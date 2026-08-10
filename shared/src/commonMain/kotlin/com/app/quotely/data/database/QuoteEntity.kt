package com.app.quotely.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey val id: String,
    val text: String,
    val author: String,
    val source: String?,
    val tagIds: List<String>,
    val themePresetId: String,
    val fontPresetId: String,
    val createdAt: Long
)
