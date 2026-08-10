package com.app.quotely.domain.model

/**
 * Single source of truth for meaningful and diverse default tags for quote enthusiasts.
 */
object DefaultTags {
    val list: List<Tag> = listOf(
        Tag("1", "Philosophy", "#D4AF37"),
        Tag("2", "Mindfulness", "#BFCDFF"),
        Tag("3", "Literature", "#C6C6C7"),
        Tag("4", "Stoicism", "#E5E2E1"),
        Tag("5", "Wisdom & Life", "#F2CA50"),
        Tag("6", "Motivation", "#FFB4AB"),
        Tag("7", "Poetry & Art", "#E2C46C"),
        Tag("8", "Existentialism", "#A9C7FF"),
        Tag("9", "Love & Soul", "#F7B5A0"),
        Tag("10", "Focus & Growth", "#8ED7A8")
    )
}
