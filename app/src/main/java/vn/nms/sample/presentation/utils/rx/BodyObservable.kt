/*
 * Copyright (C) 2016 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vn.nms.sample.presentation.utils.rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.exceptions.CompositeException
import io.reactivex.rxjava3.exceptions.Exceptions
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import retrofit2.HttpException
import retrofit2.Response
import vn.nms.sample.domain.model.response.BaseResponse

class BodyObservable<T>(private val upstream: Observable<Response<T>>) :
    Observable<T>() {
    override fun subscribeActual(observer: Observer<in T>) {
        upstream.subscribe(BodyObserver(observer))
    }

    private class BodyObserver<R> constructor(observer: Observer<in R>) :
        Observer<Response<R>> {
        private val observer: Observer<in R?> = observer as Observer<in R?>
        private var terminated = false
        override fun onSubscribe(disposable: Disposable) {
            observer.onSubscribe(disposable)
        }

        override fun onNext(response: Response<R>) {
            if (response.isSuccessful) {
                if (response.body() is BaseResponse<*>) {
                    val baseResponse = response.body() as BaseResponse<*>?
//                    if (baseResponse?.isSuccess() == true) {
//                        observer.onNext(response.body())
//                    } else {
//                        observer.onError(ApiStateException(baseResponse?.code, ""))
//                    }
                    observer.onNext(response.body())
                } else {
                    observer.onNext(response.body())
                }
            } else {
                terminated = true
                val t: Throwable = HttpException(response)
                try {
                    observer.onError(t)
                } catch (inner: Throwable) {
                    Exceptions.throwIfFatal(inner)
                    RxJavaPlugins.onError(
                        CompositeException(
                            t,
                            inner
                        )
                    )
                }
            }
        }

        override fun onComplete() {
            if (!terminated) {
                observer.onComplete()
            }
        }

        override fun onError(throwable: Throwable) {
            if (!terminated) {
                observer.onError(throwable)
            } else {
                // This should never happen! onNext handles and forwards errors automatically.
                val broken: Throwable = AssertionError(
                    "This should never happen! Report as a bug with the full stacktrace."
                )
                broken.initCause(throwable)
                RxJavaPlugins.onError(broken)
            }
        }

    }

}