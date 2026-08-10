package com.app.quotely.ui.editor

import com.app.quotely.domain.model.Tag
import com.app.quotely.ui.theme.ThemePreset

/**
 * Immutable UI State for the Quote Creation & Live Preview Editor.
 */
data class CreateQuoteUiState(
    val quoteText: String = "",
    val authorText: String = "",
    val sourceText: String = "",
    val selectedTagIds: Set<String> = emptySet(),
    val selectedThemePresetId: String = ThemePreset.AurelianMonolith.id,
    val availableTags: List<Tag> = emptyList(),
    val isSaving: Boolean = false,
    val isSaveSuccess: Boolean = false,
    val errorMessage: String? = null
) {
    val activeThemePreset: ThemePreset
        get() = ThemePreset.availablePresets.find { it.id == selectedThemePresetId }
            ?: ThemePreset.Default
}
