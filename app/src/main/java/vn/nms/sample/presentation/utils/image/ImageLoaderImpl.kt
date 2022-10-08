package vn.nms.sample.presentation.utils.image

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import dagger.hilt.android.qualifiers.ApplicationContext

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

}