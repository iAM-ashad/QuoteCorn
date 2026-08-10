package com.app.quotely.domain.model

/**
 * Pure domain model representing a category or topic tag.
 */
data class Tag(
    val id: String,
    val name: String,
    val colorHex: String
)
