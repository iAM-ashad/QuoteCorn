package com.app.quotely

import com.app.quotely.domain.util.OcrTextParser
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class OcrTextParserTest {

    @Test
    fun testTrimmingQuotationMarks() {
        val input = "“We suffer more often in imagination than in reality.”"
        val result = OcrTextParser.parse(input)

        assertEquals("We suffer more often in imagination than in reality.", result.cleanedQuoteText)
        assertNull(result.suggestedAuthor)
        assertNull(result.suggestedSource)
    }

    @Test
    fun testAuthorAndSourceExtractionWithDash() {
        val input = """
            “We suffer more in imagination than in reality.”
            — Seneca, Letters from a Stoic
        """.trimIndent()

        val result = OcrTextParser.parse(input)

        assertEquals("We suffer more in imagination than in reality.", result.cleanedQuoteText)
        assertEquals("Seneca", result.suggestedAuthor)
        assertEquals("Letters from a Stoic", result.suggestedSource)
    }

    @Test
    fun testAuthorExtractionWithByPrefix() {
        val input = """
            The obstacle is the way.
            by Marcus Aurelius
        """.trimIndent()

        val result = OcrTextParser.parse(input)

        assertEquals("The obstacle is the way.", result.cleanedQuoteText)
        assertEquals("Marcus Aurelius", result.suggestedAuthor)
        assertNull(result.suggestedSource)
    }

    @Test
    fun testRawTextWithoutAuthor() {
        val input = "A mind that is stretched by a new experience can never go back to its old dimensions."
        val result = OcrTextParser.parse(input)

        assertEquals("A mind that is stretched by a new experience can never go back to its old dimensions.", result.cleanedQuoteText)
        assertNull(result.suggestedAuthor)
        assertNull(result.suggestedSource)
    }

    @Test
    fun testEmptyInputHandling() {
        val result = OcrTextParser.parse("   ")
        assertEquals("", result.cleanedQuoteText)
        assertNull(result.suggestedAuthor)
        assertNull(result.suggestedSource)
    }
}
