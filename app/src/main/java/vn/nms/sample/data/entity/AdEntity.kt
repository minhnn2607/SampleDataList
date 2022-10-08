package vn.nms.sample.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ad_table")
data class AdEntity(
    @PrimaryKey
    var objectId: String,

    @ColumnInfo(name = "thumb")
    var adTitle: String,

    @ColumnInfo(name = "image")
    var adImage: String
) {
    fun getId(): Int {
        return objectId.split("_").lastOrNull()?.toIntOrNull() ?: -1
    }
}