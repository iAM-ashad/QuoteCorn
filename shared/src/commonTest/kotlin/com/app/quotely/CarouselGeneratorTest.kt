package com.app.quotely

import com.app.quotely.domain.model.Album
import com.app.quotely.domain.model.Quote
import com.app.quotely.ui.components.CarouselImageExporter
import com.app.quotely.ui.theme.ThemePreset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CarouselGeneratorTest {

    @Test
    fun testCarouselSlideFormatting() {
        val album = Album("album_1", "Stoic Mental Models")
        val quotes = listOf(
            Quote("q1", "Quote 1", "Seneca", createdAt = 100L),
            Quote("q2", "Quote 2", "Marcus Aurelius", createdAt = 200L),
            Quote("q3", "Quote 3", "Epictetus", createdAt = 300L)
        )

        val slides = CarouselImageExporter.generateCarouselSlides(album, quotes, maxSlides = 5)

        assertEquals(3, slides.size)
        assertEquals("01 / 03", slides[0].indexString)
        assertEquals("02 / 03", slides[1].indexString)
        assertEquals("03 / 03", slides[2].indexString)
        assertEquals("── CURATED WITH QUOTECORN ──", slides[0].watermark)
    }

    @Test
    fun testCarouselSlideCappingMax5() {
        val album = Album("album_2", "Ideas That Changed Me")
        val quotes = (1..10).map { i ->
            Quote("q$i", "Quote $i", "Author $i", createdAt = 1000L * i)
        }

        val slides = CarouselImageExporter.generateCarouselSlides(album, quotes, maxSlides = 5)

        assertEquals(5, slides.size)
        assertEquals("01 / 05", slides[0].indexString)
        assertEquals("05 / 05", slides[4].indexString)
    }

    @Test
    fun testThemePresetResolutionForAlbumCard() {
        val preset = ThemePreset.fromId("creators_choice")
        assertEquals("Creator's Choice", preset.name)
        assertTrue(preset.id == "creators_choice")
    }
}
