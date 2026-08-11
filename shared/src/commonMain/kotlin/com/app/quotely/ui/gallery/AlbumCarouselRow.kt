package com.app.quotely.ui.gallery

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quotely.domain.model.Album
import com.app.quotely.ui.theme.LocalQuotelyTypography
import com.app.quotely.ui.theme.WarmGold

@Composable
fun AlbumCarouselRow(
    albums: List<Album>,
    selectedAlbumId: String?,
    onAlbumSelect: (String?) -> Unit,
    onCreateAlbumClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalQuotelyTypography.current

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "THOUGHT ALBUMS",
                style = typography.uiLabel.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold),
                color = WarmGold
            )
        }

        Spacer(modifier = Modifier.padding(top = 8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ALL ALBUMS Chip
            val isAllSelected = selectedAlbumId == null
            Box(
                modifier = Modifier
                    .background(if (isAllSelected) WarmGold else Color.Transparent)
                    .border(
                        width = 1.dp,
                        color = if (isAllSelected) WarmGold else Color.White.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(0.dp)
                    )
                    .clickable { onAlbumSelect(null) }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "ALL THOUGHTS",
                    style = typography.uiLabel.copy(fontSize = 11.sp),
                    color = if (isAllSelected) Color(0xFF131313) else Color.White
                )
            }

            // Individual Album Chips
            albums.forEach { album ->
                val isSelected = selectedAlbumId == album.id
                val backgroundColor = if (isSelected) WarmGold else Color(0xFF222222)
                val textColor = if (isSelected) Color(0xFF131313) else Color.White
                val borderColor = if (isSelected) WarmGold else Color.White.copy(alpha = 0.2f)

                Box(
                    modifier = Modifier
                        .background(backgroundColor)
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(0.dp)
                        )
                        .clickable { onAlbumSelect(album.id) }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = album.name.uppercase(),
                            style = typography.uiLabel.copy(fontSize = 11.sp),
                            color = textColor
                        )
                    }
                }
            }

            // + NEW ALBUM Button
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .border(
                        width = 1.dp,
                        color = WarmGold.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(0.dp)
                    )
                    .clickable { onCreateAlbumClick() }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "+ NEW ALBUM",
                    style = typography.uiLabel.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold),
                    color = WarmGold
                )
            }
        }
    }
}
