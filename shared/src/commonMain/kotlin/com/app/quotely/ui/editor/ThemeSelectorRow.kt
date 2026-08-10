package com.app.quotely.ui.editor

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
fun ThemeSelectorRow(
    selectedThemePresetId: String,
    onThemeSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalQuotelyTypography.current

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "THEME PRESET",
            style = typography.uiLabel,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ThemePreset.availablePresets.forEach { preset ->
                val isSelected = selectedThemePresetId == preset.id
                val activeBorderColor = if (isSelected) WarmGold else Color.White.copy(alpha = 0.2f)
                val borderWidth = if (isSelected) 2.dp else 1.dp

                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .background(preset.backgroundColor)
                        .border(
                            width = borderWidth,
                            color = activeBorderColor,
                            shape = RoundedCornerShape(0.dp)
                        )
                        .clickable { onThemeSelect(preset.id) }
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = preset.name,
                                style = typography.uiButton.copy(fontSize = 11.sp),
                                color = preset.textColor,
                                maxLines = 1
                            )
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(preset.primaryAccent)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Aa",
                            style = typography.quoteBody.copy(fontSize = 18.sp),
                            color = preset.textColor,
                            fontFamily = preset.quoteFontFamily
                        )
                    }
                }
            }
        }
    }
}
