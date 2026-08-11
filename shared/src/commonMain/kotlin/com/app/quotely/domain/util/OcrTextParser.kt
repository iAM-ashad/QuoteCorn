package com.app.quotely.domain.util

import com.app.quotely.ocr.OcrResult

object OcrTextParser {

    fun isValidQuoteText(rawText: String): Boolean {
        val trimmed = rawText.trim()
        if (trimmed.length < 10) return false
        val letterCount = trimmed.count { it.isLetter() }
        val letterRatio = letterCount.toDouble() / trimmed.length.toDouble()
        return letterCount >= 8 && letterRatio >= 0.35
    }

    fun parse(rawText: String): OcrResult {
        if (rawText.isBlank()) {
            return OcrResult(
                rawText = "",
                cleanedQuoteText = "",
                suggestedAuthor = null,
                suggestedSource = null
            )
        }

        val lines = rawText.lines().map { it.trim() }.filter { it.isNotEmpty() }
        var authorCandidate: String? = null
        var sourceCandidate: String? = null
        val bodyLines = mutableListOf<String>()

        val authorPrefixes = listOf("—", "–", "-", "by ", "By ")

        for (line in lines) {
            val matchingPrefix = authorPrefixes.find { line.startsWith(it) }
            if (matchingPrefix != null) {
                val attributionContent = line.substring(matchingPrefix.length).trim()
                if (attributionContent.contains(",")) {
                    val parts = attributionContent.split(",", limit = 2).map { it.trim() }
                    authorCandidate = parts.getOrNull(0)?.ifBlank { null }
                    sourceCandidate = parts.getOrNull(1)?.ifBlank { null }
                } else {
                    authorCandidate = attributionContent.ifBlank { null }
                }
            } else {
                bodyLines.add(line)
            }
        }

        var cleanedBody = bodyLines.joinToString(" ")
        cleanedBody = cleanedBody.trim('"', '“', '”', '\'', '‘', '’').trim()

        return OcrResult(
            rawText = rawText,
            cleanedQuoteText = cleanedBody,
            suggestedAuthor = authorCandidate,
            suggestedSource = sourceCandidate
        )
    }
}
