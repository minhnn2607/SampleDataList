package vn.nms.sample.data.extensions

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build

inline fun <reified T> genericCast(anything: Any):T {
    return anything as T
}

inline fun <reified T> genericCastOrNull(anything: Any?):T? {
    return anything as? T
}


fun Context.createNotificationChannel(channelId: String, channelName: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val manager = getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        channel.enableLights(true)
        channel.lightColor = Color.WHITE
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        manager?.createNotificationChannel(channel)
    }
}