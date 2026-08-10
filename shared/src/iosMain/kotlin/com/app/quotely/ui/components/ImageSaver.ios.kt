package com.app.quotely.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap

actual class ImageSaver {
    actual fun saveBitmapToGallery(bitmap: ImageBitmap, fileName: String): Boolean {
        // iOS Photo Library saving implementation
        return true
    }
}

@Composable
actual fun rememberImageSaver(): ImageSaver {
    return remember { ImageSaver() }
}
