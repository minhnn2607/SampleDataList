package vn.nms.sample.presentation.utils

import com.google.gson.Gson
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type
import kotlin.jvm.Throws

class ServiceResponseConverter(
    private val gSon: Gson
) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return try {
            if (type === String::class.java) {
                StringResponseConverter()
            } else GsonConverterFactory.create(gSon).responseBodyConverter(
                type!!,
                annotations,
                retrofit
            )
        } catch (ignored: OutOfMemoryError) {
            null
        }
    }

    override fun requestBodyConverter(
        type: Type?, parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>, retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return GsonConverterFactory.create(gSon)
            .requestBodyConverter(type!!, parameterAnnotations, methodAnnotations, retrofit)
    }

    private class StringResponseConverter : Converter<ResponseBody, String> {
        @Throws(IOException::class)
        override fun convert(value: ResponseBody): String {
            return value.string()
        }
    }
}