package vn.nms.sample.presentation.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import vn.nms.sample.domain.define.ObjectType

object AppConverters {

    private lateinit var gson: Gson

    fun initialize(gson: Gson) {
        this.gson = gson
    }

    @TypeConverter
    fun fromObjectType(type: ObjectType): String {
        return type.name
    }

    @TypeConverter
    fun toObjectType(type: String): ObjectType {
        return ObjectType.valueOf(type)
    }
}