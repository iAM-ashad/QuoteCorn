package com.app.quotely

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun QuotelyBackHandler(enabled: Boolean, onBack: () -> Unit) {
    BackHandler(enabled = enabled, onBack = onBack)
}
