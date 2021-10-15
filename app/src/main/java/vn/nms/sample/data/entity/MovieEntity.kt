package vn.nms.sample.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import vn.nms.sample.domain.define.ObjectType

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey
    var objectId: String,

    @ColumnInfo(name = "name")
    var movieName: String,

    @ColumnInfo(name = "thumb")
    var movieThumb: String,

    @ColumnInfo(name = "description")
    var movieDescription: String,
) {
    fun getId(): Int {
        return objectId.split("_").lastOrNull()?.toIntOrNull() ?: -1
    }
}