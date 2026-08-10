package com.app.quotely.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quotely.ui.theme.LocalQuotelyTypography
import com.app.quotely.ui.theme.WarmGold

/**
 * Editorial Monolith Snackbar aligning 100% with QuoteCorn brand design system.
 */
@Composable
fun QuotelySnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier
) {
    val typography = LocalQuotelyTypography.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFF131313))
            .border(width = 1.dp, color = WarmGold, shape = RoundedCornerShape(0.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = snackbarData.visuals.message,
                style = typography.uiLabel.copy(fontSize = 12.sp),
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            snackbarData.visuals.actionLabel?.let { action ->
                TextButton(onClick = { snackbarData.performAction() }) {
                    Text(
                        text = action.uppercase(),
                        style = typography.uiButton.copy(fontSize = 12.sp),
                        color = WarmGold
                    )
                }
            }
        }
    }
}
