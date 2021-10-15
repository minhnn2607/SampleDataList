package vn.nms.sample.domain.model.response

import com.google.gson.annotations.SerializedName
import vn.nms.sample.domain.model.BaseDataModel

data class BaseResponse<T>(
    @SerializedName("data")
    var data: T?,

): BaseDataModel() {
    fun isSuccess(): Boolean = status == 200
}
