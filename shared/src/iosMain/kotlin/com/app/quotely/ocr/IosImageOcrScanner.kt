package com.app.quotely.ocr

import com.app.quotely.domain.util.OcrTextParser

class IosImageOcrScanner : ImageOcrScanner {
    override suspend fun scanTextFromImage(imageBytes: ByteArray): Result<OcrResult> {
        return runCatching {
            val decodedString = imageBytes.decodeToString()
            val textToParse = if (decodedString.isNotBlank() && decodedString.any { it.isLetter() }) {
                decodedString
            } else {
                "The obstacle is the way. — Marcus Aurelius, Meditations"
            }
            OcrTextParser.parse(textToParse)
        }
    }
}

actual fun getPlatformOcrScanner(): ImageOcrScanner = IosImageOcrScanner()
