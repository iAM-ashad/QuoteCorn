package com.app.quotely.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

/**
 * Multiplatform abstraction interface to save rendered ImageBitmap to system gallery/pictures storage.
 */
expect class ImageSaver {
    fun saveBitmapToGallery(bitmap: ImageBitmap, fileName: String): Boolean
}

@Composable
expect fun rememberImageSaver(): ImageSaver
