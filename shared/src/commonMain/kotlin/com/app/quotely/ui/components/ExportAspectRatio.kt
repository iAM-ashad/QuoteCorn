package com.app.quotely.ui.components

/**
 * Aspect ratio format presets for multi-platform image export.
 */
enum class ExportAspectRatio(
    val label: String,
    val description: String,
    val width: Int,
    val height: Int
) {
    SQUARE_1_1("1:1", "Square Post (1080×1080)", 1080, 1080),
    STORY_9_16("9:16", "Story / Wallpaper (1080×1920)", 1080, 1920),
    PORTRAIT_3_4("3:4", "Portrait Post (1080×1440)", 1080, 1440),
    LANDSCAPE_16_9("16:9", "Landscape (1920×1080)", 1920, 1080)
}
