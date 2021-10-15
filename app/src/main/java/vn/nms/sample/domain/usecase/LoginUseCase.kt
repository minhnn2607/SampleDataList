package vn.nms.sample.domain.usecase

import vn.nms.sample.domain.repo.UserRepo
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepo: UserRepo) {
    operator fun invoke(userName: String, pass: String) = userRepo.login(userName, pass)
}