package com.app.quotely

import com.app.quotely.ui.theme.ThemePreset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ThemePresetTest {

    @Test
    fun test30PresetsRegisteredAndUnique() {
        val presets = ThemePreset.availablePresets

        assertEquals(30, presets.size, "ThemePreset must contain exactly 30 hand-curated editorial presets")

        val uniqueIds = presets.map { it.id }.toSet()
        assertEquals(30, uniqueIds.size, "All 30 ThemePreset IDs must be unique")

        presets.forEach { preset ->
            assertTrue(preset.id.isNotBlank(), "Preset ID must not be blank")
            assertTrue(preset.name.isNotBlank(), "Preset name must not be blank")
            assertTrue(preset.description.isNotBlank(), "Preset description must not be blank")
            assertNotNull(preset.colorScheme)
        }
    }

    @Test
    fun testFromIdResolution() {
        val preset = ThemePreset.fromId("royal_emerald")
        assertEquals("Royal Emerald", preset.name)
        assertEquals("royal_emerald", preset.id)

        val defaultFallback = ThemePreset.fromId("non_existent_preset")
        assertEquals(ThemePreset.CreatorsChoice.id, defaultFallback.id)
    }
}
