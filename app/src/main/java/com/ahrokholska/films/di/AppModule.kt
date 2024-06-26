package com.ahrokholska.films.di

import com.ahrokholska.films.data.FilmsRepositoryImpl
import com.ahrokholska.films.data.FilmsService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header(
            "Authorization", "Bearer " +
                    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlZmE0NDNiMDM4ODMzNzdlMzc2NGNjZGVlOWFmMzliMCIsIm5iZiI6MTcxOTM5ODQxOC4yODA5OTksInN1YiI6IjY2NzllMjAxNDY0MTI2MmYxYTU2NTQ1ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.M3WJk_T9w5l2XHcZJ_6rhtdcxoCF6ucX_DLZ3x9wZyk"
        )
        chain.proceed(requestBuilder.build())
    }.build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideFilmsService(retrofit: Retrofit): FilmsService =
        retrofit.create(FilmsService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {
        @Binds
        fun bindFilmsRepository(filmsRepository: FilmsRepositoryImpl): FilmsRepository
    }
}