package vn.nms.sample.domain.model

open class BasePagingModel(
    var total: Int = 0,
    var current: Int = 0,
    var next: Int? = null
): BaseDataModel()