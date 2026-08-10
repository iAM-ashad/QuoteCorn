package com.app.quotely.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quotely.domain.model.Tag
import com.app.quotely.ui.theme.LocalQuotelyTypography
import com.app.quotely.ui.theme.WarmGold

@Composable
fun TagSelectorRow(
    tags: List<Tag>,
    selectedTagIds: Set<String>,
    onTagToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalQuotelyTypography.current

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "TAGS",
            style = typography.uiLabel,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tags.forEach { tag ->
                val isSelected = selectedTagIds.contains(tag.id)
                val backgroundColor = if (isSelected) WarmGold else Color.Transparent
                val textColor = if (isSelected) Color(0xFF131313) else Color.White
                val borderColor = if (isSelected) WarmGold else Color.White.copy(alpha = 0.3f)

                Box(
                    modifier = Modifier
                        .background(backgroundColor)
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(0.dp)
                        )
                        .clickable { onTagToggle(tag.id) }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = tag.name.uppercase(),
                        style = typography.uiLabel.copy(fontSize = 11.sp),
                        color = textColor
                    )
                }
            }
        }
    }
}
