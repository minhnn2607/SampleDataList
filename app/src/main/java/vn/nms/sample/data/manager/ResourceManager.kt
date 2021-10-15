package vn.nms.sample.data.manager

import android.content.Context
import androidx.annotation.*
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext


class ResourceManager constructor(@ApplicationContext private val context: Context) {
    fun getString(@StringRes resourceIdentifier: Int, vararg arguments: Any): String {
        return if (arguments.isNotEmpty())
            context.resources.getString(resourceIdentifier, *arguments)
        else
            context.resources.getString(resourceIdentifier)
    }

    fun getStringArray(@ArrayRes resourceIdentifier: Int): Array<String> =
        context.resources.getStringArray(resourceIdentifier)

    fun getInteger(@IntegerRes resourceIdentifier: Int): Int =
        context.resources.getInteger(resourceIdentifier)

    fun getIntegerArray(@ArrayRes resourceIdentifier: Int): Array<Int> =
        context.resources.getIntArray(resourceIdentifier).toTypedArray()

    fun getBoolean(@BoolRes resourceIdentifier: Int): Boolean =
        context.resources.getBoolean(resourceIdentifier)

    fun getColor(@ColorRes resourceIdentifier: Int): Int =
        ContextCompat.getColor(context, resourceIdentifier)
}