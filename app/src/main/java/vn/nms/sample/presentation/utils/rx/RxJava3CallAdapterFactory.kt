/*
 * Copyright (C) 2015 Square, Inc.
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

import io.reactivex.rxjava3.annotations.Nullable
import io.reactivex.rxjava3.core.*
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class RxJava3CallAdapterFactory private constructor(
    private val scheduler: @Nullable Scheduler?,
    private val isAsync: Boolean
) : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): @Nullable CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (rawType == Completable::class.java) {
            // Completable is not parameterized (which is what the rest of this method deals with) so it
            // can only be created with a single configuration.
            return RxJava3CallAdapter<Any>(
                Void::class.java, scheduler, isAsync, isResult = false, isBody = true, isFlowable = false, isSingle = false,
                isMaybe = false, isCompletable = true
            )
        }
        val isFlowable = rawType == Flowable::class.java
        val isSingle = rawType == Single::class.java
        val isMaybe = rawType == Maybe::class.java
        if (rawType != Observable::class.java && !isFlowable && !isSingle && !isMaybe) {
            return null
        }
        var isResult = false
        var isBody = false
        val responseType: Type
        if (returnType !is ParameterizedType) {
            val name =
                if (isFlowable) "Flowable" else if (isSingle) "Single" else if (isMaybe) "Maybe" else "Observable"
            throw IllegalStateException(
                name + " return type must be parameterized"
                        + " as " + name + "<Foo> or " + name + "<? extends Foo>"
            )
        }
        val observableType =
            getParameterUpperBound(
                0,
                returnType
            )
        when (getRawType(observableType)) {
            Response::class.java -> {
                check(observableType is ParameterizedType) {
                    ("Response must be parameterized"
                            + " as Response<Foo> or Response<? extends Foo>")
                }
                responseType = getParameterUpperBound(
                    0,
                    observableType
                )
            }
            Result::class.java -> {
                check(observableType is ParameterizedType) {
                    ("Result must be parameterized"
                            + " as Result<Foo> or Result<? extends Foo>")
                }
                responseType = getParameterUpperBound(
                    0,
                    observableType
                )
                isResult = true
            }
            else -> {
                responseType = observableType
                isBody = true
            }
        }
        return RxJava3CallAdapter<Any>(
            responseType, scheduler, isAsync, isResult, isBody, isFlowable,
            isSingle, isMaybe, false
        )
    }

    companion object {
        /**
         * Returns an instance which creates synchronous observables that do not operate on any scheduler
         * by default.
         * @return the new adapter instance
         */
        fun create(): RxJava3CallAdapterFactory {
            return RxJava3CallAdapterFactory(null, false)
        }

        /**
         * Returns an instance which creates asynchronous observables. Applying
         * [Observable.subscribeOn] has no effect on stream types created by this factory.
         * @return the new adapter instance
         */
        fun createAsync(): RxJava3CallAdapterFactory {
            return RxJava3CallAdapterFactory(null, true)
        }

        /**
         * Returns an instance which creates synchronous observables that
         * [subscribe on][Observable.subscribeOn] `scheduler` by default.
         * @param scheduler the scheduler to run the network operations on
         * @return the new adapter instance
         */
        fun createWithScheduler(scheduler: Scheduler?): RxJava3CallAdapterFactory {
            if (scheduler == null) throw NullPointerException("scheduler == null")
            return RxJava3CallAdapterFactory(scheduler, false)
        }
    }

}