package vn.nms.sample.domain.usecase

import vn.nms.sample.domain.repo.UserRepo
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val userRepo: UserRepo) {
    operator fun invoke() = userRepo.logOut()
}