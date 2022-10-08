package vn.nms.sample.data.repo

import androidx.paging.*
import androidx.paging.rxjava3.flowable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import vn.nms.sample.data.datasource.MovieMultiTypeNetworkDataSource
import vn.nms.sample.data.datasource.MovieMultiTypeRemoteMediator
import vn.nms.sample.data.datasource.MovieSingleTypeDataSource
import vn.nms.sample.data.manager.DatabaseManager
import vn.nms.sample.data.mapper.AdMapper
import vn.nms.sample.data.mapper.MovieMapper
import vn.nms.sample.data.services.ApiServices
import vn.nms.sample.domain.define.ObjectType
import vn.nms.sample.domain.exception.UnexpectedStateException
import vn.nms.sample.domain.itemviewmodel.AdItemViewModel
import vn.nms.sample.domain.itemviewmodel.ItemViewModel
import vn.nms.sample.domain.itemviewmodel.MovieItemViewModel
import vn.nms.sample.domain.model.MovieModel
import vn.nms.sample.domain.repo.HomeRepo
import javax.inject.Inject


@ExperimentalPagingApi
class HomeRepoImpl @Inject constructor(
    private val apiServices: ApiServices,
    private val databaseManager: DatabaseManager,
    private val adMapper: AdMapper,
    private val movieMapper: MovieMapper
) : HomeRepo {

    override fun getMoviePaging(): Flowable<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                prefetchDistance = 3,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                MovieSingleTypeDataSource(apiServices)
            }
        ).flowable
    }

    override fun getMovieAndAdsPaging(): Flowable<PagingData<ItemViewModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                prefetchDistance = 3,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                MovieMultiTypeNetworkDataSource(apiServices)
            }
        ).flowable
    }


    override fun getMovieAndAdsMediator(): Flowable<PagingData<ItemViewModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                prefetchDistance = 3,
                initialLoadSize = 10
            ),
            remoteMediator = MovieMultiTypeRemoteMediator(
                apiServices,
                databaseManager,
                adMapper,
                movieMapper
            ),
            pagingSourceFactory = {
                databaseManager.movieRelationDao().getMovieRelationList()
            },

            ).flowable.map { pagingData ->
            pagingData.map { mergeData ->
                when (mergeData.getObjectType()) {
                    ObjectType.AD -> {
                        if (mergeData.adEntity != null) {
                            AdItemViewModel(
                                adMapper.mapFromEntity(
                                    mergeData.adEntity
                                ),
                                mergeData.adEntity.getId().toString()
                            )
                        } else {
                            throw UnexpectedStateException(errorMessage = "AdEntity null")
                        }

                    }
                    ObjectType.MOVIE -> {
                        if (mergeData.movieEntity != null) {
                            MovieItemViewModel(
                                movieMapper.mapFromEntity(mergeData.movieEntity),
                                mergeData.movieEntity.getId().toString()
                            )
                        } else {
                            throw UnexpectedStateException(errorMessage = "MovieEntity null")
                        }
                    }
                }
            }
        }
    }

    override fun getMovie(): Single<List<MovieModel>> {
        return apiServices.getMovies(1).map {
            it.data.orEmpty()
        }
    }

    override fun getMovieAndAdItemViewModel(): Single<List<ItemViewModel>> {
        return apiServices.getAds().zipWith(apiServices.getMovies(1)) { ads, movies ->
            val result = mutableListOf<ItemViewModel>()
            val adModel = ads.data?.shuffled()?.firstOrNull()
            if (adModel != null) {
                result.add(AdItemViewModel(adModel, adModel.id.toString()))
            }
            result.addAll(movies.data.orEmpty().map { movieModel ->
                MovieItemViewModel(movieModel, movieModel.id.toString())
            })
            result
        }
    }
}