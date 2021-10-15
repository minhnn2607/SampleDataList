package vn.nms.sample.presentation.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {
    fun netWorkCheck(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info?.isConnected ?: false
    }
}