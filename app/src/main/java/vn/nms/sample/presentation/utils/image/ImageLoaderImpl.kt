package vn.nms.sample.presentation.utils.image

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import dagger.hilt.android.qualifiers.ApplicationContext
import vn.nms.sample.R
import vn.nms.sample.data.extensions.getDeviceWidth

class ImageLoaderImpl constructor(
    @ApplicationContext val context: Context,
    private val requestManager: RequestManager
) : ImageLoader {
    override fun loadImage(imageView: ImageView, url: String?, default: Drawable?) {
        if (!url.isNullOrEmpty()) {
            requestManager.load(url)
                .placeholder(default)
                .error(default)
                .centerCrop().into(imageView)
        } else {
            imageView.setImageDrawable(default)
        }
    }

    override fun loadFullImage(imageView: ImageView, url: String?, default: Drawable?) {
        if (!url.isNullOrEmpty()) {
            requestManager.load(url).fitCenter()
                .placeholder(default)
                .error(default)

                .into(imageView)
        } else {
            imageView.setImageDrawable(default)
        }
    }

    override fun loadImageWithCorner(
        imageView: ImageView,
        url: String?,
        radius: Float,
        default: Drawable?
    ) {
        TODO("Not yet implemented")
    }

    override fun loadImageFitWithSize(
        imageView: ImageView,
        url: String?,
        width: Int?,
        height: Int?
    ) {
        if (!url.isNullOrEmpty()) {
            val deviceWidth: Int = context.getDeviceWidth()

            if (width != null && height != null && width != 0 && height != 0) {
                val ratio = width.toFloat() / height.toFloat()
                val imageHeight = (deviceWidth / ratio).toInt()
                imageView.layoutParams.height = imageHeight
                imageView.layoutParams.width = deviceWidth
                imageView.requestLayout()
                requestManager.load(url)
                    .fitCenter()
                    .into(imageView)
            } else {
                imageView.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                imageView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                imageView.requestLayout()
                requestManager.load(url)
                    .fitCenter()
                    .into(imageView)
            }
        } else {
            imageView.setImageResource(R.drawable.bg_default_image)
        }
    }
}