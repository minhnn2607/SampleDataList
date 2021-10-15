package vn.nms.sample.presentation.utils.image

import android.graphics.drawable.Drawable
import android.widget.ImageView

interface ImageLoader {
    fun loadImage(imageView: ImageView, url: String?, default: Drawable? = null)
    fun loadFullImage(imageView: ImageView, url: String?, default: Drawable? = null)
    fun loadImageWithCorner(
        imageView: ImageView,
        url: String?,
        radius: Float,
        default: Drawable? = null
    )

    fun loadImageFitWithSize(
        imageView: ImageView,
        url: String?,
        width: Int? = null,
        height: Int? = null
    )
}