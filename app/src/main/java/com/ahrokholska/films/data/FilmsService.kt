package com.ahrokholska.films.data

import retrofit2.http.GET
import retrofit2.http.Path

interface FilmsService {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): TopRatedResponse

    data class TopRatedResponse(val page: Int, val results: List<Film>)


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Film
}