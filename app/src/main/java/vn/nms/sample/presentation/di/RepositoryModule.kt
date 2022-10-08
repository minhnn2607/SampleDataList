package vn.nms.sample.presentation.di

import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vn.nms.sample.data.repo.HomeRepoImpl
import vn.nms.sample.data.repo.SharePrefRepoImpl
import vn.nms.sample.data.repo.UserRepoImpl
import vn.nms.sample.domain.repo.HomeRepo
import vn.nms.sample.domain.repo.SharePrefRepo
import vn.nms.sample.domain.repo.UserRepo


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindUserRepo(repoImpl: UserRepoImpl): UserRepo

    @Binds
    abstract fun bindSharePrefRepo(repoImpl: SharePrefRepoImpl): SharePrefRepo

    @ExperimentalPagingApi
    @Binds
    abstract fun bindHomeRepo(repoImpl: HomeRepoImpl): HomeRepo
}
