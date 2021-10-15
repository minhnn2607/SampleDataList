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
import retrofit2.Response

/**
 * The result of executing an HTTP request.
 * @param <T> the value type of the result
</T> */
class Result<T> private constructor(
    private val response: @Nullable Response<T>?,
    private val error: @Nullable Throwable?
) {

    /**
     * The response received from executing an HTTP request. Only present when [.isError] is
     * false, null otherwise.
     * @return the response object
     */
    fun response(): @Nullable Response<T>? {
        return response
    }


    fun error(): @Nullable Throwable? {
        return error
    }

    fun isError(): Boolean {
        return error != null
    }

    companion object {
        fun <T> error(error: Throwable?): Result<T> {
            if (error == null) throw NullPointerException("error == null")
            return Result(null, error)
        }

        fun <T> response(response: Response<T>?): Result<T> {
            if (response == null) throw NullPointerException("response == null")
            return Result(response, null)
        }
    }

}