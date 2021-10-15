package vn.nms.sample.data.datasource

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.core.Single
import vn.nms.sample.data.services.ApiServices
import vn.nms.sample.domain.exception.UnexpectedStateException
import vn.nms.sample.domain.itemviewmodel.AdItemViewModel
import vn.nms.sample.domain.itemviewmodel.ItemViewModel
import vn.nms.sample.domain.itemviewmodel.MovieItemViewModel
import vn.nms.sample.domain.model.AdModel
import vn.nms.sample.domain.model.MovieModel
import vn.nms.sample.domain.model.request.MovieRequest
import vn.nms.sample.presentation.utils.SchedulerProvider

class MovieMultiTypeNetworkDataSource constructor(
    private val apiServices: ApiServices
) :
    RxPagingSource<MovieRequest, ItemViewModel>() {


    override fun loadSingle(params: LoadParams<MovieRequest>): Single<LoadResult<MovieRequest, ItemViewModel>> {
        return when (val request = params.key ?: MovieRequest.AdsRequest) {
            is MovieRequest.AdsRequest -> {
                apiServices.getAds().compose(SchedulerProvider.ioToIoSingle())
                    .map {
                        val adModel = it.data?.shuffled()?.first()
                        toLoadResultAds(adModel)
                    }.onErrorReturn {
                        LoadResult.Page(emptyList(), null, MovieRequest.DataRequest(page = 1))
                    }
            }
            is MovieRequest.DataRequest -> {
                val currentPage = request.page
                apiServices.getMovies(request.page).compose(SchedulerProvider.ioToIoSingle())
                    .map {
                        val isLastPage = it.next == null
                        toLoadResultMovie(it.data.orEmpty(), currentPage, isLastPage)
                    }.onErrorReturn {
                        LoadResult.Error(it)
                    }
            }
            is MovieRequest.SkipRequest , is MovieRequest.FinalRequest-> Single.just(LoadResult.Error(UnexpectedStateException()))
        }
    }

    private fun toLoadResultAds(data: AdModel?): LoadResult<MovieRequest, ItemViewModel> {
        return if (data != null) {
            LoadResult.Page(
                data = listOf(AdItemViewModel(data, data.id.toString())),
                prevKey = null,
                nextKey = MovieRequest.DataRequest(page = 1)
            )
        } else {
            LoadResult.Page(
                data = listOf(),
                prevKey = null,
                nextKey = MovieRequest.DataRequest(page = 1)
            )
        }
    }

    private fun toLoadResultMovie(data: List<MovieModel>, currentPage: Int, isLastPage: Boolean):
            LoadResult<MovieRequest, ItemViewModel> {
        return LoadResult.Page(
            data = data.map { movie -> MovieItemViewModel(movie, movie.id.toString()) },
            prevKey = if (currentPage == 1) null else MovieRequest.DataRequest(currentPage - 1),
            nextKey = if (isLastPage) null else MovieRequest.DataRequest(currentPage + 1)
        )
    }

    override fun getRefreshKey(state: PagingState<MovieRequest, ItemViewModel>): MovieRequest {
        return MovieRequest.AdsRequest
    }
}