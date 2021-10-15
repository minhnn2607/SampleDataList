package vn.nms.sample.data.manager

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.nms.sample.data.dao.*
import vn.nms.sample.data.entity.*
import vn.nms.sample.domain.config.AppConfig
import vn.nms.sample.presentation.utils.AppConverters

@Database(
    entities = [
        MovieEntity::class,
        AdEntity::class,
        MovieRelationEntity::class
    ],
    version = AppConfig.DB_VERSION,
    exportSchema = false
)
@TypeConverters(AppConverters::class)
abstract class DatabaseManager : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun adDao(): AdDao
    abstract fun movieRelationDao(): MovieRelationDao
}
