package vn.nms.sample.presentation.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableTransformer
import io.reactivex.rxjava3.core.FlowableTransformer
import io.reactivex.rxjava3.core.MaybeTransformer
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.SingleTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

object SchedulerProvider {

    fun <T> ioToMainObservable(): ObservableTransformer<T, T> =
        ObservableTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }
    fun <T> ioToIoObservable(): ObservableTransformer<T, T> =
        ObservableTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }

    fun <T> ioToMainSingle(): SingleTransformer<T, T> =
        SingleTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }
    fun <T> ioToIoSingle(): SingleTransformer<T, T> =
        SingleTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getIOThreadScheduler())
        }

    fun ioToMainCompletable(): CompletableTransformer =
        CompletableTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }


    fun <T> ioToMainFlowable(): FlowableTransformer<T, T> =
        FlowableTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }
    fun <T> ioToIoFlowable(): FlowableTransformer<T, T> =
        FlowableTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getIOThreadScheduler())
        }

    fun <T> ioToMainMaybe(): MaybeTransformer<T, T> =
        MaybeTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }
    fun <T> ioToIoMaybe(): MaybeTransformer<T, T> =
        MaybeTransformer { upstream ->
            upstream.subscribeOn(getIOThreadScheduler())
                .observeOn(getMainThreadScheduler())
        }

    private fun getIOThreadScheduler() = Schedulers.io()

    private fun getMainThreadScheduler() = AndroidSchedulers.mainThread()

}