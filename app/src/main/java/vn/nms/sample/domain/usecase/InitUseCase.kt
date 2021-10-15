package vn.nms.sample.domain.usecase

import io.reactivex.rxjava3.core.Single
import vn.nms.sample.domain.model.intent.SplashIntent
import vn.nms.sample.domain.repo.SharePrefRepo
import vn.nms.sample.domain.repo.UserRepo
import javax.inject.Inject

class InitUseCase @Inject constructor(
    private val userRepo: UserRepo,
    private val sharePrefRepo: SharePrefRepo
) {
    operator fun invoke(): Single<SplashIntent> =
        userRepo.initUser().map {
            if (sharePrefRepo.getLoginFlag()) {
                SplashIntent.OpenHome
            } else {
                SplashIntent.OpenInit
            }

        }
}