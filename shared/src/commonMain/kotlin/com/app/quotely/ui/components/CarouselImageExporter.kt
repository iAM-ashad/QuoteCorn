package com.app.quotely.ui.components

import com.app.quotely.domain.model.Album
import com.app.quotely.domain.model.Quote

data class CarouselSlide(
    val slideNumber: Int,
    val totalSlides: Int,
    val indexString: String,
    val quote: Quote,
    val watermark: String = "── CURATED WITH QUOTECORN ──"
)

object CarouselImageExporter {

    fun generateCarouselSlides(
        album: Album,
        quotes: List<Quote>,
        maxSlides: Int = 5
    ): List<CarouselSlide> {
        com.app.quotely.data.telemetry.WacTelemetryTracker.logAction(com.app.quotely.data.telemetry.WacTelemetryTracker.ACTION_EXPORT_CAROUSEL)
        val selectedQuotes = quotes.take(maxSlides)
        val total = selectedQuotes.size

        return selectedQuotes.mapIndexed { index, quote ->
            val slideNum = index + 1
            val formattedSlide = slideNum.toString().padStart(2, '0')
            val formattedTotal = total.toString().padStart(2, '0')
            CarouselSlide(
                slideNumber = slideNum,
                totalSlides = total,
                indexString = "$formattedSlide / $formattedTotal",
                quote = quote,
                watermark = "── CURATED WITH QUOTECORN ──"
            )
        }
    }
}
