package com.app.quotely.ocr

data class OcrResult(
    val rawText: String,
    val cleanedQuoteText: String,
    val suggestedAuthor: String?,
    val suggestedSource: String?
)

interface ImageOcrScanner {
    suspend fun scanTextFromImage(imageBytes: ByteArray): Result<OcrResult>
}

expect fun getPlatformOcrScanner(): ImageOcrScanner
