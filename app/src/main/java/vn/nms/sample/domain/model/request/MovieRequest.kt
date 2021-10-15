package vn.nms.sample.domain.model.request

sealed class MovieRequest {
    data class DataRequest(var page: Int = 1): MovieRequest()
    object AdsRequest: MovieRequest()
    object SkipRequest: MovieRequest()
    object FinalRequest: MovieRequest()
}