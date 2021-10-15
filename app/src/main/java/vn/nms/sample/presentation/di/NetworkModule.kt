package vn.nms.sample.presentation.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import vn.nms.sample.BuildConfig
import vn.nms.sample.data.manager.SharedPrefsManager
import vn.nms.sample.data.services.ApiServices
import vn.nms.sample.domain.manager.UserManager
import vn.nms.sample.presentation.utils.ServiceResponseConverter
import vn.nms.sample.presentation.utils.rx.RxJava3CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        @ApplicationContext context: Context,
        userManager: UserManager,
        sharedPrefsManager: SharedPrefsManager,
        gSon: Gson
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val builder = generateNewRequestBuilder(
                    context,
                    chain,
                    userManager
                )
                var response = chain.proceed(builder.build())
                response

            }
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .cache(null)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
//        if (BuildConfig.DOMAIN.startsWith("https")) {
//            val x509TrustManager = object : X509TrustManager {
//                override fun checkClientTrusted(
//                    chain: Array<out X509Certificate>?,
//                    authType: String?
//                ) {
//                }
//
//                override fun checkServerTrusted(
//                    chain: Array<out X509Certificate>?,
//                    authType: String?
//                ) {
//                }
//
//                override fun getAcceptedIssuers(): Array<X509Certificate> {
//                    return arrayOf()
//                }
//            }
//            val tlsContext: SSLContext = SSLContext.getInstance("TLS")
//            tlsContext.init(
//                null,
//                arrayOf<TrustManager>(x509TrustManager),
//                SecureRandom()
//            )
//            builder.sslSocketFactory(tlsContext.socketFactory, x509TrustManager)
//            builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
//        }
        return builder.build()
    }

    private fun generateNewRequestBuilder(
        context: Context,
        chain: Interceptor.Chain,
        userManager: UserManager
    ): Request.Builder {
        val original = chain.request()
        val originalHttpUrl = original.url
        val builderUrl = originalHttpUrl.newBuilder()
//            .addQueryParameter("lang", Locale.getDefault().language)
        val url = builderUrl.build()
        val builder = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .url(url)
        return builder
    }


    @Provides
    @Singleton
    internal fun provideApiService(gson: Gson, okHttpClient: OkHttpClient): ApiServices {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.DOMAIN + "/api/")
            .client(okHttpClient)
            .addConverterFactory(ServiceResponseConverter(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
        return retrofit.create(ApiServices::class.java)
    }
}
