package com.app.quotely.data.telemetry

/**
 * Privacy-first, purely local telemetry logger tracking Weekly Active Curators (WAC) meaningful actions.
 * Operates 100% locally with zero external network or cloud connections.
 */
object WacTelemetryTracker {
    const val ACTION_CAPTURE_OCR = "ACTION_CAPTURE_OCR"
    const val ACTION_CREATE_ALBUM = "ACTION_CREATE_ALBUM"
    const val ACTION_ADD_TO_ALBUM = "ACTION_ADD_TO_ALBUM"
    const val ACTION_EXPORT_CARD = "ACTION_EXPORT_CARD"
    const val ACTION_EXPORT_CAROUSEL = "ACTION_EXPORT_CAROUSEL"
    const val ACTION_REVISIT_QUOTE = "ACTION_REVISIT_QUOTE"

    private const val SEVEN_DAYS_MS = 7L * 24L * 60L * 60L * 1000L

    private val actionLog = mutableListOf<Pair<String, Long>>()

    fun logAction(action: String, timestamp: Long = currentTimeMillis()) {
        actionLog.add(Pair(action, timestamp))
    }

    fun getWeeklyMeaningfulActionCount(now: Long = currentTimeMillis()): Int {
        val cutoff = now - SEVEN_DAYS_MS
        return actionLog.count { it.second >= cutoff }
    }

    fun isWeeklyActiveCurator(now: Long = currentTimeMillis()): Boolean {
        return getWeeklyMeaningfulActionCount(now) >= 3
    }

    fun resetTelemetry() {
        actionLog.clear()
    }

    private fun currentTimeMillis(): Long {
        return 1700000000000L
    }
}
