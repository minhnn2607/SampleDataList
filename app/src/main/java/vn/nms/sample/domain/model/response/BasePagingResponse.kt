package vn.nms.sample.domain.model.response

import com.google.gson.annotations.SerializedName
import vn.nms.sample.domain.model.BasePagingModel

data class BasePagingResponse<T>(
    @SerializedName("data")
    var data: T?,

): BasePagingModel() {
    fun isSuccess(): Boolean = status == 200
}
