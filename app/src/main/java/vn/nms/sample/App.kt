package vn.nms.sample

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import timber.log.Timber

@HiltAndroidApp
open class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Glide.get(this).setMemoryCategory(MemoryCategory.HIGH)
        RxJavaPlugins.setErrorHandler { Timber.e("AppError:%s", it.message) }
    }
}