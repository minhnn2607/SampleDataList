package vn.nms.sample.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import vn.nms.sample.domain.define.ObjectType

@Entity(tableName = "movie_relation_table", primaryKeys = ["objectId"])
data class MovieRelationEntity(
    @ColumnInfo(name = "objectId")
    var objectId: String,

    @ColumnInfo(name = "nextKey")
    var nextKey: String?,

    @ColumnInfo(name = "previousKey")
    var previousKey: String?
)