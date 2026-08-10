package com.app.quotely.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quotely.ui.theme.LocalQuotelyTypography
import com.app.quotely.ui.theme.WarmGold

/**
 * Editorial Monolith Delete Confirmation Dialog aligning with QuoteCorn brand design system.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteConfirmationDialog(
    onConfirmDelete: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalQuotelyTypography.current

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF131313))
                .border(width = 1.dp, color = WarmGold, shape = RoundedCornerShape(0.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "“ ”",
                style = typography.quoteDisplayMobile.copy(fontSize = 32.sp),
                color = Color(0xFFFFB4AB)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "DELETE QUOTE",
                style = typography.quoteDisplayMobile.copy(fontSize = 20.sp),
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Are you sure you want to remove this quote from your sanctuary? This action cannot be undone.",
                style = typography.uiLabel.copy(fontSize = 12.sp, lineHeight = 18.sp),
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = WarmGold.copy(alpha = 0.2f))

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Cancel Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .border(width = 1.dp, color = Color.White.copy(alpha = 0.3f), shape = RoundedCornerShape(0.dp))
                        .clickable { onDismiss() }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "CANCEL",
                        style = typography.uiButton.copy(fontSize = 11.sp),
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                // Confirm Delete Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFFB3261E))
                        .border(width = 1.dp, color = Color(0xFFFFB4AB), shape = RoundedCornerShape(0.dp))
                        .clickable {
                            onConfirmDelete()
                            onDismiss()
                        }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "DELETE",
                        style = typography.uiButton.copy(fontSize = 11.sp),
                        color = Color.White
                    )
                }
            }
        }
    }
}
