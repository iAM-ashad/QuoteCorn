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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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

/**
 * Editorial Bottom Sheet Panel for Quote Focus View.
 * Contains drag handle, theme preset selector carousel, PNG export trigger, and delete action.
 */
@Composable
fun DetailControlToolbar(
    activeThemePreset: ThemePreset,
    isExporting: Boolean,
    isDeleting: Boolean,
    onThemeSelect: (String) -> Unit,
    onExportClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismissPanel: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val typography = LocalQuotelyTypography.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF131313).copy(alpha = 0.98f))
            .border(
                width = 1.dp,
                color = WarmGold.copy(alpha = 0.3f),
                shape = RoundedCornerShape(0.dp)
            )
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Drag Handle Pill Bar
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(4.dp)
                .background(Color.White.copy(alpha = 0.3f), shape = RoundedCornerShape(2.dp))
                .clickable { onDismissPanel?.invoke() }
        )

        // Bottom Sheet Title & Active Theme Badge Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "QUOTE OPTIONS",
                style = typography.uiButton.copy(fontSize = 12.sp, letterSpacing = 0.1.sp),
                color = Color.White
            )
            Text(
                text = activeThemePreset.name.uppercase(),
                style = typography.uiLabel.copy(fontSize = 10.sp),
                color = WarmGold
            )
        }

        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))

        // Theme Presets Carousel
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "SELECT VISUAL THEME",
                style = typography.uiLabel.copy(fontSize = 10.sp, letterSpacing = 0.1.sp),
                color = Color.White.copy(alpha = 0.6f)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ThemePreset.availablePresets.forEach { preset ->
                    val isSelected = activeThemePreset.id == preset.id
                    val border = if (isSelected) WarmGold else Color.White.copy(alpha = 0.2f)

                    Box(
                        modifier = Modifier
                            .background(preset.backgroundColor)
                            .border(width = if (isSelected) 2.dp else 1.dp, color = border, shape = RoundedCornerShape(0.dp))
                            .clickable { onThemeSelect(preset.id) }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(preset.primaryAccent)
                            )
                            Text(
                                text = preset.name.uppercase(),
                                style = typography.uiLabel.copy(fontSize = 10.sp),
                                color = preset.textColor
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Action Buttons Row (DELETE & EXPORT PNG)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Delete Quote Button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .border(width = 1.dp, color = Color(0xFFFFB4AB).copy(alpha = 0.6f), shape = RoundedCornerShape(0.dp))
                    .clickable(enabled = !isDeleting) { onDeleteClick() }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isDeleting) "DELETING..." else "DELETE QUOTE",
                    style = typography.uiButton.copy(fontSize = 11.sp),
                    color = Color(0xFFFFB4AB)
                )
            }

            // Export Image Button
            Button(
                onClick = onExportClick,
                enabled = !isExporting,
                colors = ButtonDefaults.buttonColors(
                    containerColor = WarmGold,
                    contentColor = Color(0xFF3C2F00)
                ),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.weight(1.5f)
            ) {
                if (isExporting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
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
