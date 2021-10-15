package vn.nms.sample.data.repo

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import vn.nms.sample.R
import vn.nms.sample.data.manager.ResourceManager
import vn.nms.sample.data.services.ApiServices
import vn.nms.sample.domain.model.intent.InitScreenIntent
import vn.nms.sample.domain.model.intent.LoginIntent
import vn.nms.sample.domain.repo.SharePrefRepo
import vn.nms.sample.domain.repo.UserRepo
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val resource: ResourceManager,
    private val apiServices: ApiServices,
    private val sharePrefRepo: SharePrefRepo
) : UserRepo {
    override fun initUser(): Single<Unit> {
        return Single.just(Unit)
    }

    override fun initStartUpScreen(): Observable<InitScreenIntent> {
        return Observable.just(InitScreenIntent.OpenRegister)
    }

    override fun login(userName: String, pass: String): Single<LoginIntent> {
        return if (userName.isEmpty() && pass.isEmpty()) {
            Single.just(LoginIntent.Error(resource.getString(R.string.error_missing_field)))
        } else {
            apiServices.login(userName, pass).map {
                if (it.isSuccess()) {
                    sharePrefRepo.saveLoginFlag()
                    LoginIntent.Success(resource.getString(R.string.message_login_success))
                } else {
                    LoginIntent.Error(it.message)
                }
            }
        }
    }

    override fun logOut(): Single<Unit> = Single.fromCallable {
        sharePrefRepo.clearUserData()
    }
}