package vn.nms.sample.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import vn.nms.sample.domain.define.ObjectType

data class MovieMergeData(
    @Embedded
    var movieRelationEntity: MovieRelationEntity,

    @Relation(
        parentColumn = "objectId",
        entityColumn = "objectId",
        entity = AdEntity::class
    )
    val adEntity: AdEntity?,

    @Relation(
        parentColumn = "objectId",
        entityColumn = "objectId",
        entity = MovieEntity::class
    )
    val movieEntity: MovieEntity?
) {
    fun getObjectType(): ObjectType {
        val objectValue = movieRelationEntity.objectId.split("_")
        return ObjectType.valueOf(objectValue.firstOrNull() ?: "")
    }
}