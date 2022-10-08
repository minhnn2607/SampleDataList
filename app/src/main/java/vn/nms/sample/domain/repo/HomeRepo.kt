package vn.nms.sample.domain.repo

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import vn.nms.sample.domain.itemviewmodel.ItemViewModel
import vn.nms.sample.domain.model.MovieModel

interface HomeRepo {
    fun getMoviePaging(): Flowable<PagingData<MovieModel>>
    fun getMovieAndAdsPaging(): Flowable<PagingData<ItemViewModel>>
    fun getMovieAndAdsMediator(): Flowable<PagingData<ItemViewModel>>

    fun getMovie(): Single<List<MovieModel>>
    fun getMovieAndAdItemViewModel(): Single<List<ItemViewModel>>
}