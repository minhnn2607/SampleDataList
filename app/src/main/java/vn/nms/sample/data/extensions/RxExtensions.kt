package vn.nms.sample.data.extensions

import android.view.View
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleSource
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

fun <T, U> Single<T>.zipWithX(other: SingleSource<U>): Single<Pair<T, U>> =
    zipWith(other, { t, u -> Pair(t, u) })