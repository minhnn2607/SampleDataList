package vn.nms.sample.data.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import vn.nms.sample.R

fun Activity.navigate(direction: NavDirections) {
    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(direction)
}

/**
 * Open child screen with direction
 */
fun View.navigate(direction: NavDirections){
    Navigation.findNavController(this).navigate(direction)
}

inline fun <reified T : Activity> Context.navigateActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}

fun Activity.navigatorShare(shareBody: String?) {
    if (!shareBody.isNullOrEmpty()) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share"))
    }
}

fun Activity.navigateOpenStore(url: String?) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }
}

fun Activity.navigateNotificationSettingApp() {
    startActivity(Intent().apply {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                putExtra(Settings.EXTRA_APP_PACKAGE, baseContext.packageName)
            }
            else -> {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                addCategory(Intent.CATEGORY_DEFAULT)
                data = Uri.parse("package:" + baseContext.packageName)
            }
        }
    })
}