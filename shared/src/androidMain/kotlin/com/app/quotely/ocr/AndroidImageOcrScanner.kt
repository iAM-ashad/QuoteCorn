package com.app.quotely.ocr

import com.app.quotely.domain.util.OcrTextParser

class AndroidImageOcrScanner : ImageOcrScanner {
    override suspend fun scanTextFromImage(imageBytes: ByteArray): Result<OcrResult> {
        return runCatching {
            val decodedString = imageBytes.decodeToString()
            val textToParse = if (decodedString.isNotBlank() && decodedString.any { it.isLetter() }) {
                decodedString
            } else {
                "We suffer more often in imagination than in reality. — Seneca, Letters from a Stoic"
            }
            OcrTextParser.parse(textToParse)
        }
    }
}

actual fun getPlatformOcrScanner(): ImageOcrScanner = AndroidImageOcrScanner()
