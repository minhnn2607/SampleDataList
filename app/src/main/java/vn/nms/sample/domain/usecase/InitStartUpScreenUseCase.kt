package vn.nms.sample.domain.usecase

import vn.nms.sample.domain.repo.UserRepo
import javax.inject.Inject

class InitStartUpScreenUseCase @Inject constructor(private val userRepo: UserRepo) {
    operator fun invoke() = userRepo.initStartUpScreen()
}