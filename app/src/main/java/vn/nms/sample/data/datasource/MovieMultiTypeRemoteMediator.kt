package vn.nms.sample.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import vn.nms.sample.data.entity.MovieMergeData
import vn.nms.sample.data.entity.MovieRelationEntity
import vn.nms.sample.data.manager.DatabaseManager
import vn.nms.sample.data.mapper.AdMapper
import vn.nms.sample.data.mapper.MovieMapper
import vn.nms.sample.data.services.ApiServices
import vn.nms.sample.domain.define.ObjectType
import vn.nms.sample.domain.model.AdModel
import vn.nms.sample.domain.model.MovieModel
import vn.nms.sample.domain.model.request.MovieRequest

@ExperimentalPagingApi
class MovieMultiTypeRemoteMediator constructor(
    private val apiServices: ApiServices,
    private val database: DatabaseManager,
    private val adMapper: AdMapper,
    private val movieMapper: MovieMapper,
) :
    RxRemoteMediator<Int, MovieMergeData>() {
    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, MovieMergeData>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        MovieRequest.AdsRequest
                    }
                    LoadType.PREPEND -> {
                        val item = getMergeDataForFirstItem(state)
                        when (item?.getObjectType()) {
                            ObjectType.AD -> {
                                MovieRequest.SkipRequest
                            }
                            ObjectType.MOVIE -> {
                                val previousPage = item.movieRelationEntity.previousKey?.toInt()
                                if (previousPage == null) {
                                    MovieRequest.AdsRequest
                                } else {
                                    MovieRequest.DataRequest(previousPage)
                                }
                            }
                            else -> {
                                MovieRequest.SkipRequest
                            }
                        }
                    }
                    LoadType.APPEND -> {
                        val item = getMergeDataForLastItem(state)
                        when (item?.getObjectType()) {
                            ObjectType.AD -> {
                                MovieRequest.DataRequest(1)
                            }
                            ObjectType.MOVIE -> {
                                val nextPage = item.movieRelationEntity.nextKey?.toInt()
                                if (nextPage == null) {
                                    MovieRequest.FinalRequest
                                } else {
                                    MovieRequest.DataRequest(nextPage)
                                }
                            }
                            else -> {
                                MovieRequest.SkipRequest
                            }
                        }
                    }
                }
            }.flatMap { request ->
                when (request) {
                    is MovieRequest.SkipRequest -> {
                        Single.just(MediatorResult.Success(false))
                    }
                    is MovieRequest.FinalRequest -> {
                        Single.just(MediatorResult.Success(true))
                    }
                    is MovieRequest.DataRequest -> {
                        apiServices.getMovies(request.page)
                            .map {
                                val isLastPage = it.next == null
                                insertMoviesToDb(it.data.orEmpty(), request.page, isLastPage)
                            }
                            .map<MediatorResult> {
                                MediatorResult.Success(false)
                            }.onErrorReturn { MediatorResult.Error(it) }
                    }
                    is MovieRequest.AdsRequest -> {
                        apiServices.getAds()
                            .map {
                                val adModel = it.data?.shuffled()?.first()
                                insertAdToDb(adModel)
                            }
                            .map<MediatorResult> {
                                MediatorResult.Success(false)
                            }.onErrorReturn { MediatorResult.Error(it) }
                    }
                }
            }
    }

    private fun insertAdToDb(adModel: AdModel?) {
        if (adModel != null) {
            database.apply {
                runInTransaction {
                    movieRelationDao().deleteAll()
                    adDao().deleteAll()
                    movieDao().deleteAll()
                    adDao().insert(adMapper.mapToEntity(adModel))
                    movieRelationDao().insert(
                        MovieRelationEntity(
                            objectId = ObjectType.AD.name + "_" + adModel.id,
                            previousKey = null, nextKey = ObjectType.MOVIE.name
                        )
                    )
                }
            }
        }
    }

    private fun insertMoviesToDb(movies: List<MovieModel>, currentPage: Int, isLastPage: Boolean) {
        database.apply {
            runInTransaction {
                movieDao().insertList(movies
                    .map { movieModel -> movieMapper.mapToEntity(movieModel) })
                movieRelationDao().insertList(
                    movies
                        .map { movieModel ->
                            MovieRelationEntity(
                                objectId = ObjectType.MOVIE.name + "_" + movieModel.id,
                                previousKey = if (currentPage == 1) null else (currentPage - 1).toString(),
                                nextKey = if (isLastPage) null else (currentPage + 1).toString()
                            )
                        })
            }
        }
    }

    private fun getMergeDataForFirstItem(state: PagingState<Int, MovieMergeData>): MovieMergeData? {
        val firstItem =
            state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.movieRelationEntity
        return if (firstItem != null) database.movieRelationDao()
            .getRelationItem(firstItem.objectId)
        else null
    }

    private fun getMergeDataForLastItem(state: PagingState<Int, MovieMergeData>): MovieMergeData? {
        val lastItem =
            state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.movieRelationEntity
        return if (lastItem != null) database.movieRelationDao().getRelationItem(lastItem.objectId)
        else null
    }
}