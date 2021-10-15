package vn.nms.sample.presentation.di

import android.content.Context
import com.bumptech.glide.Glide
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vn.nms.sample.domain.manager.UserManager
import vn.nms.sample.presentation.utils.ConnectionLiveData
import vn.nms.sample.presentation.utils.NavigatorHelper
import vn.nms.sample.presentation.utils.SafeTypeAdapterFactory
import vn.nms.sample.presentation.utils.image.ImageLoader
import vn.nms.sample.presentation.utils.image.ImageLoaderImpl
import java.lang.reflect.Modifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNavigatorHelper(userManager: UserManager) = NavigatorHelper(userManager)

    @Provides
    @Singleton
    fun provideGSon(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapterFactory(SafeTypeAdapterFactory)
        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .create()

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader =
        ImageLoaderImpl(context, Glide.with(context))

    @Singleton
    @Provides
    internal fun connectionLiveData(@ApplicationContext context: Context) =
        ConnectionLiveData(context)
}

