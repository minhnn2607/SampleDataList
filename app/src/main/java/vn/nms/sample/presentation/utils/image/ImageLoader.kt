package vn.nms.sample.presentation.utils.image

import android.graphics.drawable.Drawable
import android.widget.ImageView

interface ImageLoader {
    fun loadImage(imageView: ImageView, url: String?, default: Drawable? = null)
    fun loadFullImage(imageView: ImageView, url: String?, default: Drawable? = null)
}