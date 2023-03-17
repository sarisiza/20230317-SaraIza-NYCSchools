package com.example.nycschools.di

import android.net.ConnectivityManager
import com.example.nycschools.rest.NycSchoolsApi
import com.example.nycschools.rest.NycSchoolsApi.Companion.BASE_URL
import com.example.restconnection.services.ServiceCall
import com.example.restconnection.utils.CacheInterceptor
import com.example.restconnection.utils.ForceCacheInterceptor
import com.example.restconnection.utils.NetworkState
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Class NetworkModule
 * @author Sara Iza
 * Provides dependencies for network connection
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkState(
        connectivityManager: ConnectivityManager
    ): NetworkState = NetworkState(connectivityManager)

    @Provides
    @Singleton
    fun providesForceCacheInterceptor(
        networkState: NetworkState
    ): ForceCacheInterceptor = ForceCacheInterceptor(networkState)

    @Provides
    @Singleton
    fun providesCacheInterceptor(): CacheInterceptor = CacheInterceptor()

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        cache: Cache,
        cacheInterceptor: CacheInterceptor,
        forceCacheInterceptor: ForceCacheInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(cacheInterceptor)
            .addInterceptor(forceCacheInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .cache(cache)
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesNycSchoolsApi(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): NycSchoolsApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build().create(NycSchoolsApi::class.java)

    @Provides
    @Singleton
    fun providesServiceCall(
        networkState: NetworkState
    ): ServiceCall = ServiceCall(networkState)

    @Provides
    @Singleton
    fun providesIoDispather(): CoroutineDispatcher = Dispatchers.IO

}