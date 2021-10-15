package vn.nms.sample.presentation.utils

object DateTimeUtils {
    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS

//    fun covertTimeToText(context: Context, dataDate: Date?): String {
//        if (dataDate == null) return ""
//        val nowTime = Date()
//        val diff = nowTime.time - dataDate.time
//        return if (diff > TimeUnit.MILLISECONDS.convert(7L, TimeUnit.DAYS)) {
//            SimpleDateFormat("dd/MM/yyyy", Locale.US).format(dataDate)
//        } else {
//            when {
//                diff < 0 -> ""
//                diff < MINUTE_MILLIS -> {
//                    context.getString(R.string.moment_ago)
//                }
//                diff < 2 * MINUTE_MILLIS -> {
//                    context.resources.getQuantityString(
//                        R.plurals.minute_ago,
//                        1, 1
//                    )
//                }
//                diff < 60 * MINUTE_MILLIS -> {
//                    context.resources.getQuantityString(
//                        R.plurals.minute_ago,
//                        (diff / MINUTE_MILLIS).toInt(),
//                        (diff / MINUTE_MILLIS).toInt()
//                    )
//                }
//                diff < 2 * HOUR_MILLIS -> {
//                    context.resources.getQuantityString(R.plurals.hour_ago, 1, 1)
//                }
//                diff < 24 * HOUR_MILLIS -> {
//                    context.resources.getQuantityString(
//                        R.plurals.hour_ago,
//                        (diff / HOUR_MILLIS).toInt(),
//                        (diff / HOUR_MILLIS).toInt()
//                    )
//                }
//                diff < 48 * HOUR_MILLIS -> {
//                    context.getString(R.string.yesterday)
//                }
//                else -> {
//                    context.resources.getQuantityString(
//                        R.plurals.day_ago,
//                        (diff / DAY_MILLIS).toInt(),
//                        (diff / DAY_MILLIS).toInt()
//                    )
//                }
//            }
//        }
//    }
}