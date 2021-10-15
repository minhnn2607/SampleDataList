package vn.nms.sample.presentation.ui.binding

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.Patterns
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import vn.nms.sample.presentation.ui.widget.AppNavigationBar
import vn.nms.sample.presentation.utils.image.ImageLoader

object ViewBinding {
    @JvmStatic
    @BindingAdapter("isBold")
    fun setBold(view: TextView, isBold: Boolean) {
        if (isBold) {
            view.setTypeface(null, Typeface.BOLD)
        } else {
            view.setTypeface(null, Typeface.NORMAL)
        }
    }

    @JvmStatic
    @BindingAdapter("isUnderLine")
    fun setUnderLine(view: TextView, isUnderLine: Boolean) {
        if (isUnderLine) {
            view.paintFlags = view.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @JvmStatic
    @BindingAdapter("url")
    fun loadWebViewUrl(view: WebView, url: String?) {
        if (!url.isNullOrEmpty()) {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                view.settings.apply {
                    javaScriptEnabled = true
                    setAppCachePath(view.context.cacheDir.absolutePath)
                    loadWithOverviewMode = true
                    allowFileAccess = false
                    allowContentAccess = false
                    allowFileAccessFromFileURLs = false
                    setGeolocationEnabled(false)
                    setSupportZoom(true)
                    displayZoomControls = false
                    builtInZoomControls = false
                    cacheMode = WebSettings.LOAD_DEFAULT
                    layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
                    domStorageEnabled = true
                    setAppCacheEnabled(true)
                    defaultTextEncodingName = "utf-8"
                }
                view.loadUrl(url)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl", "imageLoader", "error")
    fun setImageWithError(
        view: ImageView,
        url: String?,
        imageLoader: ImageLoader?,
        error: Drawable
    ) {
        imageLoader?.loadImage(view, url, error)
    }

    @JvmStatic
    @BindingAdapter("imageUrl", "imageLoader")
    fun setImage(
        view: ImageView,
        url: String?,
        imageLoader: ImageLoader?
    ) {
        imageLoader?.loadImage(view, url, null)
    }

    @JvmStatic
    @BindingAdapter("imageFullUrl", "imageLoader")
    fun setImageFull(
        view: ImageView,
        url: String?,
        imageLoader: ImageLoader?
    ) {
        imageLoader?.loadFullImage(view, url, null)
    }

    @JvmStatic
    @BindingAdapter("navTitle")
    fun AppNavigationBar.setNavTitle(title: String?) {
        this.setTitle(title ?: "")
    }

    @JvmStatic
    @BindingAdapter("onClickLeftNav")
    fun setOnClickLeftNav(view: AppNavigationBar, onClickListener: View.OnClickListener?) {
        view.setOnClickLeftMenu(onClickListener)
    }

    @JvmStatic
    @BindingAdapter("onClickRightNav")
    fun setOnClickRightNav(view: AppNavigationBar, onClickListener: View.OnClickListener?) {
        view.setOnClickRightMenu(onClickListener)
    }

}