package vn.nms.sample.data.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Long.formatDate(format: String): String {
    return try {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val netDate = Date(this)
        sdf.format(netDate)
    } catch (e: Exception) {
        ""
    }
}

fun String.formatDate(format: String): Date? {
    val sdfSource = SimpleDateFormat(format, Locale.US)
    sdfSource.timeZone = TimeZone.getTimeZone("UTC")
    return try {
        sdfSource.parse(this)
    } catch (e: ParseException) {
        null
    }
}

