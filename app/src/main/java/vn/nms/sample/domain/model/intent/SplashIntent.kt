package vn.nms.sample.domain.model.intent

sealed class SplashIntent {
    object OpenHome: SplashIntent()
    object OpenInit: SplashIntent()
}