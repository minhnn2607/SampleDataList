package vn.nms.sample.domain.exception

class UnexpectedStateException(
    val code: Int = -1,
    val reason: String = "",
    val errorMessage : String = ""
) : Throwable()