package vn.nms.sample.domain.usecase

import vn.nms.sample.domain.repo.HomeRepo
import javax.inject.Inject

class GetMovieMultiTypeNetworkDbPagingUseCase @Inject constructor(private val homeRepo: HomeRepo) {
    operator fun invoke() = homeRepo.getMovieAndAdsMediator()
}