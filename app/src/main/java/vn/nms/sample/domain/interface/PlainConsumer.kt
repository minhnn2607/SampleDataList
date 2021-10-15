package vn.nms.sample.domain.`interface`

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.functions.Consumer


interface PlainConsumer<T> : Consumer<T> {
    override fun accept(@NonNull t: T)
}
