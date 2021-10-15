package vn.nms.sample.domain.repo

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import vn.nms.sample.domain.model.intent.InitScreenIntent
import vn.nms.sample.domain.model.intent.LoginIntent

interface UserRepo {
    fun initUser(): Single<Unit>
    fun initStartUpScreen(): Observable<InitScreenIntent>
    fun login(userName: String, pass: String): Single<LoginIntent>
    fun logOut(): Single<Unit>
}