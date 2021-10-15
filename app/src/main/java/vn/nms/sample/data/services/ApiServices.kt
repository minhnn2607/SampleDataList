package vn.nms.sample.data.services

import io.reactivex.rxjava3.core.Single
import retrofit2.http.*
import vn.nms.sample.domain.config.ApiEndPoint
import vn.nms.sample.domain.model.AdModel
import vn.nms.sample.domain.model.MovieModel
import vn.nms.sample.domain.model.response.BasePagingResponse
import vn.nms.sample.domain.model.response.BaseResponse

interface ApiServices {

    @FormUrlEncoded
    @POST(ApiEndPoint.LOGIN)
    fun login(
        @Field("user_name") userName: String,
        @Field("password") pass: String
    ): Single<BaseResponse<Any>>

    @GET(ApiEndPoint.MOVIE)
    fun getMovies(@Query("page") page: Int): Single<BasePagingResponse<List<MovieModel>>>

    @GET(ApiEndPoint.ADS)
    fun getAds(): Single<BaseResponse<List<AdModel>>>
}