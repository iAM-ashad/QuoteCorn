package com.app.quotely.domain.model

/**
 * Pure domain model representing a thought-provoking quote.
 */
data class Quote(
    val id: String,
    val text: String,
    val author: String,
    val source: String? = null,
    val tagIds: List<String> = emptyList(),
    val themePresetId: String = "aurelian_monolith",
    val fontPresetId: String = "playfair_display",
    val createdAt: Long = 0L
)
