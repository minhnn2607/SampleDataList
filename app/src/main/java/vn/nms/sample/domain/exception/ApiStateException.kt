package vn.nms.sample.domain.exception

class ApiStateException(
    var code: Int?,
    var errorMessage: String?,
) : Throwable()