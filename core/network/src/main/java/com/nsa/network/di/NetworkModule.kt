package com.nsa.network.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.nsa.network.BuildConfig
import com.nsa.network.Settings.OKHTTP_CONNECT_TIMEOUT
import com.nsa.network.Settings.OKHTTP_READ_TIMEOUT
import com.nsa.network.Settings.OKHTTP_WRITE_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mm.com.atom.eagle.data.remote.interceptor.AuthInterceptor
import mm.com.atom.eagle.data.remote.interceptor.TokenAuthenticator
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val cacheSize = 10L * 1024 * 1024 // 10 MB
    private const val REMOTE_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss"

    private val gson by lazy {
        GsonBuilder()
            .setDateFormat(REMOTE_DATE_TIME_FORMAT)
            .create()
    }

    @NoAuthClient
    @Singleton
    @Provides
    fun provideNoAuthOkHttpClient(
        @ApplicationContext context: Context,
//        commonInterceptor: CommonInterceptor,
//        appStateInterceptor: AppStateInterceptor,
//        responseCacheInterceptor: ResponseCacheInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, cacheSize))
//            .addInterceptor(commonInterceptor)
//            .addInterceptor(appStateInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (true) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                })
//            .addInterceptor(responseCacheInterceptor)
            .connectTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .apply {
//                if (true) {
//                    addNetworkInterceptor(StethoInterceptor())
//                }
            }.build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @NoAuthClient client: OkHttpClient,
        tokenAuthenticator: TokenAuthenticator,
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        return client.newBuilder()
            .apply {
                //add authInterceptor at the first of the list to ensure authorization in request
                interceptors().add(0, authInterceptor)
            }
            .authenticator(tokenAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return retrofitBuilder
            .client(okHttpClient)
            .build()
    }

    @NoAuthClient
    @Singleton
    @Provides
    fun provideNoAuthRetrofit(
        retrofitBuilder: Retrofit.Builder,
        @NoAuthClient okHttpClient: OkHttpClient,
    ): Retrofit {
        return retrofitBuilder
            .client(okHttpClient)
            .build()
    }

//    @Singleton
//    @Provides
//    fun provideAuthService(@NoAuthClient retrofit: Retrofit): AuthService = retrofit.create()

}