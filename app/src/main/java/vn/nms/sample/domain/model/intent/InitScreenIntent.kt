package vn.nms.sample.domain.model.intent

sealed class InitScreenIntent {
    object OpenHome: InitScreenIntent()
    object OpenRegister: InitScreenIntent()
}