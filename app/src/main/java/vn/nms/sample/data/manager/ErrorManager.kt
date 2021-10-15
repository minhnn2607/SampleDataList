package vn.nms.sample.data.manager

import com.google.gson.JsonParseException
import retrofit2.HttpException
import vn.nms.sample.R
import vn.nms.sample.domain.config.AppConfig
import vn.nms.sample.domain.define.ErrorState
import vn.nms.sample.domain.exception.ApiStateException
import vn.nms.sample.domain.exception.UnexpectedStateException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorManager constructor(private val resourceManager: ResourceManager) {
    fun getErrorState(throwable: Throwable): ErrorState {
        val e = ErrorState()
        when (throwable) {
            is UnknownHostException, is ConnectException -> {
                e.status =
                    AppConfig.ERROR_NO_INTERNET
                e.message = resourceManager.getString(R.string.error_network_connection)
                e.reason = throwable.message
            }
            is SocketTimeoutException -> {
                e.status =
                    AppConfig.ERROR_TIME_OUT
                e.message = resourceManager.getString(R.string.error_network_timeout)
                e.reason = throwable.message
            }
            is HttpException -> {
                e.status = throwable.code()
                e.reason = throwable.message
            }
            is ApiStateException -> {
                e.status = throwable.code
                if(throwable.errorMessage.isNullOrEmpty()){
                    e.message = resourceManager.getString(R.string.error_common).format(e.status)
                }else{
                    e.message = throwable.errorMessage?:""
                }
                e.reason = throwable.message
                e.message = throwable.errorMessage?:""
            }

            is JsonParseException -> {
                e.status = AppConfig.ERROR_PARSE_JSON
                e.message = resourceManager.getString(R.string.error_common).format(AppConfig.ERROR_PARSE_JSON)
                e.reason = throwable.message
            }
            is UnexpectedStateException -> {
                e.status = throwable.code
                e.message = throwable.errorMessage
                e.reason = throwable.reason
            }
            else -> {
                e.status = AppConfig.ERROR_UNKNOWN
                e.reason = throwable.message
            }
        }
        return e
    }
}