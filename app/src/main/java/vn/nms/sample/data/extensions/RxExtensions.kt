package vn.nms.sample.data.extensions

import android.view.View
import vn.nms.sample.domain.`interface`.PlainConsumer

fun <T> onSuccess(consumer: (T) -> Unit) = object : PlainConsumer<T> {
    override fun accept(t: T) {
        consumer.invoke(t)
    }
}

fun <T> onError(consumer: (T) -> Unit) = object : PlainConsumer<T> {
    override fun accept(t: T) {
        consumer.invoke(t)
    }
}

fun onClickListener(onClick: () -> Unit) = View.OnClickListener {
    onClick.invoke()
}

