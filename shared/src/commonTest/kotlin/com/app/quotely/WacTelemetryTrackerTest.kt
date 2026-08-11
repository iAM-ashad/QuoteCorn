package com.app.quotely

import com.app.quotely.data.telemetry.WacTelemetryTracker
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WacTelemetryTrackerTest {

    @BeforeTest
    fun setUp() {
        WacTelemetryTracker.resetTelemetry()
    }

    @Test
    fun testWacActionLoggingAndThreshold() {
        val now = 1700000000000L

        assertFalse(WacTelemetryTracker.isWeeklyActiveCurator(now))
        assertEquals(0, WacTelemetryTracker.getWeeklyMeaningfulActionCount(now))

        WacTelemetryTracker.logAction(WacTelemetryTracker.ACTION_CAPTURE_OCR, now - 1000)
        WacTelemetryTracker.logAction(WacTelemetryTracker.ACTION_CREATE_ALBUM, now - 2000)

        assertEquals(2, WacTelemetryTracker.getWeeklyMeaningfulActionCount(now))
        assertFalse(WacTelemetryTracker.isWeeklyActiveCurator(now))

        WacTelemetryTracker.logAction(WacTelemetryTracker.ACTION_EXPORT_CAROUSEL, now - 3000)

        assertEquals(3, WacTelemetryTracker.getWeeklyMeaningfulActionCount(now))
        assertTrue(WacTelemetryTracker.isWeeklyActiveCurator(now))
    }

    @Test
    fun testTimestampBucketingExcludesOldActions() {
        val now = 1700000000000L
        val eightDaysAgo = now - (8L * 24L * 60L * 60L * 1000L)

        WacTelemetryTracker.logAction(WacTelemetryTracker.ACTION_CAPTURE_OCR, eightDaysAgo)
        WacTelemetryTracker.logAction(WacTelemetryTracker.ACTION_CREATE_ALBUM, now - 1000)

        assertEquals(1, WacTelemetryTracker.getWeeklyMeaningfulActionCount(now))
        assertFalse(WacTelemetryTracker.isWeeklyActiveCurator(now))
    }
}
