package com.app.quotely.ui.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
fun TagFilterRow(
    tags: List<Tag>,
    selectedTagId: String?,
    onTagSelect: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalQuotelyTypography.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // "ALL" Chip
        val isAllSelected = selectedTagId == null
        val allBg = if (isAllSelected) WarmGold else Color.Transparent
        val allText = if (isAllSelected) Color(0xFF131313) else Color.White
        val allBorder = if (isAllSelected) WarmGold else Color.White.copy(alpha = 0.2f)

        Box(
            modifier = Modifier
                .background(allBg)
                .border(width = 1.dp, color = allBorder, shape = RoundedCornerShape(0.dp))
                .clickable { onTagSelect(null) }
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = "ALL",
                style = typography.uiLabel.copy(fontSize = 11.sp),
                color = allText
            )
        }

        tags.forEach { tag ->
            val isSelected = selectedTagId == tag.id
            val bg = if (isSelected) WarmGold else Color.Transparent
            val text = if (isSelected) Color(0xFF131313) else Color.White
            val border = if (isSelected) WarmGold else Color.White.copy(alpha = 0.2f)

            Box(
                modifier = Modifier
                    .background(bg)
                    .border(width = 1.dp, color = border, shape = RoundedCornerShape(0.dp))
                    .clickable { onTagSelect(tag.id) }
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = tag.name.uppercase(),
                    style = typography.uiLabel.copy(fontSize = 11.sp),
                    color = text
                )
            }
        }
    }
}
