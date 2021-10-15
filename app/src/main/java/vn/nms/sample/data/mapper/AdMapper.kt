package vn.nms.sample.data.mapper

import vn.nms.sample.data.entity.AdEntity
import vn.nms.sample.domain.define.ObjectType
import vn.nms.sample.domain.model.AdModel
import javax.inject.Inject

class AdMapper @Inject constructor() : Mapper<AdEntity, AdModel> {
    override fun mapFromEntity(type: AdEntity): AdModel {
        return AdModel(id = type.getId(), title = type.adTitle, image = type.adImage)
    }

    override fun mapToEntity(type: AdModel): AdEntity {
        return AdEntity(
            objectId = ObjectType.AD.name + "_" + type.id,
            adTitle = type.title,
            adImage = type.image
        )
    }
}