package vn.nms.sample.presentation.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException

object SafeTypeAdapterFactory : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T?> {
        val delegate = gson.getDelegateAdapter(this, type)
        return object : TypeAdapter<T?>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: T?) {
                try {
                    delegate.write(out, value)
                } catch (e: IOException) {
                    delegate.write(out, null)
                }
            }

            @Throws(IOException::class)
            override fun read(jsonReader: JsonReader): T? {
                return try {
                    delegate.read(jsonReader)
                } catch (e: IOException) {
                    jsonReader.skipValue()
                    null
                } catch (e: IllegalStateException) {
                    jsonReader.skipValue()
                    null
                } catch (e: JsonSyntaxException) {
                    jsonReader.skipValue()
                    null
                }
            }
        }
    }
}