package com.app.quotely.domain.model

data class Album(
    val id: String,
    val name: String,
    val description: String? = null,
    val coverThemeId: String = "creators_choice",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)
