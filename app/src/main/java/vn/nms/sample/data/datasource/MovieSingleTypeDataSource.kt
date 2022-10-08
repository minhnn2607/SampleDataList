package vn.nms.sample.data.datasource

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.core.Single
import vn.nms.sample.data.services.ApiServices
import vn.nms.sample.domain.model.MovieModel
import vn.nms.sample.presentation.utils.SchedulerProvider

class MovieSingleTypeDataSource constructor(private val apiServices: ApiServices) :
    RxPagingSource<Int, MovieModel>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieModel>> {
        val position = params.key ?: 1
        return apiServices.getMovies(position).compose(SchedulerProvider.ioToIoSingle())
            .map {
                toLoadResult(it.data.orEmpty(), position, it.next == null)
            }.onErrorReturn {
                LoadResult.Error(it)
            }
    }

    private fun toLoadResult(data: List<MovieModel>, currentPage: Int, isLastPage: Boolean):
            LoadResult<Int, MovieModel> {
        return LoadResult.Page(
            data = data,
            prevKey = if (currentPage == 1) null else currentPage - 1,
            nextKey = if (isLastPage) null else currentPage + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int {
        return 1
    }
}