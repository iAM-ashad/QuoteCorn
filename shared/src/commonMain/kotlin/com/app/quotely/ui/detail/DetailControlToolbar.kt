package com.app.quotely.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quotely.ui.theme.LocalQuotelyTypography
import com.app.quotely.ui.theme.ThemePreset
import com.app.quotely.ui.theme.WarmGold

@Composable
fun DetailControlToolbar(
    activeThemePreset: ThemePreset,
    isExporting: Boolean,
    isDeleting: Boolean,
    onThemeSelect: (String) -> Unit,
    onExportClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalQuotelyTypography.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF131313).copy(alpha = 0.95f))
            .border(width = 1.dp, color = Color.White.copy(alpha = 0.1f), shape = RoundedCornerShape(0.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Theme Swatches Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "THEME:",
                style = typography.uiLabel.copy(fontSize = 10.sp),
                color = Color.White.copy(alpha = 0.6f)
            )

            ThemePreset.availablePresets.forEach { preset ->
                val isSelected = activeThemePreset.id == preset.id
                val border = if (isSelected) WarmGold else Color.White.copy(alpha = 0.2f)

                Box(
                    modifier = Modifier
                        .background(preset.backgroundColor)
                        .border(width = if (isSelected) 2.dp else 1.dp, color = border, shape = RoundedCornerShape(0.dp))
                        .clickable { onThemeSelect(preset.id) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(preset.primaryAccent)
                        )
                        Text(
                            text = preset.name.uppercase(),
                            style = typography.uiLabel.copy(fontSize = 9.sp),
                            color = preset.textColor
                        )
                    }
                }
            }
        }

        // Action Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = Color(0xFFFFB4AB).copy(alpha = 0.5f), shape = RoundedCornerShape(0.dp))
                    .clickable(enabled = !isDeleting) { onDeleteClick() }
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Text(
                    text = if (isDeleting) "DELETING..." else "DELETE",
                    style = typography.uiButton.copy(fontSize = 11.sp),
                    color = Color(0xFFFFB4AB)
                )
            }

            Button(
                onClick = onExportClick,
                enabled = !isExporting,
                colors = ButtonDefaults.buttonColors(
                    containerColor = WarmGold,
                    contentColor = Color(0xFF3C2F00)
                ),
                shape = RoundedCornerShape(0.dp)
            ) {
                if (isExporting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(14.dp),
                        color = Color(0xFF3C2F00),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "EXPORT PNG IMAGE",
                        style = typography.uiButton.copy(fontSize = 11.sp)
                    )
                }
            }
        }
    }
}
