package com.app.quotely

import androidx.compose.runtime.Composable

/**
 * Multiplatform BackHandler interface for handling device system back gestures and back buttons.
 */
@Composable
expect fun QuotelyBackHandler(enabled: Boolean = true, onBack: () -> Unit)
