package com.app.quotely.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.app.quotely.domain.model.Album
import com.app.quotely.ui.theme.WarmGold

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddToAlbumSheet(
    albums: List<Album>,
    assignedAlbumIds: Set<String>,
    onToggleAlbum: (String, Boolean) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val assignedAlbums = albums.filter { assignedAlbumIds.contains(it.id) }
    val unassignedAlbums = albums.filter { !assignedAlbumIds.contains(it.id) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .border(1.dp, WarmGold, RoundedCornerShape(12.dp)),
            color = Color(0xFF131313),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Text(
                    text = "“ ”",
                    color = WarmGold,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "ADD TO THOUGHT ALBUM",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(14.dp))

                // Section 1: Currently Assigned Status Badge
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "CURRENTLY BELONGS TO:",
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (assignedAlbums.isEmpty()) {
                        Text(
                            text = "Not in any album yet",
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 12.sp
                        )
                    } else {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            assignedAlbums.forEach { album ->
                                Row(
                                    modifier = Modifier
                                        .background(Color(0xFF222222), RoundedCornerShape(4.dp))
                                        .border(1.dp, WarmGold, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = album.name.uppercase(),
                                        color = WarmGold,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "✕",
                                        color = Color.White.copy(alpha = 0.6f),
                                        fontSize = 10.sp,
                                        modifier = Modifier.clickable {
                                            onToggleAlbum(album.id, false)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Section 2: Dropdown Selector for Unassigned Albums
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "ADD TO ANOTHER ALBUM:",
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF1E1E1E), RoundedCornerShape(4.dp))
                                .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                .clickable(enabled = unassignedAlbums.isNotEmpty()) {
                                    isDropdownExpanded = true
                                }
                                .padding(horizontal = 14.dp, vertical = 12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (unassignedAlbums.isEmpty()) {
                                        "All albums assigned"
                                    } else {
                                        "Select album from list..."
                                    },
                                    color = if (unassignedAlbums.isEmpty()) Color.White.copy(alpha = 0.4f) else Color.White,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "▼",
                                    color = WarmGold,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false },
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .background(Color(0xFF1E1E1E))
                                .border(1.dp, WarmGold, RoundedCornerShape(4.dp))
                        ) {
                            unassignedAlbums.forEach { album ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(
                                                text = album.name.uppercase(),
                                                color = Color.White,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            album.description?.let { desc ->
                                                Text(
                                                    text = desc,
                                                    color = Color.White.copy(alpha = 0.5f),
                                                    fontSize = 10.sp
                                                )
                                            }
                                        }
                                    },
                                    onClick = {
                                        isDropdownExpanded = false
                                        onToggleAlbum(album.id, true)
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Done Close Button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WarmGold,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("DONE", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
