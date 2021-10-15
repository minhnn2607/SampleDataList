package vn.nms.sample.data.manager

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import vn.nms.sample.BuildConfig
import vn.nms.sample.domain.config.AppConfig
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class SharedPrefsManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    val mSharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConfig.SHARE_PREF_NAME + BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

    operator fun <T> get(key: String, anonymousClass: Class<T>): T? {
        return when (anonymousClass) {
            String::class.java -> mSharedPreferences.getString(key, "") as T
            Boolean::class.java -> java.lang.Boolean.valueOf(
                mSharedPreferences.getBoolean(
                    key,
                    false
                )
            ) as T
            Float::class.java -> java.lang.Float.valueOf(mSharedPreferences.getFloat(key, 0f)) as T
            Int::class.java -> Integer.valueOf(mSharedPreferences.getInt(key, 0)) as T
            Long::class.java -> java.lang.Long.valueOf(mSharedPreferences.getLong(key, 0)) as T
            else -> gson.fromJson(mSharedPreferences.getString(key, ""), anonymousClass)
        }
    }

    operator fun <T> get(key: String, typeOfT: TypeToken<T>): T? {
        return gson.fromJson(mSharedPreferences.getString(key, ""), typeOfT.type)
    }

    fun <T> put(key: String, data: T) {
        val editor = mSharedPreferences.edit()
        when (data) {
            is String -> editor.putString(key, data as String)
            is Boolean -> editor.putBoolean(key, data as Boolean)
            is Float -> editor.putFloat(key, data as Float)
            is Int -> editor.putInt(key, data as Int)
            is Long -> editor.putLong(key, data as Long)
            else -> editor.putString(key, gson.toJson(data))
        }
        editor.apply()
    }

    fun clear() {
        mSharedPreferences.edit().clear().apply()
    }

    fun clearDataByKey(key: String) {
        mSharedPreferences.edit().remove(key).apply()
    }
}