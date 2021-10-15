package vn.nms.sample.presentation.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vn.nms.sample.data.manager.ResourceManager
import vn.nms.sample.data.manager.DatabaseManager
import vn.nms.sample.data.manager.ErrorManager
import vn.nms.sample.data.manager.UserManagerImpl
import vn.nms.sample.domain.config.AppConfig
import vn.nms.sample.domain.manager.UserManager
import vn.nms.sample.presentation.utils.AppConverters
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideUserManager(): UserManager = UserManagerImpl()

    @Provides
    @Singleton
    fun provideResourceManager(@ApplicationContext context: Context) = ResourceManager(context)

    @Provides
    @Singleton
    fun provideErrorManager(resourceManager: ResourceManager) = ErrorManager(resourceManager)

    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context, gson: Gson): DatabaseManager {
        AppConverters.initialize(gson)
        return Room.databaseBuilder(context, DatabaseManager::class.java, AppConfig.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}